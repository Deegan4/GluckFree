# Add project specific ProGuard rules here.
-keepattributes *Annotation*
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Keep FoodItem Parcelable
-keep class com.example.glutenfreeapp.FoodItem { *; }
-keepclassmembers class com.example.glutenfreeapp.FoodItem {
    public static final android.os.Parcelable$Creator CREATOR;
}

# Keep enums
-keepclassmembers enum com.example.glutenfreeapp.FoodItem$* {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep DataManager
-keep class com.example.glutenfreeapp.DataManager { *; }
