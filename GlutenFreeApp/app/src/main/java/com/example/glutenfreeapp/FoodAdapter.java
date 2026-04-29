package com.example.glutenfreeapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<FoodItem> foodList;
    private OnItemClickListener listener;
    private OnFavoriteClickListener favoriteListener;

    public interface OnItemClickListener {
        void onItemClick(FoodItem foodItem);
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClick(FoodItem foodItem, int position);
    }

    public FoodAdapter(List<FoodItem> foodList, OnItemClickListener listener, OnFavoriteClickListener favoriteListener) {
        this.foodList = foodList;
        this.listener = listener;
        this.favoriteListener = favoriteListener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item_layout, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        FoodItem foodItem = foodList.get(position);
        holder.foodName.setText(foodItem.getName());
        holder.foodDescription.setText(foodItem.getDescription());
        holder.foodIcon.setImageResource(foodItem.getIconResId());
        holder.categoryText.setText(foodItem.getCategory());
        
        // Show safe/unsafe badge
        if (foodItem.isSafe()) {
            holder.safeBadge.setText("GF");
            holder.safeBadge.setBackgroundResource(R.drawable.gf_badge_green);
        } else {
            holder.safeBadge.setText("X");
            holder.safeBadge.setBackgroundResource(R.drawable.gf_badge_red);
        }

        // Update favorite icon
        if (foodItem.isFavorite()) {
            holder.favoriteBtn.setText("★");
        } else {
            holder.favoriteBtn.setText("☆");
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(foodItem);
            }
        });

        holder.favoriteBtn.setOnClickListener(v -> {
            if (favoriteListener != null) {
                foodItem.setFavorite(!foodItem.isFavorite());
                notifyItemChanged(position);
                favoriteListener.onFavoriteClick(foodItem, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        TextView foodDescription;
        TextView categoryText;
        TextView safeBadge;
        Button favoriteBtn;
        ImageView foodIcon;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            foodDescription = itemView.findViewById(R.id.foodDescription);
            foodIcon = itemView.findViewById(R.id.foodIcon);
            categoryText = itemView.findViewById(R.id.categoryText);
            safeBadge = itemView.findViewById(R.id.safeBadge);
            favoriteBtn = itemView.findViewById(R.id.favoriteBtn);
        }
    }
}
