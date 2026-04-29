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

- **Search Functionality**: Quickly find specific foods using the search bar

- **Clean UI**: Simple, intuitive interface with color-coded categories

## Project Structure

```
GlutenFreeApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/glutenfreeapp/
│   │   │   ├── MainActivity.java       # Main activity with food list
│   │   │   ├── FoodItem.java           # Data model for food items
│   │   │   └── FoodAdapter.java        # RecyclerView adapter
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml   # Main layout
│   │   │   │   └── food_item_layout.xml # Individual food item layout
│   │   │   ├── drawable/               # Icons and backgrounds
│   │   │   ├── values/
│   │   │   │   ├── strings.xml         # String resources
│   │   │   │   └── colors.xml          # Color/theme definitions
│   │   │   └── mipmap-*/               # App launcher icons
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
└── settings.gradle
```

## How to Build

### Prerequisites
- Android Studio (Arctic Fox or newer recommended)
- JDK 8 or higher
- Android SDK with API level 34

### Building with Android Studio
1. Open Android Studio
2. Select "Open an Existing Project"
3. Navigate to the `GlutenFreeApp` folder
4. Wait for Gradle sync to complete
5. Click "Run" to install on an emulator or connected device

### Building with Command Line
```bash
cd GlutenFreeApp
./gradlew assembleDebug
```

The APK will be generated at `app/build/outputs/apk/debug/app-debug.apk`

## Usage

1. Launch the app
2. Browse through the list of gluten-free foods
3. Use the search bar to find specific foods
4. Each food item shows:
   - Name
   - Description with gluten-free status
   - Category icon
   - "GF" badge indicating gluten-free status

## Notes

- This app provides general information about naturally gluten-free foods
- Always check product labels for potential cross-contamination
- Some processed foods may contain hidden gluten
- Consult with healthcare providers for medical dietary advice

## License

This project is open source and available for educational purposes.
