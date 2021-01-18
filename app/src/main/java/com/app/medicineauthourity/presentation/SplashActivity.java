package com.app.medicineauthourity.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.app.medicineauthourity.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, StartActivity.class));
        }, SPLASH_TIME_OUT);
    }
}