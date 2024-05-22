package com.example.foody_android.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody_android.Adapter.FoodAdapter;
import com.example.foody_android.Adapter.ResAdapter;
import com.example.foody_android.R;
import com.example.foody_android.model.Food;
import com.example.foody_android.model.Restaurant;
import com.example.foody_android.callAPI.RetrofitInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements ResAdapter.OnRestaurantItemClickListener {

    private RecyclerView recyclerView, recyclerView2;
    private ResAdapter resAdapter;
    private FoodAdapter foodAdapter;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private static final String BASE_URL = "http://192.168.15.43:3001/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        fetchRestaurants();
        fetchAllFoods();

        return view;
    }

    private void fetchRestaurants() {
        Map<String, String> options = new HashMap<>();
        options.put("key", "value"); // Replace with actual key-value pairs required by your API

        Call<List<Restaurant>> call = retrofitInterface.getRestaurants(options);

        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if (response.isSuccessful()) {
                    List<Restaurant> restaurantList = response.body();
                    if (restaurantList != null) {
                        setupResRecyclerView(restaurantList);
                    } else {
                        showError("No data available");
                        Log.e("HomeFragment", "No data available");
                    }
                } else {
                    showError("Failed to retrieve data");
                    Log.e("HomeFragment", "Failed response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                showError("An error occurred: " + t.getMessage());
                Log.e("HomeFragment", "Network call failure", t);
            }
        });
    }

    private void fetchAllFoods() {
        Call<List<Food>> call = retrofitInterface.getAllFoodItems();

        call.enqueue(new Callback<List<Food>>() {
            @Override
            public void onResponse(Call<List<Food>> call, Response<List<Food>> response) {
                if (response.isSuccessful()) {
                    List<Food> foodList = response.body();
                    if (foodList != null) {
                        setupFoodRecyclerView(foodList);
                    } else {
                        showError("No data available");
                        Log.e("HomeFragment", "No data available");
                    }
                } else {
                    showError("Failed to retrieve data");
                    Log.e("HomeFragment", "Failed response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Food>> call, Throwable t) {
                showError("An error occurred: " + t.getMessage());
                Log.e("HomeFragment", "Network call failure", t);
            }
        });
    }

    private void setupResRecyclerView(List<Restaurant> restaurantList) {
        resAdapter = new ResAdapter(getContext(), restaurantList, this);
        recyclerView.setAdapter(resAdapter);
    }

    private void setupFoodRecyclerView(List<Food> foodList) {
        foodAdapter = new FoodAdapter(getContext(), foodList);
        recyclerView2.setAdapter(foodAdapter);
    }

    @Override
    public void onRestaurantItemClick(int restaurantId) {
        Intent intent = new Intent(getActivity(), ListFoodActivity.class);
        intent.putExtra("restaurant_id", restaurantId);
        startActivity(intent);
    }

    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
