package com.example.cinemhub.ui.searchresult;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.cinemhub.databinding.FragmentSearchresultBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class SearchResultFragment extends Fragment {

    private SearchresultViewModel mViewModel;

    private String TAG="SearchResultFragment";
    private FragmentSearchresultBinding binding;
    private MovieListVerticalAdapter movieListVerticalAdapter;
    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleItemCount;
    private int threshold=1;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchresultBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.action_search));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_settings);
        mViewModel=new ViewModelProvider(getActivity()).get(SearchresultViewModel.class);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int year= SearchResultFragmentArgs.fromBundle(getArguments()).getYear();
        int categorie = SearchResultFragmentArgs.fromBundle(getArguments()).getCategorie();
        String region = "IT";
        String query = SearchResultFragmentArgs.fromBundle(getArguments()).getQueryValue();

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        binding.RecyclerViewSearch.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);

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

                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                visibleItemCount = layoutManager.getChildCount();

                if(totalItemCount==visibleItemCount ||
                        (totalItemCount <= (lastVisibleItem + threshold) && dy>0 && !mViewModel.isLoading()) &&
                                mViewModel.getMovieLiveData().getValue() != null &&
                                mViewModel.getCurrentResults()!=mViewModel.getMovieLiveData().getValue().getTotalResult()
                ){
                    Resource<List<Movie>> movieListResource=new Resource<>();

                    MutableLiveData<Resource<List<Movie>>> movieListMutableLiveData = mViewModel.getMovieLiveData();

                    if(movieListMutableLiveData!=null && movieListMutableLiveData.getValue() != null){
                        mViewModel.setLoading(true);

                        List<Movie> currentMovieList = movieListMutableLiveData.getValue().getData();
                        currentMovieList.add(null);
                        movieListResource.setData(currentMovieList);
                        movieListResource.setStatusMessage(movieListMutableLiveData.getValue().getStatusMessage());
                        movieListResource.setTotalResult(movieListMutableLiveData.getValue().getTotalResult());
                        movieListResource.setStatusCode(movieListMutableLiveData.getValue().getStatusCode());

                        movieListResource.setLoading(true);
                        movieListMutableLiveData.postValue(movieListResource);

                        int page=mViewModel.getPage() + 1;
                        mViewModel.setPage(page);

                        mViewModel.getMoreMovieSearch(getString(R.string.API_LANGUAGE), checkAdult, query,region,year);
                    }
                }



            }
        });

        final Observer<Resource<List<Movie>>> observer_coming_search=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb Search"+movies);

                movieListVerticalAdapter.setData(movies.getData());

                if(!movies.isLoading()){
                    mViewModel.setLoading(false);
                    mViewModel.setCurrentResults(movies.getData().size());
                }
            }
        };

        mViewModel.getMoreMovieSearch(getString(R.string.API_LANGUAGE), checkAdult,query,region,year).observe(getViewLifecycleOwner(), observer_coming_search);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                   //if( tab.view.getTab().getText().equals(getString(R.string.movie)) )
                       // binding.textView.setText(query);
                  // else
                     //  binding.textView.setText(year + " " + categorie);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }}
        );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.search).setEnabled(false);
        menu.findItem(R.id.search).setVisible(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mViewModel.reset();
                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }
    private List<Movie> getMovieList(String language, boolean checkAdult,String query, String region, int year){
        Resource<List<Movie>> movieListResult=mViewModel.getMovieSearch(language, checkAdult,query,region,year).getValue();
        if(movieListResult != null){
            return movieListResult.getData();
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.reset();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
