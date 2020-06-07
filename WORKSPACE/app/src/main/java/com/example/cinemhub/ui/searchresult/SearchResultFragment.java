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
import com.example.cinemhub.ui.searchresult.searchmovieresult.SearchMovieResultFragment;
import com.example.cinemhub.ui.searchresult.searchpeopleresult.SearchPeopleResultFragment;
import com.google.android.material.tabs.TabLayout;

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


        viewPageAdapter=new ViewPageAdapter(getChildFragmentManager());

        viewPageAdapter.addFragment(new SearchMovieResultFragment(year,query,categorie), "Movie");
        viewPageAdapter.addFragment(new SearchPeopleResultFragment(query), "People");


        binding.viewPager.setAdapter(viewPageAdapter);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        binding.tabLayout.setTabMode(TabLayout.MODE_FIXED);








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
                viewPageAdapter.clear();
                binding.viewPager.setAdapter(null);
                binding.tabLayout.removeAllTabs();


                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }





}
