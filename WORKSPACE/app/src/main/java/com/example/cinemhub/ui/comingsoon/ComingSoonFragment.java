package com.example.cinemhub.ui.comingsoon;

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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    private MovieListVerticalAdapter movieListVerticalAdapter;

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


    private List<Movie> getMovieList(String language, boolean checkAdult){
        Resource<List<Movie>> movieListResult=mViewModel.getMovieComingSoon(language, checkAdult).getValue();
        if(movieListResult != null){
            return movieListResult.getData();
        }
        return null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        binding.ComingSoonRecyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);



        movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), getMovieList(getString(R.string.API_LANGUAGE), checkAdult), new MovieListVerticalAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Movie movie) {
                Navigation.findNavController(getView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationMovieCard(movie.getId()));
            }
        });
        binding.ComingSoonRecyclerView.setAdapter(movieListVerticalAdapter);



        binding.ComingSoonRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Resource<List<Movie>> movieListResource=new Resource<>();

                MutableLiveData<Resource<List<Movie>>> movieListMutableLiveData = mViewModel.getMovieLiveData();

                if(movieListMutableLiveData!=null && movieListMutableLiveData.getValue() != null){
                    List<Movie> currentMovieList = movieListMutableLiveData.getValue().getData();
                    currentMovieList.add(null);
                    movieListResource.setData(currentMovieList);
                    movieListResource.setStatusMessage(movieListMutableLiveData.getValue().getStatusMessage());
                    movieListResource.setTotalResult(movieListMutableLiveData.getValue().getTotalResult());
                    movieListResource.setStatusCode(movieListMutableLiveData.getValue().getStatusCode());

                    movieListMutableLiveData.postValue(movieListResource);
                }

            }
        });

        final Observer<Resource<List<Movie>>> observer_coming_soon=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);

                movieListVerticalAdapter.setData(movies.getData());

                /*if(movies!=null && movies.getData()!= null){



                }else{
                    if(movies!= null && movies.getStatusMessage()!=null) {
                        Log.d(TAG, "ERROR CODE: " + movies.getStatusCode() + " ERROR MESSAGE: " + movies.getStatusMessage());
                    }
                    Toast toast;
                    toast = Toast.makeText(getContext(), getString(R.string.error_message_movie)+getString(R.string.title_coming_soon) , Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }*/
            }
        };

        /*Movie[] coming_soon_movies=ComingSoonFragmentArgs.fromBundle(getArguments()).getMovieComingSoonArray();

        int total_result=ComingSoonFragmentArgs.fromBundle(getArguments()).getTotalResults();
        int status_code=ComingSoonFragmentArgs.fromBundle(getArguments()).getStatusCode();
        String status_message=ComingSoonFragmentArgs.fromBundle(getArguments()).getStatusMessage();
        Resource<List<Movie>> resource=new Resource<>(Movie.toList(coming_soon_movies, checkAdult), total_result, status_code, status_message);*/
        mViewModel.getMovieComingSoon(getString(R.string.API_LANGUAGE), checkAdult).observe(getViewLifecycleOwner(), observer_coming_soon);
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
