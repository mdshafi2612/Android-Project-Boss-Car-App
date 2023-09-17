package com.example.testcarappwithfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView img1,img2;
    Animation top,bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        img1=(ImageView)findViewById(R.id.logo);
        img2=(ImageView)findViewById(R.id.sublogo);

        top= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mainlogoanimation);
        bottom= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.sublogoanimation);

        img1.setAnimation(top);
        img2.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        }, 5000);
    }

}