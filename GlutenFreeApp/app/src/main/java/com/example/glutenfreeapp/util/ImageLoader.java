package com.example.glutenfreeapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Utility class for image loading with Glide.
 * Provides centralized image loading with proper lifecycle handling.
 */
public class ImageLoader {

    /**
     * Load an image from URL or resource ID into an ImageView.
     * @param imageView The target ImageView
     * @param urlOrResId URL string or resource ID (Integer)
     * @param placeholder Placeholder drawable while loading
     */
    public static void load(@NonNull ImageView imageView, @Nullable Object urlOrResId, 
                           @Nullable Drawable placeholder) {
        Context context = imageView.getContext();
        
        Glide.with(context)
            .load(urlOrResId)
            .placeholder(placeholder)
            .error(placeholder)
            .centerCrop()
            .into(imageView);
    }

    /**
     * Load an image and return as Bitmap for processing.
     * Use with coroutines for async operations.
     */
    public static void loadAsBitmap(@NonNull ImageView imageView, @Nullable String url,
                                   @NonNull ImageCallback callback) {
        Context context = imageView.getContext();
        
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, 
                                          @Nullable Transition<? super Bitmap> transition) {
                    callback.onImageLoaded(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                    callback.onLoadFailed(null);
                }

                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
                    callback.onLoadFailed(errorDrawable);
                }
            });
    }

    /**
     * Callback interface for bitmap loading results.
     */
    public interface ImageCallback {
        void onImageLoaded(Bitmap bitmap);
        void onLoadFailed(Drawable errorDrawable);
    }

    /**
     * Clear image from ImageView to prevent memory leaks.
     */
    public static void clear(@NonNull ImageView imageView) {
        Glide.with(imageView.getContext()).clear(imageView);
    }
}
