package com.example.glutenfreeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    private String currentCategory = "All";
    private String currentFilter = "all"; // all, safe, unsafe, favorites

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        filterAllBtn = findViewById(R.id.filterAllBtn);
        filterSafeBtn = findViewById(R.id.filterSafeBtn);
        filterUnsafeBtn = findViewById(R.id.filterUnsafeBtn);
        filterFavoritesBtn = findViewById(R.id.filterFavoritesBtn);
        categoryFilterLayout = findViewById(R.id.categoryFilterLayout);

        foodList = new ArrayList<>();
        filteredList = new ArrayList<>();
        
        // Initialize with foods (both safe and unsafe)
        initializeFoods();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        foodAdapter = new FoodAdapter(filteredList, this, this);
        recyclerView.setAdapter(foodAdapter);

        searchButton.setOnClickListener(v -> filterFoods());
        
        // Filter buttons
        filterAllBtn.setOnClickListener(v -> {
            currentFilter = "all";
            updateFilterButtons();
            filterFoods();
        });
        
        filterSafeBtn.setOnClickListener(v -> {
            currentFilter = "safe";
            updateFilterButtons();
            filterFoods();
        });
        
        filterUnsafeBtn.setOnClickListener(v -> {
            currentFilter = "unsafe";
            updateFilterButtons();
            filterFoods();
        });
        
        filterFavoritesBtn.setOnClickListener(v -> {
            currentFilter = "favorites";
            updateFilterButtons();
            filterFoods();
        });

        // Show all foods initially
        filteredList.addAll(foodList);
        foodAdapter.notifyDataSetChanged();
    }

    private void updateFilterButtons() {
        filterAllBtn.setBackgroundResource(currentFilter.equals("all") ? R.drawable.filter_button_active : R.drawable.filter_button);
        filterSafeBtn.setBackgroundResource(currentFilter.equals("safe") ? R.drawable.filter_button_active : R.drawable.filter_button);
        filterUnsafeBtn.setBackgroundResource(currentFilter.equals("unsafe") ? R.drawable.filter_button_active : R.drawable.filter_button);
        filterFavoritesBtn.setBackgroundResource(currentFilter.equals("favorites") ? R.drawable.filter_button_active : R.drawable.filter_button);
    }

    private void initializeFoods() {
        // GLUTEN-FREE SAFE FOODS
        // Fruits
        foodList.add(new FoodItem("Apple", "Fruits", "Fresh apple - naturally gluten free. Great source of fiber and vitamin C.", true, R.drawable.fruit_icon));
        foodList.add(new FoodItem("Banana", "Fruits", "Fresh banana - naturally gluten free. Rich in potassium and energy.", true, R.drawable.fruit_icon));
        foodList.add(new FoodItem("Orange", "Fruits", "Fresh orange - naturally gluten free. Excellent source of vitamin C.", true, R.drawable.fruit_icon));
        foodList.add(new FoodItem("Strawberries", "Fruits", "Fresh strawberries - naturally gluten free. Antioxidant-rich berries.", true, R.drawable.fruit_icon));
        foodList.add(new FoodItem("Grapes", "Fruits", "Fresh grapes - naturally gluten free. Contains resveratrol antioxidants.", true, R.drawable.fruit_icon));
        foodList.add(new FoodItem("Blueberries", "Fruits", "Fresh blueberries - naturally gluten free. Superfood with high antioxidants.", true, R.drawable.fruit_icon));
        foodList.add(new FoodItem("Mango", "Fruits", "Fresh mango - naturally gluten free. Tropical fruit rich in vitamins A and C.", true, R.drawable.fruit_icon));
        foodList.add(new FoodItem("Pineapple", "Fruits", "Fresh pineapple - naturally gluten free. Contains bromelain enzyme.", true, R.drawable.fruit_icon));
        
        // Vegetables
        foodList.add(new FoodItem("Carrots", "Vegetables", "Fresh carrots - naturally gluten free. High in beta-carotene and fiber.", true, R.drawable.vegetable_icon));
        foodList.add(new FoodItem("Broccoli", "Vegetables", "Fresh broccoli - naturally gluten free. Cruciferous vegetable rich in nutrients.", true, R.drawable.vegetable_icon));
        foodList.add(new FoodItem("Spinach", "Vegetables", "Fresh spinach - naturally gluten free. Iron and vitamin K rich leafy green.", true, R.drawable.vegetable_icon));
        foodList.add(new FoodItem("Tomatoes", "Vegetables", "Fresh tomatoes - naturally gluten free. Rich in lycopene antioxidant.", true, R.drawable.vegetable_icon));
        foodList.add(new FoodItem("Cucumber", "Vegetables", "Fresh cucumber - naturally gluten free. Hydrating and low calorie.", true, R.drawable.vegetable_icon));
        foodList.add(new FoodItem("Bell Peppers", "Vegetables", "Fresh bell peppers - naturally gluten free. High in vitamin C.", true, R.drawable.vegetable_icon));
        foodList.add(new FoodItem("Lettuce", "Vegetables", "Fresh lettuce - naturally gluten free. Low calorie salad base.", true, R.drawable.vegetable_icon));
        foodList.add(new FoodItem("Potatoes", "Vegetables", "Fresh potatoes - naturally gluten free. Versatile starchy vegetable.", true, R.drawable.vegetable_icon));
        foodList.add(new FoodItem("Sweet Potatoes", "Vegetables", "Fresh sweet potatoes - naturally gluten free. Rich in vitamin A.", true, R.drawable.vegetable_icon));
        foodList.add(new FoodItem("Zucchini", "Vegetables", "Fresh zucchini - naturally gluten free. Low carb summer squash.", true, R.drawable.vegetable_icon));
        
        // Proteins
        foodList.add(new FoodItem("Chicken Breast", "Proteins", "Plain cooked chicken - gluten free. Lean protein source.", true, R.drawable.meat_icon));
        foodList.add(new FoodItem("Salmon", "Proteins", "Fresh salmon - naturally gluten free. Rich in omega-3 fatty acids.", true, R.drawable.fish_icon));
        foodList.add(new FoodItem("Eggs", "Proteins", "Fresh eggs - naturally gluten free. Complete protein source.", true, R.drawable.egg_icon));
        foodList.add(new FoodItem("Beef Steak", "Proteins", "Plain beef steak - gluten free. High in iron and protein.", true, R.drawable.meat_icon));
        foodList.add(new FoodItem("Pork Chop", "Proteins", "Plain pork chop - gluten free. Good source of B vitamins.", true, R.drawable.meat_icon));
        foodList.add(new FoodItem("Tuna", "Proteins", "Fresh tuna - naturally gluten free. Lean protein with omega-3s.", true, R.drawable.fish_icon));
        foodList.add(new FoodItem("Shrimp", "Proteins", "Fresh shrimp - naturally gluten free. Low calorie seafood.", true, R.drawable.fish_icon));
        foodList.add(new FoodItem("Turkey", "Proteins", "Plain turkey - gluten free. Lean poultry protein.", true, R.drawable.meat_icon));
        
        // Grains & Starches (Gluten-Free)
        foodList.add(new FoodItem("Rice", "Grains", "White or brown rice - naturally gluten free. Staple grain worldwide.", true, R.drawable.grain_icon));
        foodList.add(new FoodItem("Quinoa", "Grains", "Quinoa - naturally gluten free complete protein grain.", true, R.drawable.grain_icon));
        foodList.add(new FoodItem("Corn", "Grains", "Fresh corn - naturally gluten free. Versatile whole grain.", true, R.drawable.grain_icon));
        foodList.add(new FoodItem("Oats (GF Certified)", "Grains", "Certified gluten-free oats only. Regular oats may be cross-contaminated.", true, R.drawable.grain_icon));
        foodList.add(new FoodItem("Buckwheat", "Grains", "Buckwheat - naturally gluten free despite the name.", true, R.drawable.grain_icon));
        foodList.add(new FoodItem("Millet", "Grains", "Millet - naturally gluten free ancient grain.", true, R.drawable.grain_icon));
        
        // Dairy
        foodList.add(new FoodItem("Milk", "Dairy", "Plain milk - naturally gluten free. Check flavored varieties.", true, R.drawable.dairy_icon));
        foodList.add(new FoodItem("Cheese", "Dairy", "Most natural cheeses - gluten free. Avoid processed cheese spreads.", true, R.drawable.dairy_icon));
        foodList.add(new FoodItem("Yogurt", "Dairy", "Plain yogurt - check for additives. Greek yogurt is a good choice.", true, R.drawable.dairy_icon));
        foodList.add(new FoodItem("Butter", "Dairy", "Plain butter - naturally gluten free.", true, R.drawable.dairy_icon));
        
        // Nuts & Seeds
        foodList.add(new FoodItem("Almonds", "Nuts", "Raw almonds - naturally gluten free. High in vitamin E.", true, R.drawable.nut_icon));
        foodList.add(new FoodItem("Walnuts", "Nuts", "Raw walnuts - naturally gluten free. Rich in omega-3s.", true, R.drawable.nut_icon));
        foodList.add(new FoodItem("Cashews", "Nuts", "Raw cashews - naturally gluten free. Creamy texture.", true, R.drawable.nut_icon));
        foodList.add(new FoodItem("Sunflower Seeds", "Nuts", "Raw sunflower seeds - gluten free. Vitamin E rich.", true, R.drawable.nut_icon));
        foodList.add(new FoodItem("Chia Seeds", "Nuts", "Chia seeds - naturally gluten free. High in fiber and omega-3s.", true, R.drawable.nut_icon));
        
        // Legumes
        foodList.add(new FoodItem("Black Beans", "Legumes", "Dried or canned black beans - gluten free. High fiber protein.", true, R.drawable.bean_icon));
        foodList.add(new FoodItem("Chickpeas", "Legumes", "Dried or canned chickpeas - gluten free. Great for hummus.", true, R.drawable.bean_icon));
        foodList.add(new FoodItem("Lentils", "Legumes", "Dried lentils - naturally gluten free. Quick cooking legume.", true, R.drawable.bean_icon));
        foodList.add(new FoodItem("Kidney Beans", "Legumes", "Dried or canned kidney beans - gluten free.", true, R.drawable.bean_icon));
        foodList.add(new FoodItem("Pinto Beans", "Legumes", "Dried or canned pinto beans - gluten free.", true, R.drawable.bean_icon));
        
        // Snacks & Others
        foodList.add(new FoodItem("Popcorn", "Snacks", "Plain popcorn - naturally gluten free. Watch for flavored varieties.", true, R.drawable.snack_icon));
        foodList.add(new FoodItem("Dark Chocolate", "Snacks", "Check label for GF certification. Many are safe.", true, R.drawable.snack_icon));
        foodList.add(new FoodItem("Honey", "Snacks", "Pure honey - naturally gluten free. Natural sweetener.", true, R.drawable.snack_icon));
        foodList.add(new FoodItem("Olive Oil", "Oils", "Pure olive oil - naturally gluten free.", true, R.drawable.oil_icon));
        foodList.add(new FoodItem("Coconut Oil", "Oils", "Pure coconut oil - naturally gluten free.", true, R.drawable.oil_icon));

        // GLUTEN-CONTAINING FOODS (UNSAFE)
        foodList.add(new FoodItem("Wheat Bread", "Grains", "Contains wheat gluten. Avoid completely on GF diet.", false, R.drawable.grain_icon));
        foodList.add(new FoodItem("Barley", "Grains", "Contains gluten. Used in beer and soups.", false, R.drawable.grain_icon));
        foodList.add(new FoodItem("Rye", "Grains", "Contains gluten. Found in rye bread and crackers.", false, R.drawable.grain_icon));
        foodList.add(new FoodItem("Regular Pasta", "Grains", "Made from wheat. Choose GF pasta alternatives.", false, R.drawable.grain_icon));
        foodList.add(new FoodItem("Cookies", "Snacks", "Usually contain wheat flour. Look for GF versions.", false, R.drawable.snack_icon));
        foodList.add(new FoodItem("Cake", "Snacks", "Traditional cakes contain wheat flour. GF options available.", false, R.drawable.snack_icon));
        foodList.add(new FoodItem("Beer", "Beverages", "Made from barley. Choose GF beers or wine instead.", false, R.drawable.snack_icon));
        foodList.add(new FoodItem("Soy Sauce", "Condiments", "Traditional soy sauce contains wheat. Use tamari instead.", false, R.drawable.snack_icon));
        foodList.add(new FoodItem("Croutons", "Snacks", "Made from wheat bread. Avoid in salads.", false, R.drawable.snack_icon));
        foodList.add(new FoodItem("Seitan", "Proteins", "Made from wheat gluten. High protein but not GF.", false, R.drawable.meat_icon));
    }

    private void filterFoods() {
        String query = searchEditText.getText().toString().toLowerCase().trim();
        filteredList.clear();
        
        for (FoodItem food : foodList) {
            // Apply safety filter
            boolean matchesSafety = true;
            if (currentFilter.equals("safe")) {
                matchesSafety = food.isSafe();
            } else if (currentFilter.equals("unsafe")) {
                matchesSafety = !food.isSafe();
            } else if (currentFilter.equals("favorites")) {
                matchesSafety = food.isFavorite();
            }
            
            // Apply search query
            boolean matchesSearch = query.isEmpty() || 
                food.getName().toLowerCase().contains(query) || 
                food.getDescription().toLowerCase().contains(query) ||
                food.getCategory().toLowerCase().contains(query);
            
            if (matchesSafety && matchesSearch) {
                filteredList.add(food);
            }
        }
        
        foodAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(FoodItem foodItem) {
        // Open detail activity
        Intent intent = new Intent(this, FoodDetailActivity.class);
        intent.putExtra("name", foodItem.getName());
        intent.putExtra("category", foodItem.getCategory());
        intent.putExtra("description", foodItem.getDescription());
        intent.putExtra("isSafe", foodItem.isSafe());
        startActivity(intent);
    }

    @Override
    public void onFavoriteClick(FoodItem foodItem, int position) {
        // Could save to persistent storage here
        // For now, just update the UI
    }
}
