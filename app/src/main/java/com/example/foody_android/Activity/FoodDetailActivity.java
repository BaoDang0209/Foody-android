package com.example.foody_android.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.foody_android.R;
import com.example.foody_android.callAPI.RetrofitInterface;
import com.example.foody_android.model.Address;
import com.example.foody_android.model.Food;

import com.example.foody_android.model.Order;

import java.util.HashMap;

import com.example.foody_android.model.Restaurant;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodDetailActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    //private static final String BASE_URL = "http://192.168.1.3:3001/";
    private static final String BASE_URL = "http://192.168.1.5:3001/";
    // static final String BASE_URL = "http://192.168.1.5:3001/";
    
    private TextView foodName, description, price, quality, total, minusBtn, plusBtn, resAddress;
    private AppCompatButton orderBTN;
    private ImageView backBTN;
    private int foodId;
    private ImageView foodImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        foodName = findViewById(R.id.food_name);
        description = findViewById(R.id.descriptionTxt);
        price = findViewById(R.id.priceTxt);
        quality = findViewById(R.id.numTxt);
        total = findViewById(R.id.totalTxt);
        minusBtn = findViewById(R.id.minusBtn);
        plusBtn = findViewById(R.id.plusBtn);
        resAddress = findViewById(R.id.resAddress);
        foodImg = findViewById(R.id.pic);
        orderBTN = findViewById(R.id.orderBtn);
        backBTN = findViewById(R.id.backBtn);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        backBTN.setOnClickListener(v -> finish());

        // Get the food ID passed from the previous activity
        foodId = getIntent().getIntExtra("FOOD_ID", -1); // Default value -1 if not found
        int foodId = getIntent().getIntExtra("FOOD_ID", -1); // Default value -1 if not found
        if (foodId != -1) {
            fetchFoodDetails(foodId);
        } else {
            Toast.makeText(this, "Food ID is missing or invalid", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no ID is passed or is invalid
        }

        orderBTN.setOnClickListener(v -> handleOrder());

        minusBtn.setOnClickListener(v -> decreaseQuantity());
        plusBtn.setOnClickListener(v -> increaseQuantity());
    }

    private void calculateAndUpdateTotal() {
        double itemPrice = Double.parseDouble(price.getText().toString());
        int itemQuality = Integer.parseInt(quality.getText().toString());
        double totalValue = itemPrice * itemQuality;
        total.setText(String.valueOf(totalValue));
    }

    private void decreaseQuantity() {
        int currentQuality = Integer.parseInt(quality.getText().toString());
        if (currentQuality > 1) {
            currentQuality--;
            quality.setText(String.valueOf(currentQuality));
            calculateAndUpdateTotal();
        }
    }

    private void increaseQuantity() {
        int currentQuality = Integer.parseInt(quality.getText().toString());
        currentQuality++;
        quality.setText(String.valueOf(currentQuality));
        calculateAndUpdateTotal();
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
                    total.setText(String.valueOf(food.getPrice()));
                    Glide.with(FoodDetailActivity.this).load(food.getImage()).fitCenter().into(foodImg);
                    int restaurantID = food.getRestaurantId();
                    fetchAddressRestaurant(restaurantID);
                } else {
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                showError("Error: " + t.getMessage());
            }
        });
    }

    private void fetchAddressRestaurant(int id) {
        Call<Restaurant> call = retrofitInterface.getRestaurantById(id);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Restaurant restaurant = response.body();
                    int addressId = restaurant.getAddressId();
                    getAddressForRestaurant(addressId);
                } else {
                    showError("Failed to retrieve restaurant details");
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                showError("Error: " + t.getMessage());
            }
        });
    }

    private void getAddressForRestaurant(int addressId) {
        Call<Address> call = retrofitInterface.getAddressId(addressId);
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Address address = response.body();
                    String fullAddress = address.getUnitNumber() + ", " +
                            address.getStreetNumber() + ", " +
                            address.getCity() + ", " +
                            address.getRegion();
                    resAddress.setText(fullAddress);
                } else {
                    showError("Failed to retrieve address details");
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                showError("Error: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(FoodDetailActivity.this, message, Toast.LENGTH_SHORT).show();
        Log.e("FoodDetailActivity", message);
    }

    private void handleOrder() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("id", foodId);
        map.put("quality", Integer.parseInt(quality.getText().toString()));

        Call<Order> call = retrofitInterface.addOrder(map);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Order order = response.body();
                    showOrderInfo(order);
                } else {
                    // Hiển thị thông báo lỗi
                    Toast.makeText(FoodDetailActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                // Hiển thị thông báo lỗi
                Toast.makeText(FoodDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showOrderInfo(Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Details");
        builder.setMessage("Order ID: " + order.getId() + "\n"
                + "Food ID: " + order.getId() + "\n"
                + "Quality: " + order.getQuality() + "\n"
                + "Price: " + order.getPrice());

        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss(); // Đóng dialog khi người dùng nhấn OK
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
