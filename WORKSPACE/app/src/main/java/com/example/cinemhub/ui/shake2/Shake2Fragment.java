package com.example.cinemhub.ui.shake2;

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

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentShake2Binding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.moviecard.MovieCardFragmentDirections;
import com.example.cinemhub.ui.shake.ShakeFragmentDirections;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
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

        ShakeOptions options = new ShakeOptions()
                .background(true)
                .interval(1000)
                .shakeCount(1)
                .sensibility(1.3f);

        if (((MainActivity) getActivity()).shakeDetector2 != null){
            if(!((MainActivity) getActivity()).shakeDetector2.isRunning())
                ((MainActivity) getActivity()).shakeDetector2.start(getContext());
        }else{
            ((MainActivity) getActivity()).shakeDetector2 = new ShakeDetector(options).start(getContext(), new ShakeCallback() {
                @Override
                public void onShake() {
                    Log.d(TAG, "onShake");
                    //shakeDetector2.stopShakeDetector(getActivity());

                    Navigation.findNavController(getView()).navigate(Shake2FragmentDirections.actionNavigationShake2Self());
                }

            });
        }

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
                        Navigation.findNavController(getView()).navigate(Shake2FragmentDirections.actionNavigationShake2ToNavigationMovieCard(movie.getId()));
                    }
                });

            }
        };

        mViewModel.getMovieOnShake(100, getString(R.string.API_LANGUAGE),1).observe(getViewLifecycleOwner(), observer_on_shake);//TODO settare delle variabili globali per la lingua e per la pagina


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Shake2ViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onDestroy() {
        ((MainActivity) getActivity()).shakeDetector2.stopShakeDetector(getContext());

        ((MainActivity) requireActivity()).shakeDetector2.destroy(getContext());
        ((MainActivity) requireActivity()).shakeDetector2=null;
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(getView()).navigate(Shake2FragmentDirections.actionNavigationShake2ToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(Shake2FragmentDirections.actionNavigationShake2ToNavigationSettings());
                return true;
            case android.R.id.home:
                Navigation.findNavController(getView()).navigate(Shake2FragmentDirections.actionNavigationShake2ToNavigationShake());
                return true;
            default:return false;
        }
    }



}
