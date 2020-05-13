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
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;
    private final int MAX_LENGHT = 14;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel=new ViewModelProvider(getActivity()).get(HomeViewModel.class);
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_home));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_home);


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
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.NowPlayingImage1);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText1.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText1.setText(movie.getTitle());
                }
                if(movies.size()>1) {
                    Movie movie = movies.get(1);

                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.NowPlayingImage2);
                    binding.NowPlayingText2.setText(movie.getTitle());
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText2.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText2.setText(movie.getTitle());
                }
                if(movies.size()>2){
                    Movie movie = movies.get(2);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.NowPlayingImage3);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText3.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText3.setText(movie.getTitle());

                }
                if(movies.size()>3){
                    Movie movie = movies.get(3);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.NowPlayingImage4);

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText4.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText4.setText(movie.getTitle());
                }
                if(movies.size()>4){
                    Movie movie = movies.get(4);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.NowPlayingImage5);

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText5.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText5.setText(movie.getTitle());
                }
                if(movies.size()>5){
                    Movie movie = movies.get(5);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.NowPlayingImage6);

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText6.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText6.setText(movie.getTitle());
                }
                if(movies.size()>6){
                    Movie movie = movies.get(6);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.NowPlayingImage7);

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText7.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText7.setText(movie.getTitle());
                }
                if(movies.size()>7){
                    Movie movie = movies.get(7);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.NowPlayingImage8);

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.NowPlayingText8.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.NowPlayingText8.setText(movie.getTitle());

                }
                if(movies.size()>8){
                    Movie movie = movies.get(8);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.NowPlayingImage9);

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
                if(movies.size()>0) {
                    Movie movie = movies.get(0);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.TopRatedImage1);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.TopRatedText1.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.TopRatedText1.setText(movie.getTitle());
                }
                if(movies.size()>1) {
                    Movie movie = movies.get(1);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.TopRatedImage2);                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.TopRatedText2.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.TopRatedText2.setText(movie.getTitle());
                }
                if(movies.size()>2){
                    Movie movie = movies.get(2);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.TopRatedImage3);                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.TopRatedText3.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.TopRatedText3.setText(movie.getTitle());

                }
                if(movies.size()>3){
                    Movie movie = movies.get(3);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.TopRatedImage4);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.TopRatedText4.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.TopRatedText4.setText(movie.getTitle());
                }
                if(movies.size()>4){
                    Movie movie = movies.get(4);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.TopRatedImage5);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.TopRatedText5.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.TopRatedText5.setText(movie.getTitle());
                }
                if(movies.size()>5){
                    Movie movie = movies.get(5);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.TopRatedImage6);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.TopRatedText6.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.TopRatedText6.setText(movie.getTitle());
                }
                if(movies.size()>6){
                    Movie movie = movies.get(6);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.TopRatedImage7);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.TopRatedText7.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.TopRatedText7.setText(movie.getTitle());
                }
                if(movies.size()>7){
                    Movie movie = movies.get(7);
                    new MainActivity.DownloadImageTask((ImageView) binding.TopRatedImage8)
                            .execute("https://image.tmdb.org/t/p/w500"+movies.get(7).getPoster_path());

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.TopRatedText8.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.TopRatedText8.setText(movie.getTitle());

                }
                if(movies.size()>8){
                    Movie movie = movies.get(8);
                    new MainActivity.DownloadImageTask((ImageView) binding.TopRatedImage9)
                            .execute("https://image.tmdb.org/t/p/w500"+movie.getPoster_path());

                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.TopRatedText9.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.TopRatedText9.setText(movie.getTitle());
                }
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
                if(movies.size()>0) {
                    Movie movie = movies.get(0);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.ComingSoonImage1);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.ComingSoonText1.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.ComingSoonText1.setText(movie.getTitle());
                }
                if(movies.size()>1) {
                    Movie movie = movies.get(1);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.ComingSoonImage2);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.ComingSoonText2.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.ComingSoonText2.setText(movie.getTitle());
                }
                if(movies.size()>2){
                    Movie movie = movies.get(2);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.ComingSoonImage3);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.ComingSoonText3.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.ComingSoonText3.setText(movie.getTitle());

                }
                if(movies.size()>3){
                    Movie movie = movies.get(3);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.ComingSoonImage4);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.ComingSoonText4.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.ComingSoonText4.setText(movie.getTitle());
                }
                if(movies.size()>4){
                    Movie movie = movies.get(4);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.ComingSoonImage5);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.ComingSoonText5.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.ComingSoonText5.setText(movie.getTitle());
                }
                if(movies.size()>5){
                    Movie movie = movies.get(5);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.ComingSoonImage6);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.ComingSoonText6.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.ComingSoonText6.setText(movie.getTitle());
                }
                if(movies.size()>6){
                    Movie movie = movies.get(6);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.ComingSoonImage7);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.ComingSoonText7.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.ComingSoonText7.setText(movie.getTitle());
                }
                if(movies.size()>7){
                    Movie movie = movies.get(7);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.ComingSoonImage8);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.ComingSoonText8.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.ComingSoonText8.setText(movie.getTitle());

                }
                if(movies.size()>8){
                    Movie movie = movies.get(8);
                    Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.ComingSoonImage9);
                    if(movie.getTitle().length() > MAX_LENGHT)
                        binding.ComingSoonText9.setText(movie.getTitle().substring(0,MAX_LENGHT-1)+" ...");
                    else
                        binding.ComingSoonText9.setText(movie.getTitle());
                }
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
