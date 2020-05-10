package com.example.cinemhub;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;





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

                break;
            case R.id.navigation_shake:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_finish);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer);
                break;
            case R.id.navigation_favorite:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_finish);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer_full);
                break;
            default:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_finish);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer);
                break;
        }
    }
    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    public void shakingSetting()
    {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }
}
