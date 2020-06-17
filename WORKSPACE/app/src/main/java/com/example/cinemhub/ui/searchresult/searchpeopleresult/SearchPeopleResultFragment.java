package com.example.cinemhub.ui.searchresult.searchpeopleresult;

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
import com.example.cinemhub.adapters.PeopleListAdapter;
import com.example.cinemhub.databinding.FragmentSearchMovieResultBinding;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.ui.searchresult.SearchResultFragmentDirections;
import com.example.cinemhub.utils.Constants;

import java.util.List;

public class SearchPeopleResultFragment extends Fragment {


    private FragmentSearchMovieResultBinding binding;
    private SearchPeopleResultViewModel searchPeopleResultViewModel;
    private final String TAG = "PeopleResultFragment";
    private String query;
    private int peopleTotalItemCount;
    private int peopleLastVisibleItem;
    private int peopleVisibleItemCount;
    private int peopleThreshold =1;
    private PeopleListAdapter peopleListAdapter;
    public static SearchPeopleResultFragment newInstance() {
        return new SearchPeopleResultFragment();
    }
    public SearchPeopleResultFragment(String query) {

        this.query = query;
    }

    public SearchPeopleResultFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchMovieResultBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        searchPeopleResultViewModel =new ViewModelProvider(getActivity()).get(SearchPeopleResultViewModel.class);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManagerPeople = new GridLayoutManager(getActivity(),3);
        binding.RecyclerViewSearch.setLayoutManager(layoutManagerPeople);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        String region=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);

        peopleListAdapter = new PeopleListAdapter(getActivity(), getPeopleList(getString(R.string.API_LANGUAGE), checkAdult, query,region), new PeopleListAdapter.OnItemClickListener(){

            @Override
            public void OnItemClick(People people) {
                Navigation.findNavController(getView()).navigate(SearchResultFragmentDirections.actionNavigationSearchResultToNavigationPeopleCard(people.getId()));
            }
        });

        binding.RecyclerViewSearch.setAdapter(peopleListAdapter);

        binding.RecyclerViewSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                peopleTotalItemCount = layoutManagerPeople.getItemCount();
                peopleLastVisibleItem = layoutManagerPeople.findLastVisibleItemPosition();
                peopleVisibleItemCount = layoutManagerPeople.getChildCount();

                if((peopleTotalItemCount <= (peopleLastVisibleItem + peopleThreshold) && dy>0 && !searchPeopleResultViewModel.isPeopleIsLoading()) &&
                        searchPeopleResultViewModel.getPeopleLiveData().getValue() != null &&
                        searchPeopleResultViewModel.getPeopleCurrentResults()!= searchPeopleResultViewModel.getPeopleLiveData().getValue().getTotalResult()
                ){
                    Resource<List<People>> peopleListResource=new Resource<>();

                    MutableLiveData<Resource<List<People>>> peopleListMutableLiveData = searchPeopleResultViewModel.getPeopleLiveData();

                    if(peopleListMutableLiveData!=null && peopleListMutableLiveData.getValue() != null){
                        searchPeopleResultViewModel.setPeopleIsLoading(true);

                        List<People> currentPeopleList = peopleListMutableLiveData.getValue().getData();
                        currentPeopleList.add(null);
                        peopleListResource.setData(currentPeopleList);
                        peopleListResource.setStatusMessage(peopleListMutableLiveData.getValue().getStatusMessage());
                        peopleListResource.setTotalResult(peopleListMutableLiveData.getValue().getTotalResult());
                        peopleListResource.setStatusCode(peopleListMutableLiveData.getValue().getStatusCode());

                        peopleListResource.setLoading(true);
                        peopleListMutableLiveData.postValue(peopleListResource);

                        int page= searchPeopleResultViewModel.getPeoplePage() + 1;
                        searchPeopleResultViewModel.setPeoplePage(page);

                        searchPeopleResultViewModel.getMorePeopleSearch(getString(R.string.API_LANGUAGE), checkAdult, query,region);
                    }
                }



            }
        });


        final Observer<Resource<List<People>>> observer_people_search=new Observer<Resource<List<People>>>() {
            @Override
            public void onChanged(Resource<List<People>> people) {
                Log.d(TAG, "lista tmdb Search"+people);

                peopleListAdapter.setData(people.getData());

                if(!people.isLoading()){
                    searchPeopleResultViewModel.setPeopleIsLoading(false);
                    searchPeopleResultViewModel.setPeopleCurrentResults(people.getData().size());
                }
            }
        };

        searchPeopleResultViewModel.getPeopleSearch(getString(R.string.API_LANGUAGE), checkAdult, query ,region).observe(getViewLifecycleOwner(), observer_people_search);
    }


    private List<People> getPeopleList(String language, boolean checkAdult, String query, String region){
        Resource<List<People>> peopleListResult= searchPeopleResultViewModel.getPeopleSearch(language, checkAdult,query,region).getValue();
        if(peopleListResult != null){
            return peopleListResult.getData();
        }
        return null;
    }
}
