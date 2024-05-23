package com.example.foody_android.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.foody_android.R;
import com.example.foody_android.callAPI.RetrofitInterface;
import com.example.foody_android.model.Food;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodDetailActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://192.168.1.3:3001/";

    private TextView foodName, description, price;
    private AppCompatButton orderBTN;
    private ImageView backBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        foodName = findViewById(R.id.food_name);
        description = findViewById(R.id.descriptionTxt);
        price = findViewById(R.id.priceTxt);

        orderBTN = findViewById(R.id.orderBtn);
        backBTN = findViewById(R.id.backBtn);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        backBTN.setOnClickListener(v -> finish());

        // Get the food ID passed from the previous activity
        int foodId = getIntent().getIntExtra("FOOD_ID", -1); // Default value -1 if not found
        if (foodId != -1) {
            fetchFoodDetails(foodId);
        } else {
            Toast.makeText(this, "Food ID is missing or invalid", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no ID is passed or is invalid
        }

        orderBTN.setOnClickListener(v -> handleOrder());
    }

    private void fetchFoodDetails(int foodId) {
        Call<Food> call = retrofitInterface.getFoodByID(foodId);
        call.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Food food = response.body();
                    foodName.setText(food.getItemName());
                    description.setText(food.getDescription());
                    price.setText(String.valueOf(food.getPrice()));
                } else {
                    Toast.makeText(FoodDetailActivity.this, "Failed to retrieve food details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                Toast.makeText(FoodDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleOrder() {
        // Handle the order button click event
    }
}
