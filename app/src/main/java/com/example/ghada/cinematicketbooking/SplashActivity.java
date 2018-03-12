package com.example.ghada.cinematicketbooking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private static  int WELCOME_TIMEOUT=4000;
    Animation al;
    ImageView img1;
    TextView txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        img1= (ImageView) findViewById(R.id.imageView1);


        al= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.together);
        img1.startAnimation(al);


        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent welcome = new Intent(SplashActivity.this,HomeActivity.class);
                startActivity(welcome);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        },WELCOME_TIMEOUT);
    }
}
