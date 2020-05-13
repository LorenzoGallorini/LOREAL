package com.example.cinemhub.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentHomeBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.comingsoon.ComingSoonFragment;
import com.example.cinemhub.ui.nowplaying.NowPlayingFragment;
import com.example.cinemhub.ui.settings.SettingsFragment;
import com.example.cinemhub.ui.toprated.TopRatedFragment;

import java.io.InputStream;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
private final int MAX_LENGHT = 14;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_home));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_home);
        homeViewModel=new ViewModelProvider(this).get(HomeViewModel.class);


        final Observer<List<Movie>> observer_now_playing=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb nowplaying"+movies);
                for(int i=0;i<movies.size();i++){
                    Log.d(TAG, "film numero "+i+" "+movies.get(i).toString());

                }
                InputStream is = null;
                if(movies.size()>0) {
                    Movie movie = movies.get(0);
                    new MainActivity.DownloadImageTask((ImageView) binding.NowPlayingImage1)
                            .execute("https://image.tmdb.org/t/p/w500" + movie.getPoster_path());
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText1.setText(movie.getTitle().substring(0,MAX_LENGHT-1));
                    else
                        binding.NowPlayingText1.setText(movie.getTitle());
                }
                if(movies.size()>1) {
                    Movie movie = movies.get(1);
                    new MainActivity.DownloadImageTask((ImageView) binding.NowPlayingImage2)
                            .execute("https://image.tmdb.org/t/p/w500" + movie.getPoster_path());
                    binding.NowPlayingText2.setText(movie.getTitle());
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText2.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText2.setText(movie.getTitle());
                }
                if(movies.size()>2){
                    Movie movie = movies.get(2);
                new MainActivity.DownloadImageTask((ImageView) binding.NowPlayingImage3)
                        .execute("https://image.tmdb.org/t/p/w500"+movie.getPoster_path());
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText3.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText3.setText(movie.getTitle());

            }
                if(movies.size()>3){
                    Movie movie = movies.get(3);
                new MainActivity.DownloadImageTask((ImageView) binding.NowPlayingImage4)
                        .execute("https://image.tmdb.org/t/p/w500"+movie.getPoster_path());

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText4.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText4.setText(movie.getTitle());
            }
                if(movies.size()>4){
                    Movie movie = movies.get(4);
                new MainActivity.DownloadImageTask((ImageView) binding.NowPlayingImage5)
                        .execute("https://image.tmdb.org/t/p/w500"+movie.getPoster_path());

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText5.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText5.setText(movie.getTitle());
                }
                if(movies.size()>5){
                    Movie movie = movies.get(5);
                new MainActivity.DownloadImageTask((ImageView) binding.NowPlayingImage6)
                        .execute("https://image.tmdb.org/t/p/w500"+movie.getPoster_path());

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText6.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText6.setText(movie.getTitle());
                }
                if(movies.size()>6){
                    Movie movie = movies.get(6);
                new MainActivity.DownloadImageTask((ImageView) binding.NowPlayingImage7)
                        .execute("https://image.tmdb.org/t/p/w500"+movies.get(6).getPoster_path());

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText7.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText7.setText(movie.getTitle());
                }
                if(movies.size()>7){
                    Movie movie = movies.get(7);
                new MainActivity.DownloadImageTask((ImageView) binding.NowPlayingImage8)
                        .execute("https://image.tmdb.org/t/p/w500"+movies.get(7).getPoster_path());

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText8.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText8.setText(movie.getTitle());

                }
                if(movies.size()>8){
                    Movie movie = movies.get(8);
                new MainActivity.DownloadImageTask((ImageView) binding.NowPlayingImage9)
                        .execute("https://image.tmdb.org/t/p/w500"+movie.getPoster_path());

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText9.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText9.setText(movie.getTitle());
                }

            }
        };
        homeViewModel.getMovieNowPlaying("it-IT", 1).observe(getViewLifecycleOwner(), observer_now_playing);//TODO settare delle variabili globali per la lingua e per la pagina



        binding.textViewShowAllNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AllNowPlayingClick");
                Fragment newFragment = new NowPlayingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        final Observer<List<Movie>> observer_top_rated=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb toprated"+movies);
                for(int i=0;i<movies.size();i++){
                    Log.d(TAG, "film numero "+i+" "+movies.get(i).toString());

                }
                InputStream is = null;
            }
        };
        homeViewModel.getMovieTopRated("it-IT", 1).observe(getViewLifecycleOwner(), observer_top_rated);


        binding.textView2ShowAllTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AllRatedClick");
                Fragment newFragment = new TopRatedFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        final Observer<List<Movie>> observer_coming_soon=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);
                for(int i=0;i<movies.size();i++){
                    Log.d(TAG, "film numero "+i+" "+movies.get(i).toString());

                }
                InputStream is = null;
            }
        };
        homeViewModel.getMovieComingSoon("it-IT", 1).observe(getViewLifecycleOwner(), observer_coming_soon);

        binding.textViewShowAllComingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AllComingSoonClick");
                Fragment newFragment = new ComingSoonFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.settings){
            Log.d(TAG, "onClick: SettingsClick");

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment,new SettingsFragment(), null);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
        return false;
    }



}
