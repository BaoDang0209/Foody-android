package com.example.foody_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foody_android.Activity.FoodDetailActivity;
import com.example.foody_android.R;
import com.example.foody_android.Model.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList;
    private Context context;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.titleTxt.setText(food.getItemName());
        // Assuming price is a string, if it is a number, it should be formatted as needed
        String formattedPrice = String.format("%.2f", Double.parseDouble(food.getPrice()));
        holder.priceTxt.setText(formattedPrice+" đ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, FoodDetailActivity.class);
                intent.putExtra("FOOD_ID", food.getId()); // Pass food ID as an integer extra
                context.startActivity(intent);

            }
        });
        // Assuming food.getImageUrl() returns the URL of the food image.
        // Uncomment the line below if you have image URL
        Glide.with(context).load(food.getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, priceTxt;
        ImageView img;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            img = itemView.findViewById(R.id.img);
        }
    }
}