package com.example.cinemhub.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.MovieListVerticalAdapter;
import com.example.cinemhub.databinding.FragmentHomeBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.moviecard.MovieCardFragmentDirections;
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

        RecyclerView.LayoutManager layoutManagerNowPlaying = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManagerTopRated = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManagerComingSoon = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        binding.NowPlayingRecyclerView.setLayoutManager(layoutManagerNowPlaying);
        binding.TopRatedRecyclerView.setLayoutManager(layoutManagerTopRated);
        binding.ComingSoonRecyclerView.setLayoutManager(layoutManagerComingSoon);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);


        final Observer<List<Movie>> observer_now_playing=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb nowplaying"+movies);
                MovieListVerticalAdapter movieListVerticalAdapter=new MovieListVerticalAdapter(getActivity(), movies.subList(0,12), new MovieListVerticalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Movie movie) {
                        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationMovieCard(movie.getId()));
                    }
                });
                binding.NowPlayingRecyclerView.setAdapter(movieListVerticalAdapter);

                binding.textViewShowAllNowPlaying.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: AllNowPlayingClick");
                        //fragmentTransactionMethod(new NowPlayingFragment());
                        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationNowPlaying(Movie.fromListToArray(movies)));
                    }
                });
            }
        };
        homeViewModel.getMovieNowPlaying(getString(R.string.API_LANGUAGE), 1, checkAdult).observe(getViewLifecycleOwner(), observer_now_playing);//TODO settare delle variabili globali per la pagina



        final Observer<List<Movie>> observer_top_rated=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb toprated"+movies);
                MovieListVerticalAdapter movieListVerticalAdapter=new MovieListVerticalAdapter(getActivity(), movies.subList(0,12), new MovieListVerticalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Movie movie) {
                        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationMovieCard(movie.getId()));

                    }
                });
                binding.TopRatedRecyclerView.setAdapter(movieListVerticalAdapter);

                binding.textView2ShowAllTopRated.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: AllRatedClick");
                        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationTopRated(Movie.fromListToArray(movies)));
                    }
                });
            }
        };
        homeViewModel.getMovieTopRated(getString(R.string.API_LANGUAGE), 1, checkAdult).observe(getViewLifecycleOwner(), observer_top_rated);






        final Observer<List<Movie>> observer_coming_soon=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);
                MovieListVerticalAdapter movieListVerticalAdapter=new MovieListVerticalAdapter(getActivity(), movies.subList(0,12), new MovieListVerticalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Movie movie) {
                        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationMovieCard(movie.getId()));

                    }
                });
                binding.ComingSoonRecyclerView.setAdapter(movieListVerticalAdapter);

                binding.textViewShowAllComingSoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "onClick: AllComingSoonClick");
                        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationComingSoon(Movie.fromListToArray(movies)));
                    }
                });
            }
        };
        homeViewModel.getMovieComingSoon(getString(R.string.API_LANGUAGE), 1, checkAdult).observe(getViewLifecycleOwner(), observer_coming_soon);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(getView()).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationSettings());
                return true;
            default:return false;
        }
    }
}
