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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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

    private ComingSoonViewModel comingSoonViewModel;
    private final String TAG = "ComingSoonFragment";
    private FragmentComingSoonBinding binding;
    private MovieListVerticalAdapter movieListVerticalAdapter;

    private int totalItemCount;
    private int lastVisibleItem;
    private int threshold=1;

    public static ComingSoonFragment newInstance() {
        return new ComingSoonFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comingSoonViewModel =new ViewModelProvider(requireActivity()).get(ComingSoonViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentComingSoonBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) requireActivity()).setActionBarTitle(getString(R.string.title_coming_soon));
        ((MainActivity) requireActivity()).menuColorSettings(R.id.navigation_coming_soon);

        return view;
    }


    private List<Movie> getMovieList(String language, boolean checkAdult, String region){
        Resource<List<Movie>> movieListResult= comingSoonViewModel.getMovieComingSoon(language, checkAdult, region).getValue();
        if(movieListResult != null){
            return movieListResult.getData();
        }
        return null;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final int comingSoonRVSpanCount = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), comingSoonRVSpanCount);
        binding.ComingSoonRecyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        String region=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);



        movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), getMovieList(getString(R.string.API_LANGUAGE), checkAdult, region), movie -> Navigation.findNavController(requireView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationMovieCard(movie.getId())));
        binding.ComingSoonRecyclerView.setAdapter(movieListVerticalAdapter);



        binding.ComingSoonRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if((totalItemCount <= (lastVisibleItem + threshold) && dy>0 && !comingSoonViewModel.isLoading())
                                &&
                                comingSoonViewModel.getMovieLiveData().getValue() != null
                                &&
                                comingSoonViewModel.getCurrentResults()!= comingSoonViewModel.getMovieLiveData().getValue().getTotalResult()
                ){
                    Resource<List<Movie>> movieListResource=new Resource<>();

                    MutableLiveData<Resource<List<Movie>>> movieListMutableLiveData = comingSoonViewModel.getMovieLiveData();

                    if(movieListMutableLiveData!=null && movieListMutableLiveData.getValue() != null){
                        comingSoonViewModel.setLoading(true);

                        List<Movie> currentMovieList = movieListMutableLiveData.getValue().getData();
                        currentMovieList.add(null);
                        movieListResource.setData(currentMovieList);
                        movieListResource.setStatusMessage(movieListMutableLiveData.getValue().getStatusMessage());
                        movieListResource.setTotalResult(movieListMutableLiveData.getValue().getTotalResult());
                        movieListResource.setStatusCode(movieListMutableLiveData.getValue().getStatusCode());

                        movieListResource.setLoading(true);
                        movieListMutableLiveData.postValue(movieListResource);

                        int page= comingSoonViewModel.getPage() + 1;
                        comingSoonViewModel.setPage(page);

                        comingSoonViewModel.getMoreMovieComingSoon(getString(R.string.API_LANGUAGE), checkAdult, region);
                    }
                }



            }
        });

        final Observer<Resource<List<Movie>>> observer_coming_soon= movies -> {
            Log.d(TAG, "lista tmdb comingsoon"+movies);

            movieListVerticalAdapter.setData(movies.getData());

            if(!movies.isLoading()){
                comingSoonViewModel.setLoading(false);
                comingSoonViewModel.setCurrentResults(movies.getData().size());
            }
        };
        if(region!=null){
            comingSoonViewModel.getMovieComingSoon(getString(R.string.API_LANGUAGE), checkAdult, region).observe(getViewLifecycleOwner(), observer_coming_soon);
        }
        else {
            Log.d(TAG, "ERROR:region cannot be null");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(requireView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(requireView()).navigate(ComingSoonFragmentDirections.actionNavigationComingSoonToNavigationSettings());
                return true;
            case android.R.id.home:
                requireActivity().onBackPressed();
                return true;
            default:return false;
        }
    }





}
