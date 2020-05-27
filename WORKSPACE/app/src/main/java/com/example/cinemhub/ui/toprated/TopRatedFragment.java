package com.example.cinemhub.ui.toprated;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.MovieListVerticalAdapter;
import com.example.cinemhub.databinding.FragmentTopRatedBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.moviecard.MovieCardFragmentDirections;
import com.example.cinemhub.utils.Constants;

import java.util.List;

public class TopRatedFragment extends Fragment {

    private TopRatedViewModel mViewModel;
    private final String TAG = "TopRatedFragment";
    private FragmentTopRatedBinding binding;

    public static TopRatedFragment newInstance() {
        return new TopRatedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(getActivity()).get(TopRatedViewModel.class);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTopRatedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_top_rated));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_top_rated);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        binding.TopRatedRecyclerView.setLayoutManager(layoutManager);

        final Observer<List<Movie>> observer_top_rated=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);
                MovieListVerticalAdapter movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), movies, new MovieListVerticalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Movie movie) {
                        Navigation.findNavController(getView()).navigate(TopRatedFragmentDirections.actionNavigationTopRatedToNavigationMovieCard(movie.getId()));
                    }
                });
                binding.TopRatedRecyclerView.setAdapter(movieListVerticalAdapter);

            }
        };
        Movie[] top_rated_movies=TopRatedFragmentArgs.fromBundle(getArguments()).getMovieTopRatedArray();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        mViewModel.getMovieTopRated(getString(R.string.API_LANGUAGE), 1,checkAdult, top_rated_movies).observe(getViewLifecycleOwner(), observer_top_rated);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TopRatedViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(getView()).navigate(TopRatedFragmentDirections.actionNavigationTopRatedToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(TopRatedFragmentDirections.actionNavigationTopRatedToNavigationSettings());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }



}
