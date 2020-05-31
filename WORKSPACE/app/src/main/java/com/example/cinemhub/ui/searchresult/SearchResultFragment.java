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

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentSearchresultBinding;
import com.google.android.material.tabs.TabLayout;

public class SearchResultFragment extends Fragment {

    private SearchresultViewModel mViewModel;

    private String TAG="SearchResultFragment";
    private FragmentSearchresultBinding binding;


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
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                   if( tab.view.getTab().getText().equals(getString(R.string.movie)) )
                       binding.textView.setText("movie" );
                   else
                       binding.textView.setText("else");

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
                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }

}
