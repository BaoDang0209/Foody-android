package com.example.foody_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foody_android.Adapter.ResFoodAdapter;
import com.example.foody_android.R;
import com.example.foody_android.callAPI.RetrofitInterface;
import com.example.foody_android.model.Food;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListFoodActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ResFoodAdapter adapter;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;

    private boolean isSearch = false;
    private static final String BASE_URL = "http://192.168.1.5:3001/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        recyclerView = findViewById(R.id.foodListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        Intent intent = getIntent();
        isSearch = intent.getBooleanExtra("isSearch", false);
        if (isSearch) {
            String searchTxt = intent.getStringExtra("searchTxt");
            fetchSearch(searchTxt);
        } else {
            int restaurantId = intent.getIntExtra("restaurant_id", -1);
            if (restaurantId != -1) {
                fetchFoods(restaurantId);
            } else {
                showError("Restaurant ID is missing");
            }
        }
    }

    private void fetchSearch(String searchTxt) {
        Call<List<Food>> call = retrofitInterface.getFoodByName(searchTxt);

        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    List<Food> foods = response.body();
                    if (foods != null && !foods.isEmpty()) {
                        setupRecyclerView(foods);
                    } else {
                        showError("No matching food items found");
                    }
                } else {
                    showError("Failed to retrieve data");
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable throwable) {
                showError("An error occurred: " + throwable.getMessage());
            }
        });
    }

    private void fetchFoods(int restaurantId) {
        Call<List<Food>> call = retrofitInterface.getFoodsByRestaurant(restaurantId);

        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    List<Food> foodList = response.body();
                    if (foodList != null) {
                        setupRecyclerView(foodList);
                    } else {
                        showError("No data available");
                    }
                } else {
                    showError("Failed to retrieve data");
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                showError("An error occurred: " + t.getMessage());
            }
        });
    }

    private void setupRecyclerView(List<Food> foodList) {
        adapter = new ResFoodAdapter(this, foodList);
        recyclerView.setAdapter(adapter);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
