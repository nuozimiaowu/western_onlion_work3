package com.sty.daoWeatherDatebaseSystem;
import com.google.gson.Gson;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;
public class WeatherApi {

    //api的调用，根据传入的城市代码，获取json格式的天气信息。
    public TotalWeather getWeatherData(String locationCode) {
        StringBuffer sb = new StringBuffer();
        try {
            String apiKey = "bb764328eb96499f875dd42b9496bb7d";
            String weatherUrl = "https://devapi.qweather.com/v7/weather/3d?key=" + apiKey + "&location=" + locationCode;
            URL url = new URL(weatherUrl);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);
            InputStreamReader isr = new InputStreamReader(gzin, "utf-8");
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(" ");
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(sb.toString(),TotalWeather.class);
    }


    public static void main(String[] args) {
        // 示例调用方法
        String locationCode = "101230101"; // 例如，福州的城市代码
        WeatherApi weatherApi = new WeatherApi();
        TotalWeather weatherData = weatherApi.getWeatherData(locationCode);
        System.out.println(weatherData);
    }
}