package com.sty.daoOrderSystem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderSystemDao {
    //jdbc:mysql://hostname:port/database?variableName=value

    private final static String url = "jdbc:mysql://localhost:3306/order_system";
    private final static String username = "root";
    private final static String password = "sty040311";


    //从数据库里读取所有的订单信息，并且通过事务的管理来保证数据的一致性
    public List<Order2> selectAllWithTransaction() throws SQLException {
        //建立和数据库的链接
        Connection connection = DriverManager.getConnection(url, username, password);
        //设置数据库连接的自动提交为 false ， 开启事务
        connection.setAutoCommit(false); // Start transaction
        //improve：为什么我们要比避免使用select*  ？（from gpt）
        //使用 SELECT * 会检索表中的所有列，即使在查询中并不需要所有这些列的数据，降低性能
        //如果数据库模式发生更改，例如添加或删除列，SELECT * 查询可能会导致应用程序的不稳定性。这是因为应用程序的代码可能依赖于特定的列顺序，而不是显式指定所需的列。
        //使用 SELECT * 使得代码难以维护。如果查询中的列发生变化，维护人员可能需要查看整个应用程序，以确保代码的其他部分不受影响。

        //创建并执行sql语句，获取结果的的set集合
        String sql = "SELECT order_system.`order`.id, communityid, time, commodity.price,name,brand " +
                "FROM order_system.`order`,order_system.`commodity` " +
                "where `order`.communityid = commodity.id";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        //处理查询的结果
        List<Order2> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                //从结果集中获取订单的信息
                int id = resultSet.getInt("id");
                int communityid = resultSet.getInt("communityid");
                String time = resultSet.getString("time");
                int amount = resultSet.getInt("price");
                String name = resultSet.getString("name");
                String brand = resultSet.getString("brand");
                //把信息封装到对象里，并且把这个对象添加到查询结果列表中。
                Order2 order2 = new Order2(id, communityid, time, amount,name,brand);
                list.add(order2);
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        } finally {
            connection.setAutoCommit(true);
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }

        return list;
    }

