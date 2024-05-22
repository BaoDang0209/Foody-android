package com.example.foody_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foody_android.R;
import com.example.foody_android.model.Food;

import java.util.List;

public class ResFoodAdapter extends RecyclerView.Adapter<ResFoodAdapter.ResFoodViewHolder> {

    private List<Food> foodList;
    private Context context;

    public ResFoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ResFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_food, parent, false);
        return new ResFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResFoodViewHolder holder, int position) {
        Food foodItem = foodList.get(position);
        holder.foodNameTxt.setText(foodItem.getItemName());

        // Assuming price is a string, if it is a number, it should be formatted as needed
        String formattedPrice = String.format("%.2f", Double.parseDouble(foodItem.getPrice()));
        holder.priceTxt.setText(formattedPrice +" đ");

        // Set other views if needed
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class ResFoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodNameTxt, priceTxt;

        public ResFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodNameTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
        }
    }
}