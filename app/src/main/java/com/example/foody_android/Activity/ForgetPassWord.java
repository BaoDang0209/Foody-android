package com.example.foody_android.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foody_android.R;
import com.example.foody_android.callAPI.RetrofitInterface;
import com.example.foody_android.model.LoginResult;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgetPassWord extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    //private static final String BASE_URL = "http://192.168.1.5:3001/";


    private Button getOTPbtn;
    private EditText passwordTxt,emailTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_word);

        getOTPbtn = findViewById(R.id.buttonGetOTP);
        passwordTxt = findViewById(R.id.inputPassword);
        emailTxt = findViewById(R.id.inputEmail);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        getOTPbtn.setOnClickListener(v -> handleForgetPassword());
    }
    private void handleForgetPassword() {
        String email = emailTxt.getText().toString().trim();
        String newPassword = passwordTxt.getText().toString().trim();


        if (email.isEmpty() || newPassword.isEmpty() ) {
            Toast.makeText(ForgetPassWord.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("newPassword", newPassword);

        Call<LoginResult> call = retrofitInterface.executeForgetPassWord(map);


        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.code() == 200 ) {
                    // Start VerifyOtpActivity and pass email as an extra
                    Intent intent = new Intent(ForgetPassWord.this, OtpAcitivity.class);
                    intent.putExtra("email", email); // Pass email as an extra
                    intent.putExtra("forgot", "1"); // Pass email as an extra
                    startActivity(intent);
                } else {
                    Toast.makeText(ForgetPassWord.this, "Failed, please try again.", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(ForgetPassWord.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}