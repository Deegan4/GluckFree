package com.example.glutenfreeapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FoodAdapter.OnItemClickListener, FoodAdapter.OnFavoriteClickListener {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<FoodItem> foodList;
    private List<FoodItem> filteredList;
    private EditText searchEditText;
    private ImageButton searchButton;
    private Button filterAllBtn, filterSafeBtn, filterUnsafeBtn, filterFavoritesBtn;
    private LinearLayout categoryFilterLayout;
    private TextView emptyStateView;
    private DataManager dataManager;
    private FoodItem.FilterType currentFilter = FoodItem.FilterType.ALL;
    private FoodItem.Category currentCategory = FoodItem.Category.ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Check if onboarding should be shown (first launch only)
        boolean showOnboarding = !DataManager.getInstance(this).isOnboardingComplete();
        
        if (showOnboarding) {
            Intent intent = new Intent(this, OnboardingActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        filterAllBtn = findViewById(R.id.filterAllBtn);
        filterSafeBtn = findViewById(R.id.filterSafeBtn);
        filterUnsafeBtn = findViewById(R.id.filterUnsafeBtn);
        filterFavoritesBtn = findViewById(R.id.filterFavoritesBtn);
        categoryFilterLayout = findViewById(R.id.categoryFilterLayout);
        emptyStateView = findViewById(R.id.emptyStateView);

        foodList = new ArrayList<>();
        filteredList = new ArrayList<>();
        
        // Initialize DataManager for persistent storage
        dataManager = DataManager.getInstance(this);
        
        // Load foods from JSON asset
        loadFoods();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(filteredList, this, this);
        recyclerView.setAdapter(foodAdapter);

        setupSearchListener();
        setupFilterButtons();
        
        // Show all foods initially
        applyFilters();
    }
    
    private void loadFoods() {
        foodList = dataManager.loadFoods();
    }

    private void setupSearchListener() {
        // Real-time search as user types
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        searchButton.setOnClickListener(v -> {
            // Search is already real-time, but we can hide keyboard if needed
            applyFilters();
        });
    }
    
    private void setupFilterButtons() {
        filterAllBtn.setOnClickListener(v -> {
            currentFilter = FoodItem.FilterType.ALL;
            updateFilterButtons();
            applyFilters();
        });
        
        filterSafeBtn.setOnClickListener(v -> {
            currentFilter = FoodItem.FilterType.SAFE;
            updateFilterButtons();
            applyFilters();
        });
        
        filterUnsafeBtn.setOnClickListener(v -> {
            currentFilter = FoodItem.FilterType.UNSAFE;
            updateFilterButtons();
            applyFilters();
        });
        
        filterFavoritesBtn.setOnClickListener(v -> {
            currentFilter = FoodItem.FilterType.FAVORITES;
            updateFilterButtons();
            applyFilters();
        });
    }

    private void updateFilterButtons() {
        filterAllBtn.setBackgroundResource(currentFilter == FoodItem.FilterType.ALL ? R.drawable.filter_button_active : R.drawable.filter_button);
        filterSafeBtn.setBackgroundResource(currentFilter == FoodItem.FilterType.SAFE ? R.drawable.filter_button_active : R.drawable.filter_button);
        filterUnsafeBtn.setBackgroundResource(currentFilter == FoodItem.FilterType.UNSAFE ? R.drawable.filter_button_active : R.drawable.filter_button);
        filterFavoritesBtn.setBackgroundResource(currentFilter == FoodItem.FilterType.FAVORITES ? R.drawable.filter_button_active : R.drawable.filter_button);
    }

    private void applyFilters() {
        String query = searchEditText.getText().toString().toLowerCase().trim();
        filteredList.clear();
        
        for (FoodItem food : foodList) {
            // Apply safety filter using enum
            boolean matchesFilter;
            switch (currentFilter) {
                case SAFE:
                    matchesFilter = food.isSafe();
                    break;
                case UNSAFE:
                    matchesFilter = !food.isSafe();
                    break;
                case FAVORITES:
                    matchesFilter = food.isFavorite();
                    break;
                case ALL:
                default:
                    matchesFilter = true;
                    break;
            }
            
            // Apply search query with null safety
            boolean matchesSearch = query.isEmpty() || 
                (food.getName() != null && food.getName().toLowerCase().contains(query)) || 
                (food.getDescription() != null && food.getDescription().toLowerCase().contains(query)) ||
                (food.getCategory() != null && food.getCategory().toLowerCase().contains(query));
            
            if (matchesFilter && matchesSearch) {
                filteredList.add(food);
            }
        }
        
        // Update empty state visibility
        if (filteredList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyStateView.setVisibility(View.VISIBLE);
            emptyStateView.setText(getEmptyStateMessage());
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyStateView.setVisibility(View.GONE);
        }
        
        foodAdapter.notifyDataSetChanged();
    }
    
    private String getEmptyStateMessage() {
        String query = searchEditText.getText().toString().trim();
        switch (currentFilter) {
            case SAFE:
                return query.isEmpty() ? "No gluten-free foods found." : "No gluten-free foods match your search.";
            case UNSAFE:
                return query.isEmpty() ? "No gluten-containing foods found." : "No gluten-containing foods match your search.";
            case FAVORITES:
                return query.isEmpty() ? "No favorites yet. Tap ☆ to add some!" : "No favorite foods match your search.";
            case ALL:
            default:
                return "No foods match your search. Try different keywords.";
        }
    }

    @Override
    public void onItemClick(FoodItem foodItem) {
        // Open detail activity with Parcelable
        if (foodItem != null) {
            Intent intent = new Intent(this, FoodDetailActivity.class);
            intent.putExtra("food_item", foodItem);
            startActivity(intent);
        }
    }

    @Override
    public void onFavoriteClick(FoodItem foodItem, int position) {
        // Save to persistent storage using DataManager
        if (foodItem != null) {
            dataManager.toggleFavorite(foodItem);
            // If filtering by favorites, refresh the list
            if (currentFilter == FoodItem.FilterType.FAVORITES && !foodItem.isFavorite()) {
                applyFilters();
            }
        }
    }
}
