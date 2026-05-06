package com.example.glutenfreeapp.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.glutenfreeapp.DataManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return context.getSharedPreferences("gluten_free_prefs", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public DataManager provideDataManager(SharedPreferences sharedPreferences) {
        return new DataManager(sharedPreferences);
    }
}
