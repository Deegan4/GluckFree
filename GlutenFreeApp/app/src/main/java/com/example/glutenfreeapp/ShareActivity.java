package com.example.glutenfreeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity for sharing food items.
 */
public class ShareActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        
        // Get data from intent
        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra("food_name") || !intent.hasExtra("is_safe")) {
            finish();
            return;
        }
        
        String foodName = intent.getStringExtra("food_name");
        boolean isSafe = intent.getBooleanExtra("is_safe", true);
        
        TextView titleText = findViewById(R.id.shareTitle);
        TextView messageText = findViewById(R.id.shareMessage);
        Button shareButton = findViewById(R.id.shareButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        
        titleText.setText(foodName);
        
        String status = isSafe ? "✓ GLUTEN FREE" : "✗ Contains Gluten";
        String message = "Check out " + foodName + " - " + status + " #GlutenFree";
        messageText.setText(message);
        
        shareButton.setOnClickListener(v -> {
            // Create share intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, foodName);
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            finish();
        });
        
        cancelButton.setOnClickListener(v -> finish());
    }
}
