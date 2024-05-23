package com.example.foody_android.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foody_android.R;
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

public class LoginActivity extends AppCompatActivity {


    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    //private static final String BASE_URL = "http://192.168.1.5:3001/";
    private static final String BASE_URL = "http://10.0.2.2:3001/";

    private Button btnLogin;

    private TextView gotoSignUp;
    private EditText inputPassword, inputEmail;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.email_edittext);
        inputPassword = findViewById(R.id.password_edittext);
        btnLogin = findViewById(R.id.login_btn);
        gotoSignUp = findViewById(R.id.goto_signup_btn);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        btnLogin.setOnClickListener(v -> handleLogin());

        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }


    private void handleLogin() {
        String username = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please fill in both fields", Toast.LENGTH_LONG).show();
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);

        Call<LoginResult> call = retrofitInterface.executeLogin(map);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResult result = response.body();

                    String authToken = result.getToken();
                    Log.d("AuthToken", authToken);

                    // Lưu authToken vào SharedPreferences
                    SharedPreferencesManager.getInstance(LoginActivity.this).saveAuthToken(authToken);

                   // AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                    // Save user session here if needed
                } else if (response.code() == 401) {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed, please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}