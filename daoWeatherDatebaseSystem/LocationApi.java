package com.sty.daoWeatherDatebaseSystem;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;
//api的调用，通过城市的地名和省份名字，获得城市的城市编号
public class LocationApi {
    public String getLocationData(String location1 , String location2) {
        StringBuffer sb = new StringBuffer();
        try {
            String apiKey = "bb764328eb96499f875dd42b9496bb7d";
            String weatherUrl = "https://geoapi.qweather.com/v2/city/lookup?location=" + location1 +"adm=" +location2 + "&key=" + apiKey;
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
        LocationInfo locationInfo = gson.fromJson(sb.toString(), LocationInfo.class);
        String id = locationInfo.getLocation().get(0).getId();
        return  id;
    }

    public static void main(String[] args) {
        LocationApi locationApi = new LocationApi();
        String id = locationApi.getLocationData("jining", "jining");
        System.out.println(id);
    }
}
