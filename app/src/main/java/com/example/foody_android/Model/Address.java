package com.example.foody_android.Model;

import com.google.gson.annotations.SerializedName;

public class Address {
    @SerializedName("id")
    private int id;

    @SerializedName("unit_number")
    private String unitNumber;

    @SerializedName("street_number")
    private String streetNumber;

    @SerializedName("city")
    private String city;

    @SerializedName("region")
    private String region;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    public Address(int id, String unitNumber, String streetNumber, String city, String region, String createdAt, String updatedAt) {
        this.id = id;
        this.unitNumber = unitNumber;
        this.streetNumber = streetNumber;
        this.city = city;
        this.region = region;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters (omitted for brevity)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
