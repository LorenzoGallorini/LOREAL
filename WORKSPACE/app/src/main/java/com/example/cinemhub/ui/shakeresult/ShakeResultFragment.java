package com.example.cinemhub.ui.shakeresult;

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
import androidx.navigation.Navigation;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentShake2Binding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;


public class ShakeResultFragment extends Fragment {

    private ShakeResultViewModel mViewModel;
    private FragmentShake2Binding binding;
    private final String TAG="Shake2";
    private final int MAX_LENGHT = 14;
    int page=1;

    public static ShakeResultFragment newInstance() {
        return new ShakeResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShake2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_shake));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_shake);
        mViewModel=new ViewModelProvider(getActivity()).get(ShakeResultViewModel.class);


        final Observer<Resource<List<Movie>>> observer_on_shake=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> moviesResource) {
                List<Movie> movies=moviesResource.getData();
                Log.d(TAG, "lista shake"+movies);
                for(int i=0;i<movies.size();i++){
                    Log.d(TAG, "film numero "+i+" "+movies.get(i).toString());
                }
                InputStream is = null;

                Random random=new Random();
                int casual;

                switch (movies.size()){
                    case 0:
                        Toast toast;
                        toast = Toast.makeText(getContext(), getString(R.string.error_message_shake2), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    case 1:
                        casual=0;
                        break;
                    default:
                        casual=random.nextInt(movies.size()-1);
                        break;
                }
                Movie movie = movies.get(casual);
                Picasso.get().load("https://image.tmdb.org/t/p/w500" + movie.getPoster_path()).into(binding.filmImageButton);

                binding.textFilmName.setText(movie.getTitle());

                binding.filmImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Navigation.findNavController(getView()).navigate(ShakeResultFragmentDirections.actionNavigationShake2ToNavigationMovieCard(movie.getId()));
                    }
                });

            }
        };

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        Set<String> preferiti=sharedPreferences.getStringSet(Constants.FAVORITE_SHARED_PREF_NAME+getString(R.string.API_LANGUAGE),null);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);

        ShakeOptions options = new ShakeOptions()
                .background(true)
                .interval(1000)
                .shakeCount(1)
                .sensibility(1.3f);

        if (((MainActivity) getActivity()).shakeResultDetector != null){
            if(!((MainActivity) getActivity()).shakeResultDetector.isRunning())
                ((MainActivity) getActivity()).shakeResultDetector.start(getContext());
        }else{
            ((MainActivity) getActivity()).shakeResultDetector = new ShakeDetector(options).start(getContext(), new ShakeCallback() {
                @Override
                public void onShake() {
                    Log.d(TAG, "onShake");
                    mViewModel.reset();
                    CallShake(preferiti,checkAdult,observer_on_shake,sharedPreferences);
                }

            });
        }
        CallShake(preferiti,checkAdult,observer_on_shake,sharedPreferences);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onDestroy() {



        super.onDestroy();
    }



    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity) getActivity()).shakeResultDetector.stopShakeDetector(getContext());
        ((MainActivity) requireActivity()).shakeResultDetector.destroy(getContext());
        ((MainActivity) requireActivity()).shakeResultDetector =null;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(getView()).navigate(ShakeResultFragmentDirections.actionNavigationShake2ToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(ShakeResultFragmentDirections.actionNavigationShake2ToNavigationSettings());
                return true;
            case android.R.id.home:
                Navigation.findNavController(getView()).navigate(ShakeResultFragmentDirections.actionNavigationShake2ToNavigationShake());
                return true;
            default:return false;
        }
    }


    void CallShake(Set<String> preferiti,boolean checkAdult, Observer<Resource<List<Movie>>> observer_on_shake,
                    SharedPreferences sharedPreferences)
    {
        if(preferiti!=null && preferiti.size()>0){
            int casual_id;
            Object[] favoriteArray=preferiti.toArray();

            List<Integer> intList=new ArrayList<Integer>();
            for (Object o : favoriteArray) {
                intList.add(Integer.parseInt(o.toString().split(Constants.SEPARATOR)[0]));
            }
            Random random=new Random();

            if(intList.size()>1){
                casual_id=intList.get(random.nextInt(intList.size()-1));
            }else {
                casual_id=intList.get(0);
            }

            mViewModel.getMovieOnShake(casual_id, getString(R.string.API_LANGUAGE),page, checkAdult).observe(getViewLifecycleOwner(), observer_on_shake);

        }
        else{
            final Observer<Resource<List<Movie>>> observer_top_rated=new Observer<Resource<List<Movie>>>() {
                @Override
                public void onChanged(Resource<List<Movie>> moviesResource) {
                    List<Movie> movies=moviesResource.getData();
                    int casual_id;
                    Log.d(TAG, "shakeresult no favorite");
                    Random random=new Random();

                    switch (movies.size()){
                        case 0:
                            casual_id=100;//id di un film sicuramente presente nel DB
                            break;
                        case 1:
                            casual_id=movies.get(0).getId();
                            break;
                        default:
                            casual_id=movies.get(random.nextInt(movies.size()-1)).getId();
                            break;
                    }

                    boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
                    mViewModel.getMovieOnShake(casual_id, getString(R.string.API_LANGUAGE),page, checkAdult).observe(getViewLifecycleOwner(), observer_on_shake);


                }
            };
            String region=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);

            mViewModel.getMovieTopRated(getString(R.string.API_LANGUAGE), page, checkAdult, region).observe(getViewLifecycleOwner(), observer_top_rated);
        }
    }


}
