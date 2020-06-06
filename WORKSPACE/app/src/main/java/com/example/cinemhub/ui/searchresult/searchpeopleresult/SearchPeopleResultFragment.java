package com.example.cinemhub.ui.searchresult.searchpeopleresult;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemhub.R;

public class SearchPeopleResultFragment extends Fragment {

    private SearchPeopleResultViewModel mViewModel;

    public static SearchPeopleResultFragment newInstance() {
        return new SearchPeopleResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_people_result, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchPeopleResultViewModel.class);
        // TODO: Use the ViewModel
    }

}
