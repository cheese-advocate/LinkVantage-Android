package com.example.compulinkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    /**
     * The time the splash screen will display before navigating to the login screen
     * Shown in milliseconds - currently set to 1500 - 1.5s
     */
    private static int SPLASH_TIME_OUT = 1500;

    /**
     * Creates the main activity and runs it for the specified time
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * The method below ensures the splash screen runs only the specified
         * time before navigating to the login activity
         */
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}