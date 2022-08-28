package com.civildeal.civildeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.civildeal.civildeal.Activity.Login;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIMER = 2000;

    ImageView backgroundImage;
    TextView textView;

    Animation sideAnim, bottomAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }
        backgroundImage = findViewById(R.id.splashScreen);

//        Animation
//        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
//        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
//
//        //set animations on elements
//        backgroundImage.setAnimation(sideAnim);
//        textView.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_TIMER);
    }

    
}