package com.example.glutenfreeapp;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented tests for DataManager
 */
@RunWith(AndroidJUnit4.class)
public class DataManagerTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = 
        new ActivityTestRule<>(MainActivity.class);

    private Context context;
    private DataManager dataManager;

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        dataManager = DataManager.getInstance(context);
    }

    @Test
    public void testDataManagerSingleton() {
        DataManager instance1 = DataManager.getInstance(context);
        DataManager instance2 = DataManager.getInstance(context);
        assertSame(instance1, instance2);
    }

    @Test
    public void testLoadFoodsFromJson() {
        List<FoodItem> foods = dataManager.loadFoods();
        assertNotNull(foods);
        assertTrue("Should load foods from JSON", foods.size() > 0);
    }

    @Test
    public void testLoadFoodsContainsSafeAndUnsafe() {
        List<FoodItem> foods = dataManager.loadFoods();
        boolean hasSafe = false;
        boolean hasUnsafe = false;
        
        for (FoodItem food : foods) {
            if (food.isSafe()) hasSafe = true;
            else hasUnsafe = true;
        }
        
        assertTrue("Should have safe foods", hasSafe);
        assertTrue("Should have unsafe foods", hasUnsafe);
    }

    @Test
    public void testToggleFavoritePersistence() {
        List<FoodItem> foods = dataManager.loadFoods();
        if (foods.isEmpty()) return;
        
        FoodItem testFood = foods.get(0);
        String foodId = testFood.getId();
        
        // Toggle to favorite
        dataManager.toggleFavorite(testFood);
        assertTrue(dataManager.isFavorite(foodId));
        
        // Toggle back
        dataManager.toggleFavorite(testFood);
        assertFalse(dataManager.isFavorite(foodId));
    }
}
