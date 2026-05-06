package com.example.glutenfreeapp;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {
    private static final String TAG = "DataManager";
    private static final String FAVORITES_KEY = "favorites";
    private static final String ONBOARDING_COMPLETE_KEY = "onboarding_complete";
    
    private final SharedPreferences sharedPreferences;
    private Set<String> favoriteIds;
    
    @Inject
    public DataManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.favoriteIds = new HashSet<>(sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>()));
    }
    
    /**
     * Check if onboarding has been completed.
     */
    public boolean isOnboardingComplete() {
        return sharedPreferences.getBoolean(ONBOARDING_COMPLETE_KEY, false);
    }
    
    /**
     * Mark onboarding as completed.
     */
    public void setOnboardingComplete() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ONBOARDING_COMPLETE_KEY, true);
        editor.apply();
    }
    
    public List<FoodItem> getAllFoods() {
        return loadFoodsFromJson();
    }
    
    public List<FoodItem> loadFoodsFromJson() {
        List<FoodItem> foods = new ArrayList<>();
        try {
            // Try to load from assets first
            InputStream is = getClass().getClassLoader().getResourceAsStream("assets/foods.json");
            if (is == null) {
                // Fallback: create sample data
                return createSampleFoods();
            }
            
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray foodsArray = jsonObject.getJSONArray("foods");
            
            for (int i = 0; i < foodsArray.length(); i++) {
                JSONObject foodObj = foodsArray.getJSONObject(i);
                String name = foodObj.getString("name");
                String category = foodObj.getString("category");
                String description = foodObj.getString("description");
                boolean isSafe = foodObj.getBoolean("isSafe");
                
                FoodItem food = new FoodItem(name, category, description, isSafe, 0);
                
                // Restore favorite status
                if (favoriteIds.contains(food.getId())) {
                    food.setFavorite(true);
                }
                
                foods.add(food);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading foods.json", e);
            return createSampleFoods();
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON", e);
            return createSampleFoods();
        }
        
        return foods;
    }
    
    /**
     * Create sample food data as fallback.
     */
    private List<FoodItem> createSampleFoods() {
        List<FoodItem> foods = new ArrayList<>();
        foods.add(new FoodItem("Rice", "Grains", "Plain white or brown rice", true, 0));
        foods.add(new FoodItem("Quinoa", "Grains", "Naturally gluten-free grain", true, 0));
        foods.add(new FoodItem("Wheat Bread", "Bakery", "Contains gluten", false, 0));
        foods.add(new FoodItem("Corn Tortilla", "Bakery", "Made from corn, gluten-free", true, 0));
        foods.add(new FoodItem("Pasta", "Grains", "Regular wheat pasta contains gluten", false, 0));
        foods.add(new FoodItem("Oats", "Grains", "Check for certified gluten-free", true, 0));
        return foods;
    }
    
    public void toggleFavorite(FoodItem food) {
        food.setFavorite(!food.isFavorite());
        if (food.isFavorite()) {
            favoriteIds.add(food.getId());
        } else {
            favoriteIds.remove(food.getId());
        }
        saveFavorites();
    }
    
    public void setFavorite(FoodItem food, boolean isFavorite) {
        food.setFavorite(isFavorite);
        if (isFavorite) {
            favoriteIds.add(food.getId());
        } else {
            favoriteIds.remove(food.getId());
        }
        saveFavorites();
    }
    
    private void saveFavorites() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(FAVORITES_KEY, favoriteIds);
        editor.apply();
    }
    
    public Set<String> getFavoriteIds() {
        return new HashSet<>(favoriteIds);
    }
    
    public boolean isFavorite(String foodId) {
        return favoriteIds.contains(foodId);
    }
}
