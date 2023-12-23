package com.sty.daoWeatherDatebaseSystem;
//这是一个实体对象，用来存放城市的三日天气数据
public class DailyWeatherInfo {
    private String fxDate;   // 当日日期
    private String tempMax;  // 当日最高气温
    private String tempMin;  // 当日最低气温
    private String textDay;  // 白天天气情况

    public DailyWeatherInfo(String fxDate, String tempMax, String tempMin, String textDay) {
        this.fxDate = fxDate;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.textDay = textDay;
    }

    public String getFxDate() {
        return fxDate;
    }

    public void setFxDate(String fxDate) {
        this.fxDate = fxDate;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getTextDay() {
        return textDay;
    }

    public void setTextDay(String textDay) {
        this.textDay = textDay;
    }

    @Override
    public String toString() {
        return "DailyWeatherInfo{" +
                "fxDate='" + fxDate + '\'' +
                ", tempMax='" + tempMax + '\'' +
                ", tempMin='" + tempMin + '\'' +
                ", textDay='" + textDay + '\'' +
                '}';
    }
}
