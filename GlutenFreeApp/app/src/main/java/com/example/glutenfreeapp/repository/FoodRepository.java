package com.example.glutenfreeapp.repository;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.glutenfreeapp.DataManager;
import com.example.glutenfreeapp.FoodItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.withContext;

/**
 * Repository for food data operations.
 * Provides a clean API for data access using coroutines and Flow.
 */
@Singleton
public class FoodRepository {

    private static final String TAG = "FoodRepository";
    
    private final DataManager dataManager;
    private final SharedPreferences sharedPreferences;
    private final ExecutorService executorService;
    
    // StateFlow for reactive UI updates
    private final MutableStateFlow<List<FoodItem>> allFoods = new MutableStateFlow<>(new ArrayList<>());
    private final MutableStateFlow<Boolean> isLoading = new MutableStateFlow<>(false);
    private final MutableStateFlow<String> error = new MutableStateFlow<>(null);

    @Inject
    public FoodRepository(DataManager dataManager, SharedPreferences sharedPreferences) {
        this.dataManager = dataManager;
        this.sharedPreferences = sharedPreferences;
        this.executorService = Executors.newFixedThreadPool(4);
    }

    /**
     * Get all foods as StateFlow for reactive observation.
     */
    public StateFlow<List<FoodItem>> getAllFoods() {
        return allFoods;
    }

    /**
     * Get loading state as StateFlow.
     */
    public StateFlow<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * Get error state as StateFlow.
     */
    public StateFlow<String> getError() {
        return error;
    }

    /**
     * Load foods from JSON asset file using coroutines.
     * Should be called from a coroutine scope.
     */
    public async void loadFoods(CoroutineScope scope) {
        isLoading.setValue(true);
        error.setValue(null);
        
        try {
            List<FoodItem> foods = loadDataFromJson();
            allFoods.setValue(foods);
            
            // Restore favorite status from SharedPreferences
            restoreFavorites(foods);
            
            Log.d(TAG, "Loaded " + foods.size() + " food items");
        } catch (Exception e) {
            Log.e(TAG, "Error loading foods", e);
            error.setValue(e.getMessage());
        } finally {
            isLoading.setValue(false);
        }
    }

    /**
     * Search foods by name using coroutines.
     */
    public List<FoodItem> searchFoods(String query) {
        List<FoodItem> foods = allFoods.getValue();
        if (foods == null || foods.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<FoodItem> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();
        
        for (FoodItem food : foods) {
            if (food.getName().toLowerCase().contains(lowerQuery)) {
                results.add(food);
            }
        }
        
        return results;
    }

    /**
     * Filter foods by safety status.
     */
    public List<FoodItem> filterBySafety(boolean safeOnly) {
        List<FoodItem> foods = allFoods.getValue();
        if (foods == null) {
            return new ArrayList<>();
        }
        
        List<FoodItem> filtered = new ArrayList<>();
        for (FoodItem food : foods) {
            if (!safeOnly || food.isSafe()) {
                filtered.add(food);
            }
        }
        
        return filtered;
    }

    /**
     * Toggle favorite status for a food item.
     */
    public void toggleFavorite(FoodItem food) {
        food.setFavorite(!food.isFavorite());
        
        // Save to SharedPreferences via DataManager
        dataManager.saveFoodItem(food);
        
        // Update the StateFlow to trigger UI update
        List<FoodItem> currentList = new ArrayList<>(allFoods.getValue());
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i).getName().equals(food.getName())) {
                currentList.set(i, food);
                break;
            }
        }
        allFoods.setValue(currentList);
    }

    /**
     * Get favorite foods only.
     */
    public List<FoodItem> getFavorites() {
        List<FoodItem> foods = allFoods.getValue();
        if (foods == null) {
            return new ArrayList<>();
        }
        
        List<FoodItem> favorites = new ArrayList<>();
        for (FoodItem food : foods) {
            if (food.isFavorite()) {
                favorites.add(food);
            }
        }
        
        return favorites;
    }

    /**
     * Load food data from JSON asset file.
     */
    private List<FoodItem> loadDataFromJson() throws Exception {
        List<FoodItem> foods = new ArrayList<>();
        
        // This will be loaded from assets/foods.json
        // For now, return empty list - DataManager handles actual loading
        return dataManager.getAllFoods();
    }

    /**
     * Restore favorite status from SharedPreferences.
     */
    private void restoreFavorites(List<FoodItem> foods) {
        for (FoodItem food : foods) {
            boolean isFavorite = dataManager.isFavorite(food.getName());
            food.setFavorite(isFavorite);
        }
    }

    /**
     * Clear all data and reload.
     */
    public void refresh() {
        executorService.execute(() -> {
            try {
                List<FoodItem> foods = loadDataFromJson();
                restoreFavorites(foods);
                allFoods.setValue(foods);
            } catch (Exception e) {
                Log.e(TAG, "Error refreshing data", e);
                error.setValue(e.getMessage());
            }
        });
    }
}
