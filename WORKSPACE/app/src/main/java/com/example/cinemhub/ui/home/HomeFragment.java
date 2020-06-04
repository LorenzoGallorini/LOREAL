package com.example.cinemhub.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.ui.moviecard.MovieCardFragmentDirections;
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

        RecyclerView.LayoutManager layoutManagerNowPlaying = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManagerTopRated = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManagerComingSoon = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        binding.NowPlayingRecyclerView.setLayoutManager(layoutManagerNowPlaying);
        binding.TopRatedRecyclerView.setLayoutManager(layoutManagerTopRated);
        binding.ComingSoonRecyclerView.setLayoutManager(layoutManagerComingSoon);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        String region=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);
        int page=1;

        final Observer<Resource<List<Movie>>> observer_now_playing=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb nowplaying"+movies);
                if (movies!=null && movies.getData()!= null){
                    List<Movie> appMovies=movies.getData();
                    if (appMovies.size()>12){
                        appMovies=appMovies.subList(0,12);
                    }
                    MovieListVerticalAdapter movieListVerticalAdapter=new MovieListVerticalAdapter(
                            getActivity(), appMovies, new MovieListVerticalAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Movie movie) {
                            Navigation.findNavController(view).navigate(
                                    HomeFragmentDirections.actionNavigationHomeToNavigationMovieCard(movie.getId()));
                        }
                    });
                    binding.NowPlayingRecyclerView.setAdapter(movieListVerticalAdapter);
                    binding.textViewShowAllNowPlaying.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: AllNowPlayingClick");
                            Navigation.findNavController(view).navigate(
                                    HomeFragmentDirections.actionNavigationHomeToNavigationNowPlaying(
                                            Movie.fromListToArray(movies.getData()), movies.getTotalResult(), movies.getStatusCode(), movies.getStatusMessage()));
                        }
                    });
                }else {
                    if(movies!= null && movies.getStatusMessage()!=null) {
                        Log.d(TAG, "ERROR CODE: " + movies.getStatusCode() + " ERROR MESSAGE: " + movies.getStatusMessage());
                    }
                    Toast toast;
                    toast = Toast.makeText(getContext(), getString(R.string.error_message_movie)+getString(R.string.title_now_playing) , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        };
        homeViewModel.getMovieNowPlaying(getString(R.string.API_LANGUAGE), page, checkAdult, region).observe(getViewLifecycleOwner(), observer_now_playing);

        final Observer<Resource<List<Movie>>> observer_top_rated=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb toprated"+movies);
                if(movies!=null && movies.getData()!= null){
                    List<Movie> appMovies=movies.getData();
                    if(appMovies.size()>12){
                        appMovies=appMovies.subList(0, 12);
                    }
                    MovieListVerticalAdapter movieListVerticalAdapter=new MovieListVerticalAdapter(getActivity(), appMovies, new MovieListVerticalAdapter.OnItemClickListener() {
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
                            Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationTopRated(
                                    Movie.fromListToArray(movies.getData()), movies.getTotalResult(), movies.getStatusCode(), movies.getStatusMessage()));
                        }
                    });
                }else {
                    if(movies!= null && movies.getStatusMessage()!=null) {
                        Log.d(TAG, "ERROR CODE: " + movies.getStatusCode() + " ERROR MESSAGE: " + movies.getStatusMessage());
                    }
                    Toast toast;
                    toast = Toast.makeText(getContext(), getString(R.string.error_message_movie)+getString(R.string.title_top_rated) , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        };
        homeViewModel.getMovieTopRated(getString(R.string.API_LANGUAGE), page, checkAdult, region).observe(getViewLifecycleOwner(), observer_top_rated);

        final Observer<Resource<List<Movie>>> observer_coming_soon=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);
                if(movies!=null && movies.getData()!= null){
                    List<Movie> appMovies=movies.getData();
                    if(appMovies.size()>12){
                        appMovies=appMovies.subList(0,12);
                    }
                    MovieListVerticalAdapter movieListVerticalAdapter=new MovieListVerticalAdapter(
                            getActivity(), appMovies, new MovieListVerticalAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Movie movie) {
                            Navigation.findNavController(view).navigate(
                                    HomeFragmentDirections.actionNavigationHomeToNavigationMovieCard(movie.getId()));

                        }
                    });
                    binding.ComingSoonRecyclerView.setAdapter(movieListVerticalAdapter);

                    binding.textViewShowAllComingSoon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: AllComingSoonClick");
                            Navigation.findNavController(view).navigate(HomeFragmentDirections.actionNavigationHomeToNavigationComingSoon(
                                    Movie.fromListToArray(movies.getData()), movies.getTotalResult(), movies.getStatusCode(), movies.getStatusMessage()));
                        }
                    });
                }else {
                    if(movies!= null && movies.getStatusMessage()!=null) {
                        Log.d(TAG, "ERROR CODE: " + movies.getStatusCode() + " ERROR MESSAGE: " + movies.getStatusMessage());
                    }
                    Toast toast;
                    toast = Toast.makeText(getContext(), getString(R.string.error_message_movie)+getString(R.string.title_coming_soon) , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        };
        homeViewModel.getMovieComingSoon(getString(R.string.API_LANGUAGE), page, checkAdult, region).observe(getViewLifecycleOwner(), observer_coming_soon);
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
