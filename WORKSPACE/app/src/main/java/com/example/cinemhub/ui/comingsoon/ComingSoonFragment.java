package com.example.cinemhub.ui.comingsoon;

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
import com.example.cinemhub.databinding.FragmentComingSoonBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.utils.Constants;

import java.util.List;

public class ComingSoonFragment extends Fragment {

    private ComingSoonViewModel mViewModel;
    private final String TAG = "ComingSoonFragment";
    private FragmentComingSoonBinding binding;

    public static ComingSoonFragment newInstance() {
        return new ComingSoonFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(getActivity()).get(ComingSoonViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentComingSoonBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_coming_soon));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        binding.ComingSoonRecyclerView.setLayoutManager(layoutManager);


        final Observer<Resource<List<Movie>>> observer_coming_soon=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);
                MovieListVerticalAdapter movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), movies.getData(), new MovieListVerticalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Movie movie) {
                        Navigation.findNavController(getView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationMovieCard(movie.getId()));

                    }
                });
                binding.ComingSoonRecyclerView.setAdapter(movieListVerticalAdapter);

            }
        };

        Movie[] coming_soon_movies=ComingSoonFragmentArgs.fromBundle(getArguments()).getMovieComingSoonArray();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);

        int total_result=ComingSoonFragmentArgs.fromBundle(getArguments()).getTotalResults();
        int status_code=ComingSoonFragmentArgs.fromBundle(getArguments()).getStatusCode();
        String status_message=ComingSoonFragmentArgs.fromBundle(getArguments()).getStatusMessage();
        Resource<List<Movie>> resource=new Resource<>(Movie.toList(coming_soon_movies, checkAdult), total_result, status_code, status_message);
        mViewModel.getMovieComingSoon(getString(R.string.API_LANGUAGE), 1, checkAdult, resource).observe(getViewLifecycleOwner(), observer_coming_soon);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ComingSoonViewModel.class);
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_coming_soon);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(getView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationSettings());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }



}
