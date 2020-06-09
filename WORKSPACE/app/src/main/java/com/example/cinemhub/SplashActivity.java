package com.example.cinemhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler;

    private Runnable myRunnable;

    private final int SPLASH_DISPLAY_LENGTH = 3000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
