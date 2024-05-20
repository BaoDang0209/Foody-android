package com.example.foody_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foody_android.callAPI.RetrofitInterface;
import com.example.foody_android.model.LoginResult;
import android.widget.EditText;
import android.widget.Button;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://192.168.1.3:3001/";

    private Button btnSignUp;

    private TextView gotoLogin;
    private EditText inputPassword, inputEmail, inputUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputEmail = findViewById(R.id.email_edittext);
        inputPassword = findViewById(R.id.password_edittext);
        inputUserName = findViewById(R.id.username_edittext);
        btnSignUp = findViewById(R.id.create_account_btn);
        gotoLogin = findViewById(R.id.goto_login_btn);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        btnSignUp.setOnClickListener(v -> handleRegister());


    }

    private void handleRegister() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String username = inputUserName.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please fill in both fields", Toast.LENGTH_LONG).show();
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("username",username);

        Call<LoginResult> call = retrofitInterface.executeSignup(map);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResult result = response.body();
                    // Start VerifyOtpActivity and pass email as an extra
                    Intent intent = new Intent(SignUpActivity.this,Otp.class);
                    intent.putExtra("email", email); // Pass email as an extra
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, "Register failed, please try again.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}