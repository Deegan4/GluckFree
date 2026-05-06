# Gluten Free Foods Android App

A simple Android application that helps users discover foods that are naturally gluten-free or certified gluten-free.

## Features

- **Browse Gluten-Free Foods**: View a comprehensive list of foods organized by category:
  - Fruits (Apple, Banana, Orange, Strawberries, etc.)
  - Vegetables (Carrots, Broccoli, Spinach, Tomatoes, etc.)
  - Proteins (Chicken, Salmon, Eggs, Beef, etc.)
  - Gluten-Free Grains (Rice, Quinoa, Corn, Certified GF Oats, etc.)
  - Dairy (Milk, Cheese, Yogurt, Butter)
  - Nuts & Seeds (Almonds, Walnuts, Cashews, etc.)
  - Legumes (Black Beans, Chickpeas, Lentils, etc.)
  - Snacks & Others (Popcorn, Dark Chocolate, Honey, etc.)

- **Search Functionality**: Real-time search as you type

- **Filter Options**: Filter by All, GF Safe, Contains Gluten, or Favorites

- **Persistent Favorites**: Your favorite foods are saved between app sessions using SharedPreferences

- **Clean UI**: Simple, intuitive interface with color-coded categories and empty state messages

- **Data from JSON**: Food data is loaded from a JSON asset file for easy updates

## Project Structure

```
GlutenFreeApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/glutenfreeapp/
│   │   │   ├── MainActivity.java       # Main activity with food list
│   │   │   ├── FoodItem.java           # Data model (Parcelable) with enums
│   │   │   ├── FoodAdapter.java        # RecyclerView adapter
│   │   │   ├── FoodDetailActivity.java # Detail view for food items
│   │   │   └── DataManager.java        # Handles JSON loading & SharedPreferences
│   │   ├── assets/
│   │   │   └── foods.json              # Food data in JSON format
│   │   └── res/
│   │       ├── layout/                 # XML layouts
│   │       └── drawable/               # Icons and drawables
│   ├── src/test/                       # Unit tests
│   └── src/androidTest/                # Instrumented tests
├── build.gradle                        # Project build config
└── app/build.gradle                    # App module build config
```

## Improvements Implemented

### Critical Fixes
1. ✅ **Data Persistence** - Favorites now persist using SharedPreferences via DataManager
2. ✅ **JSON Data Source** - Food data moved to `assets/foods.json` for easy maintenance
3. ✅ **Parcelable Implementation** - FoodItem implements Parcelable for safe Intent passing
4. ✅ **Real-time Search** - Search filters as you type using TextWatcher

### Code Quality
5. ✅ **Null Safety** - Added null checks on Intent extras and food item properties
6. ✅ **Efficient Updates** - Using notifyItemChanged() in adapter
7. ✅ **Enum Types** - FilterType and Category enums replace magic strings
8. ✅ **Error Handling** - Empty state view with contextual messages
9. ✅ **Single Responsibility** - DataManager handles data operations separately

### User Experience
10. ✅ **Accessibility** - Added contentDescription to interactive elements
11. ✅ **Empty State** - Shows helpful messages when no results found
12. ✅ **Category Filtering** - Infrastructure ready for category filter buttons

### Production Readiness
13. ✅ **ProGuard Enabled** - Release builds now use minification with proper rules
14. ✅ **Unit Tests** - FoodItemTest covers model classes
15. ✅ **UI Tests** - DataManagerTest covers data persistence

## Build & Run

1. Open the project in Android Studio
2. Sync Gradle files
3. Run on an emulator or device (API 24+)

## Testing

- **Unit Tests**: `./gradlew test`
- **Instrumented Tests**: `./gradlew connectedAndroidTest`

## Dependencies

- AndroidX AppCompat 1.6.1
- Material Design 1.9.0
- RecyclerView 1.3.1
- CardView 1.0.0
- JUnit 4.13.2
- AndroidX Test Extensions 1.1.5
- Espresso 3.5.1

## License

This project is for educational purposes.
