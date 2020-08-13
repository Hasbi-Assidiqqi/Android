package com.android.screensplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Animation topAnim, bottomAnim, rightAnim, leftAnim, scaleAnim;
    ImageView starMoon, mosque, soil, soilRight, soilLeft;
    TextView logo, slogan;
    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Set FullScreen
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View. SYSTEM_UI_FLAG_IMMERSIVE
        );

        // Set Screen Splash
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent screenSplash = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(screenSplash);
                finish();
            }
        }, 6000);


        // setAnim
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        rightAnim = AnimationUtils.loadAnimation(this,R.anim.right_animation);
        leftAnim = AnimationUtils.loadAnimation(this,R.anim.left_animation);
        scaleAnim = AnimationUtils.loadAnimation(this,R.anim.rotate_animation);

        // Instance Anim
        logo = findViewById(R.id.tv_modern);
        slogan = findViewById(R.id.tv_screenSplash);
        starMoon = findViewById(R.id.img_starMoon);
        mosque = findViewById(R.id.img_mosque);
        soil = findViewById(R.id.img_soil);
        soilRight = findViewById(R.id.img_soilRight);
        soilLeft = findViewById(R.id.img_soilLeft);

        // set Anim
        logo.setAnimation(topAnim);
        slogan.setAnimation(topAnim);
        starMoon.setAnimation(scaleAnim);
        soil.setAnimation(bottomAnim);
        mosque.setAnimation(bottomAnim);
        soilRight.setAnimation(rightAnim);
        soilLeft.setAnimation(leftAnim);

    }


}