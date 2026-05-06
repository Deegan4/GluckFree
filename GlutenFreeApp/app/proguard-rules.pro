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

# Hilt/Dagger
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ComponentSupplier { *; }
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel { *; }
-keepclassmembers,allowshrinking,allowobfuscation class * extends dagger.hilt.android.internal.managers.ViewComponentManager {
    *;
}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# Keep repository and viewmodel classes
-keep class com.example.glutenfreeapp.repository.** { *; }
-keep class com.example.glutenfreeapp.viewmodel.** { *; }
-keep class com.example.glutenfreeapp.di.** { *; }
-keep class com.example.glutenfreeapp.util.** { *; }
