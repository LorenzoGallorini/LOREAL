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
import com.example.cinemhub.adapters.PeopleListVerticalAdapter;
import com.example.cinemhub.databinding.FragmentSearchresultBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.utils.Constants;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class SearchResultFragment extends Fragment {

    private SearchresultViewModel mViewModel;
    private String TAG="SearchResultFragment";
    private FragmentSearchresultBinding binding;
    private MovieListVerticalAdapter movieListVerticalAdapter;
    private PeopleListVerticalAdapter peopleListVerticalAdapter;
    private int movieTotalItemCount;
    private int movieLastVisibleItem;
    private int movieVisibleItemCount;
    private int movieThreshold =1;
    private int peopleTotalItemCount;
    private int peopleLastVisibleItem;
    private int peopleVisibleItemCount;
    private int peopleThreshold=1;

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
        String query = SearchResultFragmentArgs.fromBundle(getArguments()).getQueryValue();

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        GridLayoutManager layoutManagerPeople = new GridLayoutManager(getActivity(),3);
        binding.RecyclerViewSearch.setLayoutManager(layoutManager);
        binding.RecyclerViewSearchPeople.setLayoutManager(layoutManagerPeople);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        String region=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);

        movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), getMovieList(getString(R.string.API_LANGUAGE), checkAdult, query,region , year), new MovieListVerticalAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Movie movie) {
                Navigation.findNavController(getView()).navigate(SearchResultFragmentDirections.actionNavigationSearchResultToNavigationMovieCard(movie.getId()));
            }
        });
        peopleListVerticalAdapter = new PeopleListVerticalAdapter(getActivity(), getPeopleList(getString(R.string.API_LANGUAGE), checkAdult, query,region), new PeopleListVerticalAdapter.OnItemClickListener(){

            @Override
            public void OnItemClick(People people) {
                Navigation.findNavController(getView()).navigate(SearchResultFragmentDirections.actionNavigationSearchResultToNavigationPeopleCard(people.getId()));
            }
        });
        binding.RecyclerViewSearch.setAdapter(movieListVerticalAdapter);
        binding.RecyclerViewSearchPeople.setAdapter(peopleListVerticalAdapter);
        binding.RecyclerViewSearchPeople.setVisibility(View.INVISIBLE);
        binding.RecyclerViewSearchPeople.setAlpha(0);
        binding.RecyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override

            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                movieTotalItemCount = layoutManager.getItemCount();
                movieLastVisibleItem = layoutManager.findLastVisibleItemPosition();
                movieVisibleItemCount = layoutManager.getChildCount();

                if(movieTotalItemCount == movieVisibleItemCount ||
                        (movieTotalItemCount <= (movieLastVisibleItem + movieThreshold) && dy>0 && !mViewModel.isMovieIsLoading()) &&
                                mViewModel.getMovieLiveData().getValue() != null &&
                                mViewModel.getMovieCurrentResults()!=mViewModel.getMovieLiveData().getValue().getTotalResult()
                ){
                    Resource<List<Movie>> movieListResource=new Resource<>();

                    MutableLiveData<Resource<List<Movie>>> movieListMutableLiveData = mViewModel.getMovieLiveData();

                    if(movieListMutableLiveData!=null && movieListMutableLiveData.getValue() != null){
                        mViewModel.setMovieIsLoading(true);

                        List<Movie> currentMovieList = movieListMutableLiveData.getValue().getData();
                        currentMovieList.add(null);
                        movieListResource.setData(currentMovieList);
                        movieListResource.setStatusMessage(movieListMutableLiveData.getValue().getStatusMessage());
                        movieListResource.setTotalResult(movieListMutableLiveData.getValue().getTotalResult());
                        movieListResource.setStatusCode(movieListMutableLiveData.getValue().getStatusCode());

                        movieListResource.setLoading(true);
                        movieListMutableLiveData.postValue(movieListResource);

                        int page=mViewModel.getMoviePage() + 1;
                        mViewModel.setMoviePage(page);

                        mViewModel.getMoreMovieSearch(getString(R.string.API_LANGUAGE), checkAdult, query,region,year);
                    }
                }



            }
        });
        binding.RecyclerViewSearchPeople.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                peopleTotalItemCount = layoutManagerPeople.getItemCount();
                peopleLastVisibleItem = layoutManagerPeople.findLastVisibleItemPosition();
                peopleVisibleItemCount = layoutManagerPeople.getChildCount();

                if(peopleTotalItemCount==peopleVisibleItemCount ||
                        (peopleTotalItemCount <= (peopleLastVisibleItem + peopleThreshold) && dy>0 && !mViewModel.isPeopleIsLoading()) &&
                                mViewModel.getPeopleLiveData().getValue() != null &&
                                mViewModel.getPeopleCurrentResults()!=mViewModel.getPeopleLiveData().getValue().getTotalResult()
                ){
                    Resource<List<People>> peopleListResource=new Resource<>();

                    MutableLiveData<Resource<List<People>>> peopleListMutableLiveData = mViewModel.getPeopleLiveData();

                    if(peopleListMutableLiveData!=null && peopleListMutableLiveData.getValue() != null){
                        mViewModel.setPeopleIsLoading(true);

                        List<People> currentPeopleList = peopleListMutableLiveData.getValue().getData();
                        currentPeopleList.add(null);
                        peopleListResource.setData(currentPeopleList);
                        peopleListResource.setStatusMessage(peopleListMutableLiveData.getValue().getStatusMessage());
                        peopleListResource.setTotalResult(peopleListMutableLiveData.getValue().getTotalResult());
                        peopleListResource.setStatusCode(peopleListMutableLiveData.getValue().getStatusCode());

                        peopleListResource.setLoading(true);
                        peopleListMutableLiveData.postValue(peopleListResource);

                        int page=mViewModel.getPeoplePage() + 1;
                        mViewModel.setPeoplePage(page);

                        mViewModel.getMorePeopleSearch(getString(R.string.API_LANGUAGE), checkAdult, query,region);
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
                    mViewModel.setMovieIsLoading(false);
                    mViewModel.setMovieCurrentResults(movies.getData().size());
                }
            }
        };
        final Observer<Resource<List<People>>> observer_people_search=new Observer<Resource<List<People>>>() {
            @Override
            public void onChanged(Resource<List<People>> people) {
                Log.d(TAG, "lista tmdb Search"+people);

                peopleListVerticalAdapter.setData(people.getData());

                if(!people.isLoading()){
                    mViewModel.setPeopleIsLoading(false);
                    mViewModel.setPeopleCurrentResults(people.getData().size());
                }
            }
        };

        mViewModel.getMoreMovieSearch(getString(R.string.API_LANGUAGE), checkAdult,query,region,year).observe(getViewLifecycleOwner(), observer_movie_search);
        mViewModel.getMorePeopleSearch(getString(R.string.API_LANGUAGE), checkAdult,query,region).observe(getViewLifecycleOwner(), observer_people_search);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                   if(tab.view.getTab().getText().equals(getString(R.string.movie)) )
                   {
                       binding.RecyclerViewSearchPeople.setAlpha(0);
                       binding.RecyclerViewSearchPeople.setVisibility(View.INVISIBLE);
                       binding.RecyclerViewSearch.setAlpha(1);
                       binding.RecyclerViewSearch.setVisibility(View.VISIBLE);
                   }
                   else
                   {
                       binding.RecyclerViewSearch.setAlpha(0);
                       binding.RecyclerViewSearch.setVisibility(View.INVISIBLE);
                       binding.RecyclerViewSearchPeople.setAlpha(1);
                       binding.RecyclerViewSearchPeople.setVisibility(View.VISIBLE);


                   }

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
    private List<People> getPeopleList(String language, boolean checkAdult,String query, String region){
        Resource<List<People>> peopleListResult=mViewModel.getPeopleSearch(language, checkAdult,query,region).getValue();
        if(peopleListResult != null){
            return peopleListResult.getData();
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
