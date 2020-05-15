package com.example.cinemhub;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cinemhub.utils.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private SensorManager mSensorManager;
    public float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_shake, R.id.navigation_favorite)
                .build();


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG,"onNavigationItemSelected");

                return false;
            }
        });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.search_menu, menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    public void menuColorSettings(int FromWho)
    {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        switch (FromWho) {
            case R.id.navigation_home:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.home_full);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_finish);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer);

                menu.findItem(R.id.navigation_home).setEnabled(false);
                menu.findItem(R.id.navigation_shake).setEnabled(true);
                menu.findItem(R.id.navigation_favorite).setEnabled(true);

                break;
            case R.id.navigation_shake:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_finish);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer);

                menu.findItem(R.id.navigation_home).setEnabled(true);
                menu.findItem(R.id.navigation_shake).setEnabled(false);
                menu.findItem(R.id.navigation_favorite).setEnabled(true);
                break;
            case R.id.navigation_favorite:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_finish);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer_full);

                menu.findItem(R.id.navigation_home).setEnabled(true);
                menu.findItem(R.id.navigation_shake).setEnabled(true);
                menu.findItem(R.id.navigation_favorite).setEnabled(false);
                break;
            default:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_finish);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer);

                menu.findItem(R.id.navigation_home).setEnabled(true);
                menu.findItem(R.id.navigation_shake).setEnabled(true);
                menu.findItem(R.id.navigation_favorite).setEnabled(true);
                break;
        }
    }

}
