package com.example.glutenfreeapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment for onboarding screens.
 */
public class OnboardingFragment extends Fragment {
    
    private static final String ARG_TITLE = "title";
    private static final String ARG_DESCRIPTION = "description";
    private static final String ARG_IMAGE_RES = "image_res";
    private static final String ARG_IS_LAST = "is_last";
    
    private String title;
    private String description;
    private int imageRes;
    private boolean isLast;
    private OnboardingListener listener;
    
    public interface OnboardingListener {
        void onComplete();
        void onNext();
    }
    
    public static OnboardingFragment newInstance(String title, String description, int imageRes, boolean isLast) {
        OnboardingFragment fragment = new OnboardingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESCRIPTION, description);
        args.putInt(ARG_IMAGE_RES, imageRes);
        args.putBoolean(ARG_IS_LAST, isLast);
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            description = getArguments().getString(ARG_DESCRIPTION);
            imageRes = getArguments().getInt(ARG_IMAGE_RES);
            isLast = getArguments().getBoolean(ARG_IS_LAST);
        }
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        
        ImageView imageView = view.findViewById(R.id.onboardingImage);
        TextView titleText = view.findViewById(R.id.onboardingTitle);
        TextView descText = view.findViewById(R.id.onboardingDescription);
        Button actionButton = view.findViewById(R.id.onboardingActionButton);
        
        imageView.setImageResource(imageRes);
        titleText.setText(title);
        descText.setText(description);
        
        if (isLast) {
            actionButton.setText("Get Started");
        } else {
            actionButton.setText("Next");
        }
        
        actionButton.setOnClickListener(v -> {
            if (isLast && listener != null) {
                listener.onComplete();
            } else if (listener != null) {
                listener.onNext();
            }
        });
        
        return view;
    }
    
    public void setOnboardingListener(OnboardingListener listener) {
        this.listener = listener;
    }
}
