package com.example.foody_android.model;

import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("id")
    private int id;

    @SerializedName("restaurant_name")
    private String name;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("address_id")
    private int addressId;

    @SerializedName("restaurant_kind")
    private String kind;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    // Getters and Setters (if needed)
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getUserId() {
        return userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getKind() {
        return kind;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}