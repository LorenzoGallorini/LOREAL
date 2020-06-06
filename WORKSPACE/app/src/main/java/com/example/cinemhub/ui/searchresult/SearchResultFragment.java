package com.example.cinemhub.ui.searchresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.MovieListVerticalAdapter;
import com.example.cinemhub.adapters.PeopleListVerticalAdapter;
import com.example.cinemhub.adapters.ViewPageAdapter;
import com.example.cinemhub.databinding.FragmentSearchresultBinding;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.ui.searchresult.searchmovieresult.SearchMovieResultFragment;
import com.example.cinemhub.ui.searchresult.searchpeopleresult.SearchPeopleResultFragment;

import java.util.List;

public class SearchResultFragment extends Fragment {

    private SearchresultViewModel mViewModel;
    private String TAG="SearchResultFragment";
    private FragmentSearchresultBinding binding;
    private MovieListVerticalAdapter movieListVerticalAdapter;
    private PeopleListVerticalAdapter peopleListVerticalAdapter;

    private ViewPageAdapter viewPageAdapter;

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





        viewPageAdapter=new ViewPageAdapter(getFragmentManager());

        viewPageAdapter.addFragment(new SearchMovieResultFragment(year,query), "Movie");
        viewPageAdapter.addFragment(new SearchPeopleResultFragment(), "People");
        
        binding.viewPager.setAdapter(viewPageAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);


/*

        GridLayoutManager layoutManagerPeople = new GridLayoutManager(getActivity(),3);

        binding.RecyclerViewSearchPeople.setLayoutManager(layoutManagerPeople);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);



        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult=sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        String region=sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);





        peopleListVerticalAdapter = new PeopleListVerticalAdapter(getActivity(), getPeopleList(getString(R.string.API_LANGUAGE), checkAdult, query,region), new PeopleListVerticalAdapter.OnItemClickListener(){

            @Override
            public void OnItemClick(People people) {
                Navigation.findNavController(getView()).navigate(SearchResultFragmentDirections.actionNavigationSearchResultToNavigationPeopleCard(people.getId()));
            }
        });

        binding.RecyclerViewSearchPeople.setAdapter(peopleListVerticalAdapter);









        binding.RecyclerViewSearchPeople.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                peopleTotalItemCount = layoutManagerPeople.getItemCount();
                peopleLastVisibleItem = layoutManagerPeople.findLastVisibleItemPosition();
                peopleVisibleItemCount = layoutManagerPeople.getChildCount();

                if((peopleTotalItemCount <= (peopleLastVisibleItem + peopleThreshold) && dy>0 && !mViewModel.isPeopleIsLoading()) &&
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
        };*/
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



    private List<People> getPeopleList(String language, boolean checkAdult,String query, String region){
        Resource<List<People>> peopleListResult=mViewModel.getPeopleSearch(language, checkAdult,query,region).getValue();
        if(peopleListResult != null){
            return peopleListResult.getData();
        }
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
