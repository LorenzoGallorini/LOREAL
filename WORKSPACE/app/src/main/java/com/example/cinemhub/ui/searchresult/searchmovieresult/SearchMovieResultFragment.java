package com.example.cinemhub.ui.searchresult.searchmovieresult;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentSearchMovieResultBinding;
import com.example.cinemhub.databinding.FragmentSearchresultBinding;
import com.example.cinemhub.ui.searchresult.SearchresultViewModel;

public class SearchMovieResultFragment extends Fragment {

    private SearchMovieResultViewModel mViewModel;
    private FragmentSearchMovieResultBinding binding;

    public static SearchMovieResultFragment newInstance() {
        return new SearchMovieResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSearchMovieResultBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.action_search));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_settings);
        mViewModel=new ViewModelProvider(getActivity()).get(SearchMovieResultViewModel.class);




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        binding.RecyclerViewSearch.setLayoutManager(layoutManager);
    }


}
