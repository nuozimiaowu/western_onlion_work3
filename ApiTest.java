import com.sty.daoWeatherDatebaseSystem.DailyWeatherInfo;
import com.sty.daoWeatherDatebaseSystem.WeatherSelectionDto;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;

public class ApiTest {

    @Test//测试更新城市的信息
    public void TestUpdateCityWeather() throws SQLException {
        WeatherSelectionDto weatherSelectionDto = new WeatherSelectionDto();
        boolean b = weatherSelectionDto.updateCityWeather("shanghai","shanghai");
        if (b == false)
            System.out.println("更新失败");
        else
            System.out.println("更新成成功");
    }

    //测试用户获取城市的三日天气
    @Test
    public void TestGetCityWeather() throws SQLException {
        WeatherSelectionDto weatherSelectionDto = new WeatherSelectionDto();
        List<DailyWeatherInfo> cityWeather = weatherSelectionDto.getCityWeather("fuzhou","fuzhou");
        for (DailyWeatherInfo dailyWeatherInfo : cityWeather) {
            System.out.println(dailyWeatherInfo);
        }
    }
}
