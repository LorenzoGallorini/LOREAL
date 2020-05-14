package com.example.cinemhub.ui.shake2;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentShake2Binding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.moviecard.MovieCardFragment;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;
import java.util.Random;

import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;

public class Shake2Fragment extends Fragment {

    private Shake2ViewModel mViewModel;
    private FragmentShake2Binding binding;
    private final String TAG="Shake2";
    private final int MAX_LENGHT = 14;

    public static Shake2Fragment newInstance() {
        return new Shake2Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShake2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_shake));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_shake);

        mViewModel=new ViewModelProvider(getActivity()).get(Shake2ViewModel.class);


        final Observer<List<Movie>> observer_on_shake=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista shake"+movies);
                for(int i=0;i<movies.size();i++){
                    Log.d(TAG, "film numero "+i+" "+movies.get(i).toString());
                }
                InputStream is = null;

                Random random=new Random();
                int casual= random.nextInt(movies.size()-1);
                Movie movie = movies.get(casual);
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.filmImageButton);

                binding.textFilmName.setText(movie.getTitle());

                binding.filmImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fragmentTransactionMethod(new MovieCardFragment(), movie.getId());
                    }
                });

            }
        };

        mViewModel.getMovieOnShake(100, "it-IT",1).observe(getViewLifecycleOwner(), observer_on_shake);//TODO settare delle variabili globali per la lingua e per la pagina


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Shake2ViewModel.class);
        // TODO: Use the ViewModel
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

}
