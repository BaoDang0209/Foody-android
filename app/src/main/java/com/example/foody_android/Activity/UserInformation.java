package com.example.foody_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foody_android.R;
import com.example.foody_android.CallAPI.RetrofitInterface;
import com.example.foody_android.Model.LoginResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInformation extends Fragment {
    private RetrofitInterface retrofitInterface;

   // private static final String BASE_URL = "http://10.0.2.2:3001/";
   private static final String BASE_URL = "http://192.16.1.2:3001/";

    private String authToken;

    TextView txt_edit_info;

    private ImageView backBtn;
    private TextView logoutBtn;

    private EditText username, fullname, phone, email;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_information, container, false);

        // Initialize TextViews
        username = v.findViewById(R.id.user_name);
        fullname = v.findViewById(R.id.full_name);
        phone = v.findViewById(R.id.phone);
        email = v.findViewById(R.id.email);






         //Get authToken from SharedPreferences
        authToken = SharedPreferencesManager.getInstance(requireContext()).getAuthToken();
        Log.d("ShipperProfileFragment", "AuthToken: " + authToken);

        // Initialize Retrofit


        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Call API to get user data
        Call<LoginResult> call = retrofitInterface.getUserProfile("Bearer " + authToken);
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    LoginResult user = response.body();
                    if (user != null) {
                        // Display data in TextViews
                        username.setText(user.getUsername());
                        email.setText(user.getEmail());
                        phone.setText(user.getPhone_number());
                        fullname.setText((user.getFullname()));
                    }
                } else {
                    Log.e("UserInformation", "Response error: " + response.message());
                    // Handle the case where the response is not successful
                }
            }


            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.e("UserInformation", "API call failed: ", t);
            }
        });

        txt_edit_info =v.findViewById(R.id.editinfo);
        txt_edit_info.setOnClickListener(v1->
        {handleEditProfile();
        });
        return v;



    }


    private void handleEditProfile() {

        String susername = username.getText().toString().trim();
        String semail = email.getText().toString().trim();
        String sphone = phone.getText().toString().trim();
        String sfullname = fullname.getText().toString().trim();


        authToken = SharedPreferencesManager.getInstance(requireContext()).getAuthToken();

        // Initialize Retrofit


        retrofitInterface = retrofit.create(RetrofitInterface.class);
        HashMap<String, String> map = new HashMap<>();
        map.put("username", susername);
        map.put("email", semail);
        map.put("phone_number", sphone);
        map.put("fullname", sfullname);

        Call<LoginResult> call2 = retrofitInterface.updateUserProfile("Bearer " + authToken, map);

        call2.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call2, Response<LoginResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResult result = response.body();

                    // Save authToken into SharedPreferences
                    Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_LONG).show();
                    // Navigate to ShipperPanelBottomNavigationActivity

                } else if (response.code() == 401) {
                    Toast.makeText(getContext(), "Wrong Credentials", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Profile update failed, please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(getContext(), "Request failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
