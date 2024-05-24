package com.example.foody_android.model;

import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("id")
    private int id;

    @SerializedName("orderId")
    private int orderId;

    @SerializedName("menuItemId")
    private int menuItemId;

    @SerializedName("quality")
    private int quality;

    @SerializedName("price")
    private int price;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", menuItemId=" + menuItemId +
                ", quality=" + quality +
                ", price=" + price +
                '}';
    }
}
