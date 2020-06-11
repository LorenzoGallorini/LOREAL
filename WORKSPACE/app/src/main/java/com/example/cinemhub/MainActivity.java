package com.example.cinemhub;

import android.content.Context;
import android.content.SharedPreferences;
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

import safety.com.br.android_shake_detector.core.ShakeDetector;

/**
 * Activity principale per gestire l'intera applicazione
 * All'interno sono contenuti la Bottom Navigation Menu e un contenitore dove gestiamo i vari Fragment
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    public ShakeDetector shakeDetector;/**< attributo che serve per rilevare la shekerata del telefono nello ShakeFragment*/
    public ShakeDetector shakeResultDetector;/**< attributo che serve per rilevare la shekerata del telefono nello ShakeResultFragment*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        String regionSelected=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);


        // in caso di primo utilizzo dell'app andiamo a settare il valore ragion nelle SharedPreferences con un valore di default
        if(regionSelected==null || regionSelected.length()==0){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.REGION_SHARED_PREF_NAME, Constants.REGION_ITALY);
            editor.apply();
        }

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
        // andiamo a creare la bottom navigation menu
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

    /**
     * impostiamo il titolo della schermata in base al Fragment in cui ci troviamo
     * @param title titolo del Fragment
     */
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    /**
     * metodo per gestire le icone presenti nel bottom navigation menu tenendo conto della schermata
     * in cui ci troviamo
     * @param fromWho ID della schermata in cui ci troviamo
     */
    public void menuColorSettings(int fromWho)
    {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        /*!
         * nello switchCase andiamo a controllare l'ID e disabilitiamo e cambiamo l'icona del Fragment
         * in cui ci troviamo (Abbiamo preso spunto da Instagram)
         */
        switch (fromWho) {
            case R.id.navigation_home:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.home_full);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_prova_1);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer);

                menu.findItem(R.id.navigation_home).setEnabled(false);
                menu.findItem(R.id.navigation_shake).setEnabled(true);
                menu.findItem(R.id.navigation_favorite).setEnabled(true);
                break;
            case R.id.navigation_shake:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_prova_1);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer);

                menu.findItem(R.id.navigation_home).setEnabled(true);
                menu.findItem(R.id.navigation_shake).setEnabled(false);
                menu.findItem(R.id.navigation_favorite).setEnabled(true);
                break;
            case R.id.navigation_favorite:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_prova_1);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer_full);

                menu.findItem(R.id.navigation_home).setEnabled(true);
                menu.findItem(R.id.navigation_shake).setEnabled(true);
                menu.findItem(R.id.navigation_favorite).setEnabled(false);
                break;
            default:
                menu.findItem(R.id.navigation_home).setIcon(R.drawable.ic_home_black);
                menu.findItem(R.id.navigation_shake).setIcon(R.drawable.shake_prova_1);
                menu.findItem(R.id.navigation_favorite).setIcon(R.drawable.prefer);

                menu.findItem(R.id.navigation_home).setEnabled(true);
                menu.findItem(R.id.navigation_shake).setEnabled(true);
                menu.findItem(R.id.navigation_favorite).setEnabled(true);
                break;
        }
    }
}
