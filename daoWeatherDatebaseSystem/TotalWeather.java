package com.sty.daoWeatherDatebaseSystem;

import java.util.List;
//这个类是json数据格式的映射，帮我们把拿到的数据封装到一个类里。
public class TotalWeather {

    private String code;
    private String updateTime;
    private String fxLink;
    private List<DailyWeather> daily;
    private Refer refer;

    @Override
    public String toString() {
        return "TotalWeather{" +
                "code='" + code + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", fxLink='" + fxLink + '\'' +
                ", daily=" + daily +
                ", refer=" + refer +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public List<DailyWeather> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyWeather> daily) {
        this.daily = daily;
    }

    public Refer getRefer() {
        return refer;
    }

    public void setRefer(Refer refer) {
        this.refer = refer;
    }

    public static class DailyWeather {
        private String fxDate;
        private String sunrise;
        private String sunset;
        private String moonrise;
        private String moonset;
        private String moonPhase;
        private String moonPhaseIcon;
        private String tempMax;
        private String tempMin;
        private String iconDay;
        private String textDay;
        private String iconNight;
        private String textNight;
        private String wind360Day;
        private String windDirDay;
        private String windScaleDay;
        private String windSpeedDay;
        private String wind360Night;
        private String windDirNight;
        private String windScaleNight;
        private String windSpeedNight;
        private String humidity;
        private String precip;
        private String pressure;
        private String vis;
        private String cloud;
        private String uvIndex;

        @Override
        public String toString() {
            return "DailyWeather{" +
                    "fxDate='" + fxDate + '\'' +
                    ", sunrise='" + sunrise + '\'' +
                    ", sunset='" + sunset + '\'' +
                    ", moonrise='" + moonrise + '\'' +
                    ", moonset='" + moonset + '\'' +
                    ", moonPhase='" + moonPhase + '\'' +
                    ", moonPhaseIcon='" + moonPhaseIcon + '\'' +
                    ", tempMax='" + tempMax + '\'' +
                    ", tempMin='" + tempMin + '\'' +
                    ", iconDay='" + iconDay + '\'' +
                    ", textDay='" + textDay + '\'' +
                    ", iconNight='" + iconNight + '\'' +
                    ", textNight='" + textNight + '\'' +
                    ", wind360Day='" + wind360Day + '\'' +
                    ", windDirDay='" + windDirDay + '\'' +
                    ", windScaleDay='" + windScaleDay + '\'' +
                    ", windSpeedDay='" + windSpeedDay + '\'' +
                    ", wind360Night='" + wind360Night + '\'' +
                    ", windDirNight='" + windDirNight + '\'' +
                    ", windScaleNight='" + windScaleNight + '\'' +
                    ", windSpeedNight='" + windSpeedNight + '\'' +
                    ", humidity='" + humidity + '\'' +
                    ", precip='" + precip + '\'' +
                    ", pressure='" + pressure + '\'' +
                    ", vis='" + vis + '\'' +
                    ", cloud='" + cloud + '\'' +
                    ", uvIndex='" + uvIndex + '\'' +
                    '}';
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

        // Getters and setters
    }

    public static class Refer {
        private List<String> sources;
        private List<String> license;

        // Getters and setters
    }
}
