package com.example.glutenfreeapp.repository;

import com.example.glutenfreeapp.DataManager;
import com.example.glutenfreeapp.FoodItem;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.content.SharedPreferences;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for FoodRepository.
 */
public class FoodRepositoryTest {

    @Mock
    private DataManager mockDataManager;

    @Mock
    private SharedPreferences mockSharedPreferences;

    private FoodRepository foodRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        foodRepository = new FoodRepository(mockDataManager, mockSharedPreferences);
    }

    @Test
    public void repository_initializesCorrectly() {
        assertNotNull(foodRepository);
        assertNotNull(foodRepository.getAllFoods());
        assertNotNull(foodRepository.getIsLoading());
        assertNotNull(foodRepository.getError());
    }

    @Test
    public void getAllFoods_returnsStateFlow() {
        // Verify that getAllFoods returns a non-null StateFlow
        assertNotNull(foodRepository.getAllFoods());
    }

    @Test
    public void getIsLoading_returnsStateFlow() {
        // Verify that getIsLoading returns a non-null StateFlow
        assertNotNull(foodRepository.getIsLoading());
    }

    @Test
    public void getError_returnsStateFlow() {
        // Verify that getError returns a non-null StateFlow
        assertNotNull(foodRepository.getError());
    }
}
