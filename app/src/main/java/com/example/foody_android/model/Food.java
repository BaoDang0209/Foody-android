package com.example.foody_android.model;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("id")
    private int id;

    @SerializedName("restaurant_id")
    private int restaurantId;

    @SerializedName("item_name")
    private String itemName;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private String price;

    @SerializedName("category")
    private String category;

    @SerializedName("image")
    private String image;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    public int getId() {
        return id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}