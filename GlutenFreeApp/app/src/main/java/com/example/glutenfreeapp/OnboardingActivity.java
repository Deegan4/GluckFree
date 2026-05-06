package com.example.glutenfreeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;

/**
 * Onboarding activity with ViewPager for first-time users.
 */
public class OnboardingActivity extends AppCompatActivity implements OnboardingFragment.OnboardingListener {
    
    private static final String PREF_ONBOARDING_COMPLETE = "onboarding_complete";
    
    private ViewPager viewPager;
    private OnboardingAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Check if onboarding was already completed
        if (isOnboardingComplete()) {
            navigateToMain();
            finish();
            return;
        }
        
        setContentView(R.layout.activity_onboarding);
        
        viewPager = findViewById(R.id.onboardingViewPager);
        setupViewPager();
    }
    
    private boolean isOnboardingComplete() {
        return DataManager.getInstance(this).isOnboardingComplete();
    }
    
    private void setupViewPager() {
        adapter = new OnboardingAdapter(getSupportFragmentManager());
        
        // Add onboarding screens
        adapter.addFragment(OnboardingFragment.newInstance(
            "Find Gluten-Free Foods",
            "Easily search and discover foods that are safe for your gluten-free diet.",
            android.R.drawable.ic_menu_search,
            false
        ));
        
        adapter.addFragment(OnboardingFragment.newInstance(
            "Track Your Favorites",
            "Save your favorite gluten-free products for quick access while shopping.",
            android.R.drawable.btn_star_big_on,
            false
        ));
        
        adapter.addFragment(OnboardingFragment.newInstance(
            "Stay Safe & Healthy",
            "Avoid gluten-containing ingredients and make informed food choices.",
            android.R.drawable.ic_dialog_info,
            true
        ));
        
        viewPager.setAdapter(adapter);
    }
    
    @Override
    public void onComplete() {
        DataManager.getInstance(this).setOnboardingComplete();
        navigateToMain();
        finish();
    }
    
    @Override
    public void onNext() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < adapter.getCount() - 1) {
            viewPager.setCurrentItem(currentItem + 1, true);
        }
    }
    
    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    
    static class OnboardingAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();
        
        public OnboardingAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }
        
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        
        @Override
        public int getCount() {
            return fragments.size();
        }
        
        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }
    }
}
