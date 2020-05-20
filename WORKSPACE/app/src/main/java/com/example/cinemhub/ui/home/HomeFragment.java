package com.example.cinemhub.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentHomeBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.comingsoon.ComingSoonFragment;
import com.example.cinemhub.ui.moviecard.MovieCardFragment;
import com.example.cinemhub.ui.nowplaying.NowPlayingFragment;
import com.example.cinemhub.ui.search.SearchFragment;
import com.example.cinemhub.ui.settings.SettingsFragment;
import com.example.cinemhub.ui.toprated.TopRatedFragment;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;

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


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_home));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_home);


        final Observer<List<Movie>> observer_now_playing=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb nowplaying"+movies);
                for(int i=0;i<movies.size();i++){
                    Log.d(TAG, "film numero "+i+" "+movies.get(i).toString());

                }
                if(movies.size()>0) {

                    setMoviePreview(movies.get(0), binding.NowPlayingText1, binding.NowPlayingImage1);
                    if(movies.size()>1) {

                        setMoviePreview(movies.get(1), binding.NowPlayingText2, binding.NowPlayingImage2);
                        if(movies.size()>2){

                            setMoviePreview(movies.get(2), binding.NowPlayingText3, binding.NowPlayingImage3);
                            if(movies.size()>3){

                                setMoviePreview(movies.get(3), binding.NowPlayingText4, binding.NowPlayingImage4);
                                if(movies.size()>4){

                                    setMoviePreview(movies.get(4), binding.NowPlayingText5, binding.NowPlayingImage5);
                                    if(movies.size()>5){

                                        setMoviePreview(movies.get(5), binding.NowPlayingText6, binding.NowPlayingImage6);
                                        if(movies.size()>6){

                                            setMoviePreview(movies.get(6), binding.NowPlayingText7, binding.NowPlayingImage7);
                                            if(movies.size()>7){

                                                setMoviePreview(movies.get(7), binding.NowPlayingText8, binding.NowPlayingImage8);
                                                if(movies.size()>8){

                                                    setMoviePreview(movies.get(8), binding.NowPlayingText9, binding.NowPlayingImage9);
                                                    if(movies.size()>9){

                                                        setMoviePreview(movies.get(9), binding.NowPlayingText10, binding.NowPlayingImage10);
                                                        if(movies.size()>10){
                                                            setMoviePreview(movies.get(10), binding.NowPlayingText11, binding.NowPlayingImage11);

                                                            if(movies.size()>11){
                                                                setMoviePreview(movies.get(11), binding.NowPlayingText12, binding.NowPlayingImage12);

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        homeViewModel.getMovieNowPlaying(getString(R.string.API_LANGUAGE), 1).observe(getViewLifecycleOwner(), observer_now_playing);//TODO settare delle variabili globali per la lingua e per la pagina



        binding.textViewShowAllNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AllNowPlayingClick");
                fragmentTransactionMethod(new NowPlayingFragment());
            }
        });

        final Observer<List<Movie>> observer_top_rated=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb toprated"+movies);
                for(int i=0;i<movies.size();i++){
                    Log.d(TAG, "film numero "+i+" "+movies.get(i).toString());

                }
                if(movies.size()>0) {

                    setMoviePreview(movies.get(0), binding.TopRatedText1, binding.TopRatedImage1);
                    if(movies.size()>1) {

                        setMoviePreview(movies.get(1), binding.TopRatedText2, binding.TopRatedImage2);
                        if(movies.size()>2){

                            setMoviePreview(movies.get(2), binding.TopRatedText3, binding.TopRatedImage3);
                            if(movies.size()>3){

                                setMoviePreview(movies.get(3), binding.TopRatedText4, binding.TopRatedImage4);
                                if(movies.size()>4){

                                    setMoviePreview(movies.get(4), binding.TopRatedText5, binding.TopRatedImage5);
                                    if(movies.size()>5){

                                        setMoviePreview(movies.get(5), binding.TopRatedText6, binding.TopRatedImage6);
                                        if(movies.size()>6){

                                            setMoviePreview(movies.get(6), binding.TopRatedText7, binding.TopRatedImage7);
                                            if(movies.size()>7){

                                                setMoviePreview(movies.get(7), binding.TopRatedText8, binding.TopRatedImage8);
                                                if(movies.size()>8){

                                                    setMoviePreview(movies.get(8), binding.TopRatedText9, binding.TopRatedImage9);
                                                    if(movies.size()>9){

                                                        setMoviePreview(movies.get(9), binding.TopRatedText10, binding.TopRatedImage10);
                                                        if(movies.size()>10){
                                                            setMoviePreview(movies.get(10), binding.TopRatedText11, binding.TopRatedImage11);

                                                            if(movies.size()>11){
                                                                setMoviePreview(movies.get(11), binding.TopRatedText12, binding.TopRatedImage12);

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        homeViewModel.getMovieTopRated(getString(R.string.API_LANGUAGE), 1).observe(getViewLifecycleOwner(), observer_top_rated);




        binding.textView2ShowAllTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AllRatedClick");
                fragmentTransactionMethod(new TopRatedFragment());
            }
        });

        final Observer<List<Movie>> observer_coming_soon=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);
                for(int i=0;i<movies.size();i++){
                    Log.d(TAG, "film numero "+i+" "+movies.get(i).toString());

                }
                if(movies.size()>0) {

                    setMoviePreview(movies.get(0), binding.ComingSoonText1, binding.ComingSoonImage1);
                    if(movies.size()>1) {

                        setMoviePreview(movies.get(1), binding.ComingSoonText2, binding.ComingSoonImage2);
                        if(movies.size()>2){

                            setMoviePreview(movies.get(2), binding.ComingSoonText3, binding.ComingSoonImage3);
                            if(movies.size()>3){

                                setMoviePreview(movies.get(3), binding.ComingSoonText4, binding.ComingSoonImage4);
                                if(movies.size()>4){

                                    setMoviePreview(movies.get(4), binding.ComingSoonText5, binding.ComingSoonImage5);
                                    if(movies.size()>5){

                                        setMoviePreview(movies.get(5), binding.ComingSoonText6, binding.ComingSoonImage6);
                                        if(movies.size()>6){

                                            setMoviePreview(movies.get(6), binding.ComingSoonText7, binding.ComingSoonImage7);
                                            if(movies.size()>7){

                                                setMoviePreview(movies.get(7), binding.ComingSoonText8, binding.ComingSoonImage8);
                                                if(movies.size()>8){

                                                    setMoviePreview(movies.get(8), binding.ComingSoonText9, binding.ComingSoonImage9);
                                                    if(movies.size()>9){

                                                        setMoviePreview(movies.get(9), binding.ComingSoonText10, binding.ComingSoonImage10);
                                                        if(movies.size()>10){
                                                            setMoviePreview(movies.get(10), binding.ComingSoonText11, binding.ComingSoonImage11);

                                                            if(movies.size()>11){
                                                                setMoviePreview(movies.get(11), binding.ComingSoonText12, binding.ComingSoonImage12);

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        homeViewModel.getMovieComingSoon(getString(R.string.API_LANGUAGE), 1).observe(getViewLifecycleOwner(), observer_coming_soon);

        binding.textViewShowAllComingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AllComingSoonClick");
                fragmentTransactionMethod(new ComingSoonFragment());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.search){
            Log.d(TAG, "onClick: SearchClick");
            fragmentTransactionMethod(new SearchFragment());
            return true;
        }else if(id==R.id.settings){
            Log.d(TAG, "onClick: SettingsClick");
            fragmentTransactionMethod(new SettingsFragment());
            return true;
        }
        return false;
    }

    private void fragmentTransactionMethod (Fragment newFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void fragmentTransactionMethod (Fragment newFragment, int movie_id){
        Bundle bundle = new Bundle();
        bundle.putInt("MovieId", movie_id);
        newFragment.setArguments(bundle);
        fragmentTransactionMethod(newFragment);
    }

    private void setMoviePreview (Movie movie, TextView textView, ImageButton imageButton){
        if(movie.getPoster_path()!=null){
            Picasso.get().load(Constants.IMAGE_BASE_URL + movie.getPoster_path()).into(imageButton);
        }else {
            imageButton.setImageResource(R.drawable.no_image_avaiable);
        }
        if(movie.getTitle().length() > Constants.MAX_LENGHT)
            textView.setText(movie.getTitle().substring(0,Constants.MAX_LENGHT-1)+" ...");
        else
            textView.setText(movie.getTitle());

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransactionMethod(new MovieCardFragment(), movie.getId());
            }
        });
    }


}
