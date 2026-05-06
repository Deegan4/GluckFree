package com.example.glutenfreeapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

/**
 * Unit tests for FoodItem class
 */
public class FoodItemTest {

    private FoodItem safeFood;
    private FoodItem unsafeFood;

    @Before
    public void setUp() {
        safeFood = new FoodItem("Apple", "Fruits", "Fresh apple - naturally gluten free", true, 1);
        unsafeFood = new FoodItem("Wheat Bread", "Grains", "Contains wheat gluten", false, 2);
    }

    @Test
    public void testFoodItemCreation() {
        assertEquals("Apple", safeFood.getName());
        assertEquals("Fruits", safeFood.getCategory());
        assertTrue(safeFood.isSafe());
        assertFalse(safeFood.isFavorite());
    }

    @Test
    public void testToggleFavorite() {
        assertFalse(safeFood.isFavorite());
        safeFood.setFavorite(true);
        assertTrue(safeFood.isFavorite());
        safeFood.setFavorite(false);
        assertFalse(safeFood.isFavorite());
    }

    @Test
    public void testFoodItemIdGeneration() {
        assertNotNull(safeFood.getId());
        assertEquals("apple", safeFood.getId());
        assertEquals("wheat_bread", unsafeFood.getId());
    }

    @Test
    public void testFilterTypeEnum() {
        assertEquals(FoodItem.FilterType.ALL, FoodItem.FilterType.ALL);
        assertEquals(FoodItem.FilterType.SAFE, FoodItem.FilterType.SAFE);
        assertEquals(FoodItem.FilterType.UNSAFE, FoodItem.FilterType.UNSAFE);
        assertEquals(FoodItem.FilterType.FAVORITES, FoodItem.FilterType.FAVORITES);
    }

    @Test
    public void testCategoryEnum() {
        assertEquals("Fruits", FoodItem.Category.FRUITS.getDisplayName());
        assertEquals("Vegetables", FoodItem.Category.VEGETABLES.getDisplayName());
        assertEquals("All", FoodItem.Category.ALL.getDisplayName());
    }

    @Test
    public void testCategoryFromString() {
        assertEquals(FoodItem.Category.FRUITS, FoodItem.Category.fromString("Fruits"));
        assertEquals(FoodItem.Category.VEGETABLES, FoodItem.Category.fromString("Vegetables"));
        assertEquals(FoodItem.Category.ALL, FoodItem.Category.fromString("Unknown"));
    }
}
