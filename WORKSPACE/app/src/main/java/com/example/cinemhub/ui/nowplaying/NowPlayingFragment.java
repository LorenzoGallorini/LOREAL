package com.example.cinemhub.ui.nowplaying;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.example.cinemhub.databinding.FragmentNowPlayingBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.ui.moviecard.MovieCardFragmentDirections;
import com.example.cinemhub.utils.Constants;

import java.util.List;

public class NowPlayingFragment extends Fragment {

    private NowPlayingViewModel mViewModel;
    private final String TAG = "NowPlayingFragment";
    private FragmentNowPlayingBinding binding;
    public static NowPlayingFragment newInstance() {
        return new NowPlayingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(getActivity()).get(NowPlayingViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNowPlayingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_now_playing));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_now_playing);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        binding.recyclerViewNowPlaying.setLayoutManager(layoutManager);

        final Observer<Resource<List<Movie>>> observer_now_playing=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);

                if(movies!=null && movies.getData()!= null){

                    MovieListVerticalAdapter movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), movies.getData(), new MovieListVerticalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Movie movie) {
                        Log.d(TAG, "onclick listener");
                        Navigation.findNavController(view).navigate(NowPlayingFragmentDirections.nowPlayingOpenMovieCardAction(movie.getId()));
                    }
                });
                binding.recyclerViewNowPlaying.setAdapter(movieListVerticalAdapter);
                }else{
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
        Movie[] now_playing_movies=NowPlayingFragmentArgs.fromBundle(getArguments()).getMovieNowPlayingArray();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        int total_result=NowPlayingFragmentArgs.fromBundle(getArguments()).getTotalResults();
        int status_code=NowPlayingFragmentArgs.fromBundle(getArguments()).getStatusCode();
        String status_message=NowPlayingFragmentArgs.fromBundle(getArguments()).getStatusMessage();
        Resource<List<Movie>> resource=new Resource<>(Movie.toList(now_playing_movies, checkAdult), total_result, status_code, status_message);
        mViewModel.getMovieNowPlaying(getString(R.string.API_LANGUAGE), 1, checkAdult, resource).observe(getViewLifecycleOwner(), observer_now_playing);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NowPlayingViewModel.class);
        // TODO: Use the ViewModel
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
                Navigation.findNavController(getView()).navigate(NowPlayingFragmentDirections.actionNavigationNowPlayingToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(NowPlayingFragmentDirections.actionNavigationNowPlayingToNavigationSettings());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }



}
