package com.example.foody_android.CallAPI;

import com.example.foody_android.Model.Address;
import com.example.foody_android.Model.Food;
import com.example.foody_android.Model.LoginResult;
import com.example.foody_android.Model.Order;
import com.example.foody_android.Model.Restaurant;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitInterface {
    @POST("/api/auth/signin")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/api/auth/signup")
    Call<LoginResult> executeSignup(@Body HashMap<String, String> map);

    @POST("/api/auth/forgotPassword")
    Call<LoginResult> executeForgetPassWord(@Body HashMap<String, String> map);

    @POST("/api/user/otp/verify-otp")
    Call<LoginResult> verifyOtp(@Body HashMap<String, String> map);

    @POST("/api/auth/verifyOTP")
    Call<LoginResult> verifyOtpforgetPass(@Body HashMap<String, String> map);

    @POST("/api/user/otp/send-otp")
    Call<LoginResult> sendOTP(@Body HashMap<String, String> map);

    @POST("/api/orders/addorder")
    Call<Order> addOrder(@Body HashMap<String, String> map);


    @GET("/api/auth/getUserProfile")
    Call<LoginResult> getUserProfile(@Header("Authorization") String authToken);


    @PUT("/api/auth/editProfile")
    Call<LoginResult> updateUserProfile(@Header("Authorization") String authToken,@Body HashMap<String, String> map);

    @GET("/api/restaurants/getall")
    Call<List<Restaurant>> getRestaurants(@QueryMap Map<String, String> options);

    @GET("/api/menu_items/getbyrestaurant/{restaurantId}")
    Call<List<Food>> getFoodsByRestaurant(@Path("restaurantId") int restaurantId);

    @GET("api/menu_items/getall")
    Call<List<Food>> getAllFoodItems();

    @GET("/api/address/getaddressId/{addressId}")
    Call<Address> getAddressId(@Path("addressId") int addressId);

    @GET("/api/menu_items/getbyid/{FOOD_ID}")
    Call<Food> getFoodByID(@Path("FOOD_ID") int id);

    @GET("/api/restaurants/")
    Call<Restaurant> getRestaurantById(@Query("id") int id);

    @GET("/api/menu_items/getItembyName/{searchkeyword}")
    Call<List<Food>> getFoodByName(@Path("searchkeyword") String searchkeyword);

    @POST("/api/orders/addorder")
    Call<Order> addOrder(@Header("Authorization") String authToken,@Body HashMap<String, String> map);
    @POST("/api/notification")
    Call<Order> notifyShipper(@Header("Authorization") String authToken,@Body HashMap<String, Integer> map);

}

