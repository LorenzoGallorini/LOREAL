package com.example.cinemhub.ui.moviecard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentMovieCardBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.search.SearchFragment;
import com.example.cinemhub.ui.settings.SettingsFragment;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

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
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_movie_card);


        mViewModel=new ViewModelProvider(this).get(MovieCardViewModel.class);

        final Observer<Movie> observer_details=new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                Log.d(TAG, "movie tmdb details"+movie);
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.filmImage);

                binding.filmTextTitle.setText(movie.getTitle());
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                        Constants.FAVOURITE_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
                Set<String> preferiti;
                try {
                    preferiti=sharedPreferences.getStringSet(Constants.FAVOURITE_SHARED_PREF_NAME,null);
                    if (preferiti == null)
                        preferiti = new HashSet<String>();
                }
                catch(Exception e)
                {
                    preferiti = new HashSet<String>();
                }
                if (preferiti.contains(Integer.toString(movie.getId()))) {
                    binding.MovieCardFavouriteButton.setImageResource(R.drawable.prefer_full);
                }
                else
                {
                    binding.MovieCardFavouriteButton.setImageResource(R.drawable.startplus);
                }
                binding.MovieCardFavouriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Set<String> preferiti;
                        Toast toast;
                        try {
                            preferiti=sharedPreferences.getStringSet(Constants.FAVOURITE_SHARED_PREF_NAME,null);
                            if (preferiti == null)
                                preferiti = new HashSet<String>();
                        }
                        catch(Exception e)
                        {
                            preferiti = new HashSet<String>();
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (preferiti.contains(Integer.toString(movie.getId()))) {
                            Set<String> in = new HashSet<String>(preferiti);
                            in.remove(Integer.toString(movie.getId()));
                            editor.remove(Constants.FAVOURITE_SHARED_PREF_NAME);
                            editor.putStringSet(Constants.FAVOURITE_SHARED_PREF_NAME, in);
                            toast = Toast.makeText(getContext(), "Rimosso " + movie.getTitle() + " dai tuoi preferiti", Toast.LENGTH_SHORT);

                            binding.MovieCardFavouriteButton.setImageResource(R.drawable.startplus);
                        }
                        else {

                            Set<String> in = new HashSet<String>(preferiti);
                            in.add(Integer.toString(movie.getId()));
                            editor.putStringSet(Constants.FAVOURITE_SHARED_PREF_NAME, in);


                           toast = Toast.makeText(getContext(), "Aggiunto " + movie.getTitle() + " ai tuoi preferiti", Toast.LENGTH_SHORT);

                            binding.MovieCardFavouriteButton.setImageResource(R.drawable.prefer_full);
                        }
                        editor.commit();
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        binding.MovieCardFavouriteButton.setClickable(false);


                        new CountDownTimer(2000, 2000) { //Set Timer for 5 seconds
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                binding.MovieCardFavouriteButton.setClickable(true);
                            }
                        }.start();
                    }

                });

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

}
