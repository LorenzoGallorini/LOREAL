package com.example.cinemhub.ui.moviecard;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentMovieCardBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.settings.SettingsFragment;
import com.squareup.picasso.Picasso;

public class MovieCardFragment extends Fragment {

    private MovieCardViewModel mViewModel;
    private FragmentMovieCardBinding binding;
    private final String TAG="MovieCard";

    public static MovieCardFragment newInstance() {
        return new MovieCardFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieCardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_movie_card));

        mViewModel=new ViewModelProvider(this).get(MovieCardViewModel.class);

        final Observer<Movie> observer_details=new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                Log.d(TAG, "movie tmdb details"+movie);
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.filmImage);

                binding.filmTextTitle.setText(movie.getTitle());

            }
        };

        Bundle bundle = getArguments();
        int value = bundle.getInt("MovieId");

        mViewModel.getMovieDetails(value, "it-IT").observe(getViewLifecycleOwner(), observer_details);//TODO settare delle variabili globali per la lingua e per la pagina

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieCardViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.settings){
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

}
