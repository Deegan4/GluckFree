package com.example.glutenfreeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class FoodDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        // Get data from intent with null safety
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra("food_item")) {
            finish();
            return;
        }
        
        FoodItem foodItem = intent.getParcelableExtra("food_item");
        if (foodItem == null) {
            finish();
            return;
        }

        // Set up views
        TextView titleText = findViewById(R.id.detailTitle);
        TextView categoryText = findViewById(R.id.detailCategory);
        TextView descText = findViewById(R.id.detailDescription);
        TextView safeStatus = findViewById(R.id.safeStatus);
        Button backBtn = findViewById(R.id.backBtn);

        titleText.setText(foodItem.getName());
        categoryText.setText("Category: " + foodItem.getCategory());
        descText.setText(foodItem.getDescription());

        if (foodItem.isSafe()) {
            safeStatus.setText("✓ GLUTEN FREE - Safe to Eat");
            safeStatus.setTextColor(ContextCompat.getColor(this, R.color.green));
        } else {
            safeStatus.setText("✗ CONTAINS GLUTEN - Avoid");
            safeStatus.setTextColor(ContextCompat.getColor(this, R.color.red));
        }

        backBtn.setOnClickListener(v -> finish());
    }
}
