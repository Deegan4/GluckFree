package com.example.glutenfreeapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.viewModelScope;

import com.example.glutenfreeapp.FoodItem;
import com.example.glutenfreeapp.repository.FoodRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.launch;

/**
 * ViewModel for MainActivity.
 * Provides food data and operations using coroutines and StateFlow.
 */
@HiltViewModel
public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";
    
    private final FoodRepository foodRepository;
    
    // UI state
    private final MutableLiveData<List<FoodItem>> displayedFoods = new MutableLiveData<>();
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> showFavoritesOnly = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> safeFilter = new MutableLiveData<>(false);

    @Inject
    public MainViewModel(@NonNull Application application, FoodRepository foodRepository) {
        super(application);
        this.foodRepository = foodRepository;
        
        // Load foods on initialization
        loadFoods();
    }

    /**
     * Get observed list of foods to display.
     */
    public LiveData<List<FoodItem>> getDisplayedFoods() {
        return displayedFoods;
    }

    /**
     * Get current search query.
     */
    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    /**
     * Get favorites filter state.
     */
    public LiveData<Boolean> getShowFavoritesOnly() {
        return showFavoritesOnly;
    }

    /**
     * Get safe filter state.
     */
    public LiveData<Boolean> getSafeFilter() {
        return safeFilter;
    }

    /**
     * Get loading state from repository.
     */
    public StateFlow<Boolean> getIsLoading() {
        return foodRepository.getIsLoading();
    }

    /**
     * Load foods from repository.
     */
    private void loadFoods() {
        viewModelScope.launch {
            foodRepository.loadFoods(viewModelScope);
            
            // Observe the StateFlow and update LiveData
            launch {
                foodRepository.getAllFoods().collect { foods ->
                    applyFilters();
                };
            }
        };
    }

    /**
     * Update search query and filter results.
     */
    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
        applyFilters();
    }

    /**
     * Toggle favorites filter.
     */
    public void toggleFavoritesFilter() {
        Boolean current = showFavoritesOnly.getValue();
        showFavoritesOnly.setValue(current != null && !current);
        applyFilters();
    }

    /**
     * Toggle safe foods filter.
     */
    public void toggleSafeFilter() {
        Boolean current = safeFilter.getValue();
        safeFilter.setValue(current != null && !current);
        applyFilters();
    }

    /**
     * Toggle favorite status for a food item.
     */
    public void toggleFavorite(FoodItem food) {
        foodRepository.toggleFavorite(food);
        applyFilters();
    }

    /**
     * Apply all active filters to the food list.
     */
    private void applyFilters() {
        List<FoodItem> allFoods = foodRepository.getAllFoods().getValue();
        if (allFoods == null) {
            allFoods = new ArrayList<>();
        }
        
        List<FoodItem> filtered = new ArrayList<>(allFoods);
        
        // Apply search filter
        String query = searchQuery.getValue();
        if (query != null && !query.trim().isEmpty()) {
            String lowerQuery = query.toLowerCase().trim();
            List<FoodItem> searched = new ArrayList<>();
            for (FoodItem food : filtered) {
                if (food.getName().toLowerCase().contains(lowerQuery)) {
                    searched.add(food);
                }
            }
            filtered = searched;
        }
        
        // Apply favorites filter
        if (Boolean.TRUE.equals(showFavoritesOnly.getValue())) {
            List<FoodItem> favorites = new ArrayList<>();
            for (FoodItem food : filtered) {
                if (food.isFavorite()) {
                    favorites.add(food);
                }
            }
            filtered = favorites;
        }
        
        // Apply safety filter
        if (Boolean.TRUE.equals(safeFilter.getValue())) {
            List<FoodItem> safe = new ArrayList<>();
            for (FoodItem food : filtered) {
                if (food.isSafe()) {
                    safe.add(food);
                }
            }
            filtered = safe;
        }
        
        displayedFoods.setValue(filtered);
    }

    /**
     * Refresh data from source.
     */
    public void refresh() {
        foodRepository.refresh();
    }
}
