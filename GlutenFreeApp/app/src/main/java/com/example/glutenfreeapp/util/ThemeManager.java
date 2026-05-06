package com.example.glutenfreeapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Utility class for theme management including dark mode support.
 */
public class ThemeManager {

    private static final String PREF_THEME = "pref_theme_mode";
    private static final int MODE_DEFAULT = 0;
    private static final int MODE_LIGHT = 1;
    private static final int MODE_DARK = 2;

    /**
     * Initialize the app theme based on user preference.
     * Call this in Application.onCreate() before any activity starts.
     */
    public static void initializeTheme(Context context) {
        int themeMode = getSavedThemeMode(context);
        applyTheme(themeMode);
    }

    /**
     * Apply the specified theme mode.
     * @param mode MODE_DEFAULT, MODE_LIGHT, or MODE_DARK
     */
    public static void applyTheme(int mode) {
        switch (mode) {
            case MODE_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case MODE_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case MODE_DEFAULT:
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    /**
     * Save user's theme preference.
     */
    public static void saveThemeMode(Context context, int mode) {
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .edit()
            .putInt(PREF_THEME, mode)
            .apply();
    }

    /**
     * Get saved theme mode from preferences.
     * @return MODE_DEFAULT, MODE_LIGHT, or MODE_DARK
     */
    public static int getSavedThemeMode(Context context) {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            .getInt(PREF_THEME, MODE_DEFAULT);
    }

    /**
     * Check if dark mode is currently active.
     */
    public static boolean isDarkMode(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode 
            & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }

    /**
     * Toggle between light and dark mode.
     * Recreates the activity to apply changes.
     */
    public static void toggleTheme(Activity activity) {
        boolean isDark = isDarkMode(activity);
        int newMode = isDark ? MODE_LIGHT : MODE_DARK;
        saveThemeMode(activity, newMode);
        applyTheme(newMode);
        activity.recreate();
    }
}
