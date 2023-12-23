package com.sty.daoWeatherDatebaseSystem;

import java.util.List;
//这是一个实体对象，用来封装通过城市的名字得到的城市信息（json格式）
public class LocationInfo {
    private String code;
    private List<Location> location;
    private Refer refer;

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public static class Location {
        private String name;
        private String id;
        private String lat;
        private String lon;
        private String adm2;
        private String adm1;
        private String country;
        private String tz;
        private String utcOffset;
        private String isDst;
        private String type;
        private String rank;
        private String fxLink;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", lat='" + lat + '\'' +
                    ", lon='" + lon + '\'' +
                    ", adm2='" + adm2 + '\'' +
                    ", adm1='" + adm1 + '\'' +
                    ", country='" + country + '\'' +
                    ", tz='" + tz + '\'' +
                    ", utcOffset='" + utcOffset + '\'' +
                    ", isDst='" + isDst + '\'' +
                    ", type='" + type + '\'' +
                    ", rank='" + rank + '\'' +
                    ", fxLink='" + fxLink + '\'' +
                    '}';
        }
    }

    public static class Refer {
        private List<String> sources;
        private List<String> license;

        // Add getters and setters here
        // ...

        @Override
        public String toString() {
            return "Refer{" +
                    "sources=" + sources +
                    ", license=" + license +
                    '}';
        }
    }

    // Add getters and setters for the main class here
    // ...

    @Override
    public String toString() {
        return "LocationInfo{" +
                "code='" + code + '\'' +
                ", location=" + location +
                ", refer=" + refer +
                '}';
    }
}
