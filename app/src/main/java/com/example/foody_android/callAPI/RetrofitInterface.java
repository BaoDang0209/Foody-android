package com.example.foody_android.callAPI;

import com.example.foody_android.model.LoginResult;
import com.example.foody_android.model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
public interface RetrofitInterface {
    @POST("/api/auth/signin")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);
    @POST("/api/auth/signup")
    Call<LoginResult> executeSignup (@Body HashMap<String, String> map);
    @POST("/api/user/otp/verify-otp")
    Call<LoginResult> verifyOtp(@Body HashMap<String, String> map);
    @POST("/api/user/otp/send-otp")
    Call<LoginResult> sendOTP(@Body HashMap<String, String> map);

    @GET("/api/test/user")
    Call<User> getUserProfile(@Header("Authorization") String authToken);

}
