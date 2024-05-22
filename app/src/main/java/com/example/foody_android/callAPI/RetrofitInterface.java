package com.example.foody_android.callAPI;

import com.example.foody_android.model.LoginResult;
<<<<<<< HEAD
import com.example.foody_android.model.User;
=======
import com.example.foody_android.model.Restaurant;
>>>>>>> a7965b695a19e68b0acd50a2009f14d898a6079d

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
<<<<<<< HEAD
import retrofit2.http.Header;
=======
>>>>>>> a7965b695a19e68b0acd50a2009f14d898a6079d
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

<<<<<<< HEAD
    @GET("/api/test/user")
    Call<User> getUserProfile(@Header("Authorization") String authToken);

=======
    @GET("/api/restaurants")
    Call<List<Restaurant>> getRestaurants(@QueryMap Map<String, String> options);
>>>>>>> a7965b695a19e68b0acd50a2009f14d898a6079d
}
