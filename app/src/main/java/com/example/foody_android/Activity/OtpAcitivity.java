package com.example.foody_android.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class OtpAcitivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    //private static final String BASE_URL = "http://192.168.1.5:3001/";
    private static final String BASE_URL = "http://10.0.2.2:3001/";
    private Button btnVerify;

    private TextView reSend;
    private EditText[] otpFields = new EditText[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_acitivity);

        // Initialize the OTP EditText fields
        otpFields[0] = findViewById(R.id.inputCode1);
        otpFields[1] = findViewById(R.id.inputCode2);
        otpFields[2] = findViewById(R.id.inputCode3);
        otpFields[3] = findViewById(R.id.inputCode4);
        otpFields[4] = findViewById(R.id.inputCode5);
        otpFields[5] = findViewById(R.id.inputCode6);

        btnVerify = findViewById(R.id.buttonVerify);

        reSend = findViewById(R.id.textResendOTP);

        String email = getIntent().getStringExtra("email");

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);
        btnVerify.setOnClickListener(v -> handleVerify(email)); // Pass email to handleVerify()

        reSend.setOnClickListener(v -> handleResendOtp(email)); //reSend email to handleResendOtp()
    }

    private void handleVerify(String email) {
        // Combine the OTP values from the EditText fields
        StringBuilder otp = new StringBuilder();
        for (EditText otpField : otpFields) {
            otp.append(otpField.getText().toString());
        }

        // Check if OTP is empty
        if (otp.toString().isEmpty()) {
            Toast.makeText(OtpAcitivity.this, "Please enter OTP", Toast.LENGTH_LONG).show();
            return;
        }

        // Prepare the request body (map) for API call
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("otp", otp.toString());

        Call<LoginResult> call = retrofitInterface.verifyOtp(map);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.code() ==200 ) {
                    LoginResult result = response.body();
                    AlertDialog.Builder builder = new AlertDialog.Builder(OtpAcitivity.this);
                                // Redirect to ShipperPanelBottomNavigationActivity
                                Intent intent = new Intent(OtpAcitivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();  // Optional: finish the current activity

                    // Save user session here if needed
                } else if (response.code() == 401) {
                    Toast.makeText(OtpAcitivity.this, "Wrong Credentials", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OtpAcitivity.this, "OTP invalid, please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(OtpAcitivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }
    private void handleResendOtp(String email) {
        // Prepare the request body (map) for API call
        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);

        Call<LoginResult> call = retrofitInterface.sendOTP(map);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OtpAcitivity.this, "OTP resent successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OtpAcitivity.this, "Failed to resend OTP, please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Toast.makeText(OtpAcitivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
