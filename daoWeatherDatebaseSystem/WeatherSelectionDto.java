package com.sty.daoWeatherDatebaseSystem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherSelectionDto {

    private final static String url = "jdbc:mysql://localhost:3306/order_system";
    private final static String username = "root";
    private final static String password = "sty040311";

    //下面就是关于数据库的问题了！
    //数据的更新，换句话说就是通过api读取数据然后把这些数据保存到数据库里，实现数据的写入和更新。
    //需要传入城市的编码
    public boolean updateCityWeather(String location1 , String location2) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        LocationApi locationApi = new LocationApi();
        String cityId = locationApi.getLocationData(location1, location2);
        for (int CityDaily = 1; CityDaily <= 3 ; CityDaily++) {
            WeatherApi weatherApi = new WeatherApi();
            TotalWeather weatherData = weatherApi.getWeatherData(cityId);
            List<TotalWeather.DailyWeather> daily = weatherData.getDaily();
            TotalWeather.DailyWeather dailyWeather = daily.get(CityDaily - 1);

            String sql = "update weather_predict.weather " +
                    "set fx_date = ? , temp_max = ? , temp_min = ? , text_day = ? " +
                    "where city_id = ? and city_daily = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,dailyWeather.getFxDate());
            preparedStatement.setString(2,dailyWeather.getTempMax());
            preparedStatement.setString(3,dailyWeather.getTempMin());
            preparedStatement.setString(4,dailyWeather.getTextDay());
            preparedStatement.setString(5,cityId);
            preparedStatement.setInt(6,CityDaily);

            int update = preparedStatement.executeUpdate();
            if (update == 0){
                return false;
            }
        }
        return true;
    }

    //用户查询数据库
    public List<DailyWeatherInfo> getCityWeather(String location1,String location2) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        String sql = "select weather.fx_date, weather.temp_min ,weather.temp_max , weather.text_day from weather_predict.weather where weather_predict.weather.city_id = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        LocationApi locationApi = new LocationApi();
        String cityId = locationApi.getLocationData(location1, location2);
        preparedStatement.setString(1, cityId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.getRow() == 0){
            //生成三行新的城市的数据
            String sql2 = "insert into weather_predict.weather(city_id, city_daily) values(?,1)";
            String sql3 = "insert into weather_predict.weather(city_id, city_daily) values(?,2)";
            String sql4 = "insert into weather_predict.weather(city_id, city_daily) values(?,3)";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
            PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
            preparedStatement2.setString(1,cityId);
            preparedStatement3.setString(1,cityId);
            preparedStatement4.setString(1,cityId);
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();
            preparedStatement4.executeUpdate();
            boolean b = updateCityWeather(location1 , location2);
        }

        List<DailyWeatherInfo> list = new ArrayList<>();
        ResultSet resultSet2 = preparedStatement.executeQuery();
        while (resultSet2.next()){
            String fxDate = resultSet2.getString("fx_date");
            String tempMax = resultSet2.getString("temp_max");
            String tempMin = resultSet2.getString("temp_min");
            String textDay = resultSet2.getString("text_day");
            DailyWeatherInfo dailyWeatherInfo = new DailyWeatherInfo(fxDate,tempMax,tempMin,textDay);
            list.add(dailyWeatherInfo);
        }
        return list;
    }
}
