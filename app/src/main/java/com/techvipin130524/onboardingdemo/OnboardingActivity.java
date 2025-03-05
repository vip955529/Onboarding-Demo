package com.techvipin130524.onboardingdemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.techvipin130524.onboardingdemo.adapter.OnboardingAdapter;
import com.techvipin130524.onboardingdemo.model.OnboardingItem;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    //private TabLayout tabIndicator;
    private TextView heading;

    private LinearLayout indicatorLayout;
    private ImageView[] dots;

    private final String[] headings = {
            "Create gamified quizzes becomes simple",
            "Find quizzes to test out your knowledge",
            "Take part in challenges with friends"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_onboarding);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //ID's
        heading = findViewById(R.id.heading);
        viewPager = findViewById(R.id.viewPager);
        indicatorLayout = findViewById(R.id.indicatorLayout);
        findViewById(R.id.buttonSignup).setOnClickListener(v -> navigateToSignup());
        findViewById(R.id.textLogin).setOnClickListener(v -> navigateToLogin());

        List<OnboardingItem> items = new ArrayList<>();
        items.add(new OnboardingItem(R.drawable.image1));
        items.add(new OnboardingItem(R.drawable.image2));
        items.add(new OnboardingItem(R.drawable.image3));

        viewPager.setAdapter(new OnboardingAdapter(items));

        // Attach TabLayout to ViewPager2
//        new TabLayoutMediator(tabIndicator, viewPager, (tab, position) -> {
//            tab.setIcon(R.drawable.tab_indicator_dot);
//        }).attach();

        // Create small dots dynamically
        createIndicatorDots(items.size());

        // Set initial heading
        heading.setText(headings[0]);

        // Listen for page changes and update heading
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateDots(position);
                heading.setText(headings[position]); // Update heading text
            }
        });
    }

//    private void createIndicatorDots(int count) {
//        dots = new ImageView[count];
//        for (int i = 0; i < count; i++) {
//            dots[i] = new ImageView(this);
//            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_unselected));
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
//            params.setMargins(8, 0, 8, 0);
//            indicatorLayout.addView(dots[i], params);
//        }
//        dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_selected));
//    }

    private void createIndicatorDots(int count) {
        dots = new ImageView[count];
        for (int i = 0; i < count; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_unselected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);  // Proper spacing
            params.gravity = android.view.Gravity.CENTER;  // Ensure proper centering

            indicatorLayout.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_selected));
    }

    private void updateDots(int position) {
        Log.d("DOT_DEBUG", "Active dot position: " + position);  // Debugging log

        for (int i = 0; i < dots.length; i++) {
            if (i == position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_selected));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.dot_unselected));
            }
        }
    }


    private void navigateToSignup() {
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

    private void navigateToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
