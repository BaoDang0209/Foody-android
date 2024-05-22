package com.example.foody_android.callAPI;

import com.example.foody_android.model.LoginResult;
import com.example.foody_android.model.Restaurant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("/api/restaurants")
    Call<List<Restaurant>> getRestaurants(@QueryMap Map<String, String> options);
}
