package com.sty.daoOrderSystem;

public class Order2 {
    private int id;
    private int communityid;
    private String time;
    private int amount;
    private String name;
    private String brand;

    public Order2(int id, int communityid, String time, int amount, String name, String brand) {
        this.id = id;
        this.communityid = communityid;
        this.time = time;
        this.amount = amount;
        this.name = name;
        this.brand = brand;
    }

    public Order2() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommunityid() {
        return communityid;
    }

    public void setCommunityid(int communityid) {
        this.communityid = communityid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "Order2{" +
                "id=" + id +
                ", communityid=" + communityid +
                ", time='" + time + '\'' +
                ", amount=" + amount +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
