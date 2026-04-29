package com.example.glutenfreeapp;

public class FoodItem {
    private String name;
    private String category;
    private String description;
    private boolean isSafe;
    private int iconResId;
    private boolean isFavorite;

    public FoodItem(String name, String category, String description, boolean isSafe, int iconResId) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.isSafe = isSafe;
        this.iconResId = iconResId;
        this.isFavorite = false;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSafe() {
        return isSafe;
    }

    public int getIconResId() {
        return iconResId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