//----------------------------------------------------------------------------------------------------------------------

    //通过主键的值查找到一个特定的订单并打印出来
    public Order selectOne(int givenId) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(false);
        //上一个是连表查询（内连接），这一个是单表查询。
        String sql = "SELECT id,communityid,time,price FROM order_system.`order` WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, givenId);

        ResultSet resultSet = preparedStatement.executeQuery();
        Order order = null;

        try {
            if (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setCommunityid(resultSet.getInt("communityid"));
                order.setTime(resultSet.getString("time"));
                order.setAmount(resultSet.getInt("price"));
            }
            //提交事务
            connection.commit();
        } catch (SQLException throwables) {
            //出现了异常，打印异常并且事务回滚
            throwables.printStackTrace();
            connection.rollback();
        } finally {
            // 恢复链接数据库的自动提交状态，关闭结果集和链接，释放资源
            resultSet.close();
            preparedStatement.close();
            connection.setAutoCommit(true);
            connection.close();
        }
        //返回最后得到的订单列表
        return order;
    }

    //----------------------------------------------------------------------------------------------------------------------
    //根据主键对于数据库进行修改（更新）
    public boolean update(Order order) throws Exception {
        Connection connection = DriverManager.getConnection(url, username, password);
        int eU = 0;
        connection.setAutoCommit(false);
        //这里检查更新时候，输入的商品是否存在
        String sql2 = "SELECT name FROM order_system.commodity WHERE id = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
        preparedStatement1.setInt(1,order.getCommunityid());
        ResultSet c = preparedStatement1.executeQuery();
        boolean hasRows = c.next();
        if (!hasRows) {
            System.out.println("商品不存在，更改订单失败");
            return false;
        }

        //这里检查更新订单的主键是否存在
        Order order2 = selectOne(order.getId());
        if (order2 == null){
            System.out.println("主键不存在，请重新设置主键");
            return false;
        }

        String sql = "update order_system.`order` " +
                "set order_system.`order`.communityid = ?," +
                "time = ?,order_system.`order`.price= ? where id = ?";
        //创建并执行更新订单信息的SQL语句，使用预处理语句设置参数。
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //应该要解决注入的问题：：通过参数化的查询方式来避免sql注入，利用preparedStatement来设置参数
        //这样做可以保证用户输入的任何数据都是被视为数据的不可执行字段，解决的sql的注入问题
        try {
            preparedStatement.setInt(1, order.getCommunityid());
            preparedStatement.setString(2, order.getTime());
            preparedStatement.setInt(3, order.getAmount());
            preparedStatement.setInt(4, order.getId());
            eU = preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            preparedStatement.close();
            connection.close();
        }
        return eU == 1;
    }


    //给表添加新的元素；
    public boolean add(Order order) throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // 在创建订单时，实施数据验证，确保订单信息的完整性和准确性
        if (order.getAmount() <= 0) {
            System.out.println("数目不能小于等于0，添加订单失败");
            return false;
        }
        //主键不能重复
        Order order1 = selectOne(order.getId());
        if (order1 != null){
            System.out.println("主键已经存在，请重新设置主键");
            return false;
        }
        // 建立数据库连接
        connection = DriverManager.getConnection(url, username, password);
        // 设置数据库连接的自动提交为 false，开启事务
        connection.setAutoCommit(false);

        //商品必须存在才可以添加
        String sql2 = "SELECT * FROM order_system.commodity WHERE order_system.commodity.id = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql2);
        preparedStatement1.setInt(1,order.getCommunityid());
        ResultSet c = preparedStatement1.executeQuery();
        boolean hasRows = c.next();
        System.out.println(hasRows);
        if (!hasRows) {
              System.out.println("您想要添加的商品不存在dede，添加订单失败");
              return false;
            }
        // 创建并执行 SQL 语句，添加操作
            String sql = "insert into order_system.`order` values (?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
        try {
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setInt(2, order.getCommunityid());
            preparedStatement.setString(3, order.getTime());
            preparedStatement.setInt(4, order.getAmount());
            // 执行 SQL 语句
            int eU = preparedStatement.executeUpdate();
            // 提交事务
            connection.commit();
            // 返回执行结果
            return eU > 0;
        } catch (Exception e) {
            // 如果出现异常，回滚事务,返回false
            if (connection != null) {
                connection.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            // 关闭资源
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }


    //根据主键删除某一条订单；；
    public boolean delete(int givenid) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        String sql = "delete from order_system.`order` where order_system.`order`.id=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, givenid);
        int i = preparedStatement.executeUpdate();
        return i > 0;
    }

    //根据价格进行排序
    public List<Order> sortByPrice() throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(false);
        String sql = "select id,communityid,time,price from order_system.`order` order by order_system.`order`.price";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Order> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int communityid = resultSet.getInt("communityid");
                String time = resultSet.getString("time");
                int amount = resultSet.getInt("price");

                Order order = new Order(id, communityid, time, amount);
                list.add(order);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();

        } finally {
            connection.setAutoCommit(true);
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        return list;
    }

    //分页查询实现
    public List<Order> selectPage(int pageNumber, int pageSize) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        String sql = "SELECT id,communityid,time,price FROM order_system.`order` ORDER BY id LIMIT ? OFFSET ?";
        //pageNUmber表明想看的是第几页的内容，pageSize则是每一页的大小，offset是根据前面的两个值算出来具体某一次的分页是从哪一条数据开始的。
        int offset = (pageNumber - 1) * pageSize;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pageSize);
        preparedStatement.setInt(2, offset);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Order> list = new ArrayList<>();

        try {
            while (resultSet.next()) {
                //把通过分页查询查到的数据放到一个对象里，然后再把这个对象放入数字中》》
                int id = resultSet.getInt("id");
                int communityid = resultSet.getInt("communityid");
                String time = resultSet.getString("time");
                int amount = resultSet.getInt("price");
                Order order = new Order(id, communityid, time, amount);
                list.add(order);
            }
        } finally {
            //释放资源
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        return list;
    }

    //如果想要删除已经存在在订单中的商品，你要怎么处理？
    //answer:首先根据这个商品的id查找订单，删除所有购买了这个商品的订单（约束条件），然后再删除这个商品。
    public boolean deleteCommodity(int givenId) throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);  // 开始事务
            //先删除有关的订单
            String deleteOrderSql = "DELETE FROM order_system.`order` WHERE order_system.`order`.communityid=?";
            try (PreparedStatement preparedStatement1 = connection.prepareStatement(deleteOrderSql)) {
                preparedStatement1.setInt(1, givenId);
                preparedStatement1.executeUpdate();
            }
            //再删除要删除的商品
            String deleteCommoditySql = "DELETE FROM order_system.`commodity` WHERE order_system.`commodity`.id=?";
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(deleteCommoditySql)) {
                preparedStatement2.setInt(1, givenId);
                preparedStatement2.executeUpdate();
            }
            // 提交事务，如果没有异常，对于两个表的删除将会同时生效。
            connection.commit();
            return true;
        } catch (SQLException e) {
            // 发生异常时回滚事务
            e.printStackTrace();
            return false;
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
    }

}



