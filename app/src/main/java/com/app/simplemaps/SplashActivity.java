package com.app.simplemaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

        private static final long SPLASH_DELAY = 2000; // Delay in milliseconds

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Intent untuk memulai MainActivity setelah Splash Screen
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);

                    // Menutup SplashActivity agar tidak dapat diakses kembali dengan tombol back
                    finish();
                }
            }, SPLASH_DELAY);
        }
    }

//Signature :
//10120146 - Irshal Mulky H - IF4