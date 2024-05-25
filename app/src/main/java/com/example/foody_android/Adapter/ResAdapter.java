package com.example.foody_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.foody_android.R;
import com.example.foody_android.Model.Restaurant;
import java.util.List;

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.ResViewHolder> {

    private List<Restaurant> restaurantList;
    private Context context;
    private OnRestaurantItemClickListener onRestaurantItemClickListener;

    public interface OnRestaurantItemClickListener {
        void onRestaurantItemClick(int restaurantId);
    }

    public ResAdapter(Context context, List<Restaurant> restaurantList, OnRestaurantItemClickListener onRestaurantItemClickListener) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.onRestaurantItemClickListener = onRestaurantItemClickListener;
    }

    @NonNull
    @Override
    public ResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_res, parent, false);
        return new ResViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.restaurantName.setText(restaurant.getName());
        holder.restaurantKind.setText(restaurant.getKind());
        holder.restaurantAddress.setText(restaurant.getAddress());
        holder.itemView.setOnClickListener(v -> {
            if (onRestaurantItemClickListener != null) {
                onRestaurantItemClickListener.onRestaurantItemClick(restaurant.getId());
            }
        });
        Glide.with(context).load(restaurant.getImage())
                .fitCenter().into(holder.pic);
    }


    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    static class ResViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName,restaurantKind,restaurantAddress;
        ImageView pic;

        public ResViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.titleTxt);
            restaurantKind = itemView.findViewById(R.id.reskindTxt);
            restaurantAddress=itemView.findViewById(R.id.addressTxt);
            pic=itemView.findViewById(R.id.pic);

        }
    }
}
