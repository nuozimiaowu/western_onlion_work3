package com.sty.daoOrderSystem;

public class Order {
    private int id;
    private int communityid;
    private String time;
    private int amount;

    public Order(int id, int communityid, String time, int amount) {
        this.id = id;
        this.communityid = communityid;
        this.time = time;
        this.amount = amount;
    }

    public Order() {
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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", communityid=" + communityid +
                ", time=" + time +
                ", price=" + amount +
                '}';
    }
}
