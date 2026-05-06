package com.example.glutenfreeapp;

import android.content.Context;
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

public class DataManager {
    private static final String TAG = "DataManager";
    private static final String PREFS_NAME = "gluten_free_prefs";
    private static final String FAVORITES_KEY = "favorites";
    
    private static DataManager instance;
    private Context context;
    private SharedPreferences sharedPreferences;
    private Set<String> favoriteIds;
    
    private DataManager(Context context) {
        this.context = context.getApplicationContext();
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        this.favoriteIds = new HashSet<>(sharedPreferences.getStringSet(FAVORITES_KEY, new HashSet<>()));
    }
    
    public static synchronized DataManager getInstance(Context context) {
        if (instance == null) {
            instance = new DataManager(context);
        }
        return instance;
    }
    
    public List<FoodItem> loadFoods() {
        List<FoodItem> foods = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("foods.json");
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
                String iconName = foodObj.getString("icon");
                
                int iconResId = getIconResourceId(iconName);
                FoodItem food = new FoodItem(name, category, description, isSafe, iconResId);
                
                // Restore favorite status
                if (favoriteIds.contains(food.getId())) {
                    food.setFavorite(true);
                }
                
                foods.add(food);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading foods.json", e);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON", e);
        }
        
        return foods;
    }
    
    private int getIconResourceId(String iconName) {
        return context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
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
