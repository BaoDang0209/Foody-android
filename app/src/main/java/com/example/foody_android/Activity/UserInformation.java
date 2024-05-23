package com.example.foody_android.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foody_android.R;
import com.example.foody_android.callAPI.RetrofitInterface;
import com.example.foody_android.model.LoginResult;
import com.example.foody_android.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInformation extends Fragment {
    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://192.168.1.5:3001/";

    private String authToken;

    private EditText username, fullname, phone, email;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_information, container, false);

        // Initialize TextViews
        username = username.findViewById(R.id.user_name);
        fullname = fullname.findViewById(R.id.full_name);
        phone = phone.findViewById(R.id.phone);
        email = email.findViewById(R.id.email);


        // Get authToken from SharedPreferences
        authToken = SharedPreferencesManager.getInstance(requireContext()).getAuthToken();
        Log.d("ShipperProfileFragment", "AuthToken: " + authToken);

        // Initialize Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Call API to get user data
        Call<User> call = retrofitInterface.getUserProfile("Bearer " + authToken);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    LoginResult user = response.body().getLoginResult();
                    if (user != null) {
                        // Display data in TextViews
                        username.setText(user.getUsername());
                        email.setText(user.getEmail());
                        //phone.setText(user.getPhone());
                        //fullname.setText((user.getFullname()));


                    }
                } else {
                    Log.e("UserInformation", "Response error: " + response.message());
                    // Handle the case where the response is not successful
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("UserInformation", "API call failed: ", t);
                // Handle failure
            }
        });
        return v;


    }
}
