package com.example.cinemhub.ui.nowplaying;

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
import com.example.cinemhub.databinding.FragmentNowPlayingBinding;
import com.example.cinemhub.models.Movie;

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
        final Observer<List<Movie>> observer_now_playing=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);
                MovieListVerticalAdapter movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), movies, new MovieListVerticalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(Movie movie) {

                        Navigation.findNavController(view).navigate(NowPlayingFragmentDirections.nowPlayingOpenMovieCardAction(movie.getId()));
                        Log.d(TAG, "onclick listener");
                        //fragmentTransactionMethod(new MovieCardFragment(), movie.getId());

                    }
                });
                binding.recyclerViewNowPlaying.setAdapter(movieListVerticalAdapter);
            }
        };
        mViewModel.getMovieNowPlaying(getString(R.string.API_LANGUAGE), 1).observe(getViewLifecycleOwner(), observer_now_playing);
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
        int id=item.getItemId();
        if(id==R.id.search){
            Log.d(TAG, "onClick: SearchClick");
            Navigation.findNavController(getView()).navigate(NowPlayingFragmentDirections.actionNavigationNowPlayingToNavigationSearch());
            return true;
        }else if(id==R.id.settings){
            Log.d(TAG, "onClick: SettingsClick");
            Navigation.findNavController(getView()).navigate(NowPlayingFragmentDirections.actionNavigationNowPlayingToNavigationSettings());
            return true;
        }
        return false;
    }



}
