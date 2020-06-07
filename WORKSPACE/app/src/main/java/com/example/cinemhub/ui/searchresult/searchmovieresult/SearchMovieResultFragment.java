package com.example.cinemhub.ui.searchresult.searchmovieresult;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.example.cinemhub.R;
import com.example.cinemhub.adapters.MovieListVerticalAdapter;
import com.example.cinemhub.databinding.FragmentSearchMovieResultBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.ui.searchresult.SearchResultFragmentDirections;
import com.example.cinemhub.utils.Constants;

import java.util.List;

public class SearchMovieResultFragment extends Fragment {
    private MovieListVerticalAdapter movieListVerticalAdapter;
    private SearchMovieResultViewModel searchMovieResultViewModel;
    private FragmentSearchMovieResultBinding binding;
    private int movieTotalItemCount;
    private int movieLastVisibleItem;
    private int movieVisibleItemCount;
    private int movieThreshold =1;
    private int year;
    private int categorie;
    private String query;
    private final String TAG = "SearchMovieFragment";

    public SearchMovieResultFragment(int year, String query, int categorie) {
        this.year = year;
        this.query = query;
        this.categorie=categorie;
    }

    public SearchMovieResultFragment() {
    }

    public static SearchMovieResultFragment newInstance() {
        return new SearchMovieResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSearchMovieResultBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        searchMovieResultViewModel =new ViewModelProvider(getActivity()).get(SearchMovieResultViewModel.class);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        binding.RecyclerViewSearch.setLayoutManager(layoutManager);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        String region=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);
        movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), getMovieList(getString(R.string.API_LANGUAGE), checkAdult, query,region , year), new MovieListVerticalAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Movie movie) {
                Navigation.findNavController(getView()).navigate(SearchResultFragmentDirections.actionNavigationSearchResultToNavigationMovieCard(movie.getId()));
            }
        });
        binding.RecyclerViewSearch.setAdapter(movieListVerticalAdapter);

        binding.RecyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override

            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                movieTotalItemCount = layoutManager.getItemCount();
                movieLastVisibleItem = layoutManager.findLastVisibleItemPosition();
                movieVisibleItemCount = layoutManager.getChildCount();

                if((movieTotalItemCount <= (movieLastVisibleItem + movieThreshold) && dy>0 && !searchMovieResultViewModel.isMovieIsLoading()) &&
                        searchMovieResultViewModel.getMovieLiveData().getValue() != null &&
                        searchMovieResultViewModel.getMovieCurrentResults()!=searchMovieResultViewModel.getMovieLiveData().getValue().getTotalResult()
                ){
                    Resource<List<Movie>> movieListResource=new Resource<>();

                    MutableLiveData<Resource<List<Movie>>> movieListMutableLiveData = searchMovieResultViewModel.getMovieLiveData();

                    if(movieListMutableLiveData!=null && movieListMutableLiveData.getValue() != null){
                        searchMovieResultViewModel.setMovieIsLoading(true);

                        List<Movie> currentMovieList = movieListMutableLiveData.getValue().getData();
                        currentMovieList.add(null);
                        movieListResource.setData(currentMovieList);
                        movieListResource.setStatusMessage(movieListMutableLiveData.getValue().getStatusMessage());
                        movieListResource.setTotalResult(movieListMutableLiveData.getValue().getTotalResult());
                        movieListResource.setStatusCode(movieListMutableLiveData.getValue().getStatusCode());

                        movieListResource.setLoading(true);
                        movieListMutableLiveData.postValue(movieListResource);

                        int page=searchMovieResultViewModel.getMoviePage() + 1;
                        searchMovieResultViewModel.setMoviePage(page);

                        searchMovieResultViewModel.getMoreMovieSearch(getString(R.string.API_LANGUAGE), checkAdult, query,region,year,categorie);
                    }
                }

            }

        });
        final Observer<Resource<List<Movie>>> observer_movie_search=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb Search"+movies);

                movieListVerticalAdapter.setData(movies.getData());

                if(!movies.isLoading()){
                    searchMovieResultViewModel.setMovieIsLoading(false);
                    searchMovieResultViewModel.setMovieCurrentResults(movies.getData().size());
                }
            }
        };
        searchMovieResultViewModel.getMovieSearch(getString(R.string.API_LANGUAGE), checkAdult, query ,region, year,categorie).observe(getViewLifecycleOwner(), observer_movie_search);


    }


    private List<Movie> getMovieList(String language, boolean checkAdult, String query, String region, int year){
        Resource<List<Movie>> movieListResult=searchMovieResultViewModel.getMovieSearch(language, checkAdult,query,region,year,categorie).getValue();
        if(movieListResult != null){
            return movieListResult.getData();
        }
        return null;
    }


}
