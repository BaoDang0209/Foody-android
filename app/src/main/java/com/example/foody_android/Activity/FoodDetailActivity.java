package com.example.foody_android.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.foody_android.R;
import com.example.foody_android.CallAPI.RetrofitInterface;
import com.example.foody_android.Model.Address;
import com.example.foody_android.Model.Food;

import com.example.foody_android.Model.LoginResult;
import com.example.foody_android.Model.Order;

import java.io.IOException;
import java.util.HashMap;

import com.example.foody_android.Model.Restaurant;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodDetailActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;



    private static final String BASE_URL = "http://192.168.1.8:3001/";

    
    private TextView foodName, description, price, quality, total, minusBtn, plusBtn, resAddress;
    private AppCompatButton orderBTN;
    private EditText userAddress, phoneNum;

    private ImageView backBTN;
    private int foodId,userID;
    private ImageView foodImg;

    private String authToken;


    private LoginResult loginResult;

    private String fullname, phone;

    private String orderId;


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
        userAddress = findViewById(R.id.userAddress);

        authToken = SharedPreferencesManager.getInstance(this).getAuthToken();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        retrofitInterface = retrofit.create(RetrofitInterface.class);

        //Get authToken from SharedPreferences


        backBTN.setOnClickListener(v -> finish());

        // Get the food ID passed from the previous activity
            foodId = getIntent().getIntExtra("FOOD_ID", -1); // Default value -1 if not found
        foodId = getIntent().getIntExtra("FOOD_ID", -1); // Default value -1 if not found
        int foodId = getIntent().getIntExtra("FOOD_ID", -1); // Default value -1 if not found
        if (foodId != -1) {
            fetchFoodDetails(foodId);
        } else {
            Toast.makeText(this, "Food ID is missing or invalid", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no ID is passed or is invalid
        }

        orderBTN.setOnClickListener(v1 ->
        {
            handleOrder();
        });

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
        Call<Food> callFood = retrofitInterface.getFoodByID(foodId);
        callFood.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> callFood, Response<Food> response) {
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
            public void onFailure(Call<Food> callFood, Throwable t) {
                showError("Error: " + t.getMessage());
            }
        });
    }

    private void fetchAddressRestaurant(int id) {
        Call<Restaurant> callRes = retrofitInterface.getRestaurantById(id);
        callRes.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> callRes, Response<Restaurant> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Restaurant restaurant = response.body();
                    int addressId = restaurant.getAddressId();
                    getAddressForRestaurant(addressId);
                } else {
                    showError("Failed to retrieve restaurant details");
                }
            }

            @Override
            public void onFailure(Call<Restaurant> callRes, Throwable t) {
                showError("Error: " + t.getMessage());
            }
        });
    }

    private void getAddressForRestaurant(int addressId) {
        Call<Address> callAddress = retrofitInterface.getAddressId(addressId);
        callAddress.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> callAddress, Response<Address> response) {
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
            public void onFailure(Call<Address> callAddress, Throwable t) {
                showError("Error: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(FoodDetailActivity.this, message, Toast.LENGTH_SHORT).show();
        Log.e("FoodDetailActivity", message);
    }

    private void handleOrder() {
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        // Call API to get user data
        Call<LoginResult> callUser = retrofitInterface.getUserProfile("Bearer " + authToken);
        callUser.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> callUser, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    LoginResult user = response.body();
                    if (user != null) {
                        fullname = user.getFullname();
                        phone = user.getPhone_number();

                        HashMap<String, String> map = new HashMap<>();
                        map.put("menuItemId", String.valueOf(foodId));
                        map.put("quantity", String.valueOf(Integer.parseInt(quality.getText().toString())));
                        map.put("phoneNumber", phone);
                        map.put("receiverName", fullname);
                        map.put("from_address", resAddress.getText().toString());
                        map.put("to_address", userAddress.getText().toString());

                        // Gọi API addOrder với HashMap map
                        Call<Order> call = retrofitInterface.addOrder("Bearer " + authToken, map);
                        call.enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                Log.d("OrderProcess", "onResponse called");
                                if (response.code() == 200) {
                                    Order order = response.body();
                                   // showOrderInfo(order);

                                    showOrderInfo(order);




                                } else {
                                    Log.d("OrderResponse", "Response not successful: " + response.code());
                                    if (response.errorBody() != null) {
                                        try {
                                            Log.d("OrderResponse", "Error body: " + response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Toast.makeText(FoodDetailActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable t) {
                                Log.d("OrderProcess", "onFailure called with message: " + t.getMessage());
                                Toast.makeText(FoodDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Log.e("UserInformation", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                Log.e("UserInformation", "API call failed: ", t);
            }
        });
    }

    private void showOrderInfo(Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Confirmation")
                .setMessage("Order placed successfully!\nOrder ID: " + order.getOrderId())
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấn nút Xác nhận
                        dialog.dismiss();
                        // Thêm mã code xử lý khi người dùng xác nhận đơn hàng ở đây (nếu cần)
                        HashMap<String, Integer> map = new HashMap<>();
                        map.put("order", order.getOrderId());
                        Call<Order> call = retrofitInterface.notifyShipper("Bearer " + authToken, map);
                        call.enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {

                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable throwable) {

                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng nhấn nút Không xác nhận hoặc Cancel
                        dialog.dismiss();
                        // Thêm mã code xử lý khi người dùng không xác nhận đơn hàng ở đây (nếu cần)
                    }
                })
                .show();
    }


   /*private void showOrderInfo(Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Details");
        builder.setMessage("Order ID: " + order.get() + "\n"
                + "Food ID: " + order.getMenuItemId() + "\n"
                + "Quantity: " + order.getQuality() + "\n"
               + "Price: " + order.getPrice() + "\n");

        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
       });

        AlertDialog dialog = builder.create();
        dialog.show();
    }*/
}
