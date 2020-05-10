package com.example.cinemhub.ui.moviecard;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentMovieCardBinding;

public class MovieCardFragment extends Fragment {

    private MovieCardViewModel mViewModel;
    private FragmentMovieCardBinding binding;
    private final String TAG="MovieCard";

    public static MovieCardFragment newInstance() {
        return new MovieCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMovieCardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_movie_card));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieCardViewModel.class);
        // TODO: Use the ViewModel
    }

}
