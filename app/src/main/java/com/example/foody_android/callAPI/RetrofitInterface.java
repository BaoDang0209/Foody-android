package com.example.foody_android.callAPI;

import com.example.foody_android.model.Address;
import com.example.foody_android.model.Food;
import com.example.foody_android.model.LoginResult;
import com.example.foody_android.model.User;
import com.example.foody_android.model.Restaurant;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RetrofitInterface {
    @POST("/api/auth/signin")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/auth/signup")
    Call<LoginResult> executeSignup(@Body HashMap<String, String> map);

    @POST("/api/user/otp/verify-otp")
    Call<LoginResult> verifyOtp(@Body HashMap<String, String> map);

    @POST("/api/user/otp/send-otp")
    Call<LoginResult> sendOTP(@Body HashMap<String, String> map);


    @GET("/api/test/user")
    Call<User> getUserProfile(@Header("Authorization") String authToken);

    @GET("/api/restaurants/getall")
    Call<List<Restaurant>> getRestaurants(@QueryMap Map<String, String> options);

    @GET("/api/menu_items/getbyrestaurant/{restaurantId}")
    Call<List<Food>> getFoodsByRestaurant(@Path("restaurantId") int restaurantId);

    @GET("api/menu_items/getall")
    Call<List<Food>> getAllFoodItems();

    @GET("/api/address/getaddressId/{addressId}")
    Call<Address> getAddressId(@Path("addressId") int addressId);

}

