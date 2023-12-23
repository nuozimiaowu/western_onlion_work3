import com.sty.daoOrderSystem.Order;
import com.sty.daoOrderSystem.Order2;
import com.sty.daoOrderSystem.OrderSystemDao;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;


public class OrderTest {
    //测试查看所有订单的selectall方法
    @Test
    public void Testselectall() throws SQLException {
        OrderSystemDao orderSystemDao = new OrderSystemDao();
        List<Order2> selectall = orderSystemDao.selectAllWithTransaction();
        selectall.forEach(order -> System.out.println(order));
    }

    //测试通过订单的主键查看某个订单的selectone方法；主键是否存在的情况
    @Test
    public void Testselectone() throws SQLException{
        OrderSystemDao orderSystemDao = new OrderSystemDao();
        Order selectone = orderSystemDao.selectOne(4);
        //Order selectone = orderSystemDao.selectOne(3);
        if (selectone == null){
            System.out.println("查找失败，没有此订单");
        }else {
            System.out.println(selectone);}
    }

    //更改某个订单TestUpdate()方法，订单的主键是否存在，改变的商品是否存在
    @Test
    public void TestUpdate() throws Exception {
        Order order1 = new Order(2, 1,"2020.20.20", 0);
        //Order order1 = new Order(101, 9,"2020.20.20", 77);
        //Order order1 = new Order(10000, 1,"2020.20.20", 77);
        OrderSystemDao orderSystemDao = new OrderSystemDao();
        boolean update = orderSystemDao.update(order1);
        if(update == true){
            System.out.println("更改成功");
        }else
        {
            System.out.println("更改失败");
        }
    }

    //添加新的订单（要给予order类）Testadd(),对于商品是否存在的验证,对于主键是否存在的验证，数目不能是负数的验证
    @Test
    public void Testadd() throws Exception {
        Order order1 = new Order(110,7 ,"1000.00.00", 9);
        OrderSystemDao orderSystemDao = new OrderSystemDao();
        boolean update = orderSystemDao.add(order1);
        if(update == true){
            System.out.println("添加成功");
        }
        else
        {
            System.out.println("添加失败");
        }
    }

    //删除订单的
    @Test
    public void Testdelete() throws SQLException {
        OrderSystemDao orderSystemDao = new OrderSystemDao();
        boolean delete = orderSystemDao.delete(110);
        if(delete == true){
            System.out.println("删除成功");
        }
        else
        {
            System.out.println("删除失败,订单不存在");
        }
    }

    //测试购买的数量进行排序
    @Test
    public void TestSortByCommunityid() throws SQLException {
        OrderSystemDao orderSystemDao = new OrderSystemDao();
        List<Order> orders = orderSystemDao.sortByPrice();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
//分页查询
    @Test
    public void TestselectPage() throws SQLException {
        OrderSystemDao orderSystemDao = new OrderSystemDao();
        List<Order> orders1 = orderSystemDao.selectPage(1, 4);
        orders1.forEach(order -> System.out.println(order));

        System.out.println("---------------------------------------------------");
        List<Order> orders2 = orderSystemDao.selectPage(2, 4);
        orders2.forEach(order -> System.out.println(order));

        System.out.println("---------------------------------------------------");
        List<Order> orders3 = orderSystemDao.selectPage(3, 4);
        orders3.forEach(order -> System.out.println(order));

        System.out.println("---------------------------------------------------");
        List<Order> orders4 = orderSystemDao.selectPage(4, 4);
        orders3.forEach(order -> System.out.println(order));
    }

    //测试删除商品的方法
    @Test
    public void TestDeleteCommunity() throws SQLException {
        OrderSystemDao orderSystemDao = new OrderSystemDao();
        boolean b = orderSystemDao.deleteCommodity(1);
        if(b == true){
            System.out.println("商品删除成功");
        }
        else{
            System.out.println("商品删除失败");
        }
    }
}
