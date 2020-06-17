package com.example.cinemhub.ui.favorite;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.MovieListAdapter;
import com.example.cinemhub.databinding.FragmentFavoriteBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;
    private final String TAG = "FavoriteFragment";
    private FragmentFavoriteBinding binding;


    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteViewModel =new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) requireActivity()).setActionBarTitle(getString(R.string.title_favorite));
        ((MainActivity) requireActivity()).menuColorSettings(R.id.navigation_favorite);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final int favoriteRVSpanCount = 3;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), favoriteRVSpanCount);
        binding.FavoriteRecyclerView.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(
                Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        Set<String> preferiti;
        preferiti=sharedPreferences.getStringSet(Constants.FAVORITE_SHARED_PREF_NAME+getString(R.string.API_LANGUAGE),null);
        if (preferiti==null || preferiti.isEmpty()){
            binding.emptyTextView.setVisibility(View.VISIBLE);
            binding.imageViewStar.setVisibility(View.VISIBLE);
            binding.emptyTextView2.setVisibility(View.VISIBLE);
        }else {
            binding.emptyTextView.setVisibility(View.INVISIBLE);
            binding.imageViewStar.setVisibility(View.INVISIBLE);
            binding.emptyTextView2.setVisibility(View.INVISIBLE);

            List<Movie> mList=new ArrayList<>();
            Object[] favoriteObjArray=preferiti.toArray();
            for (Object o : favoriteObjArray) {
                String[] app = o.toString().split(Constants.SEPARATOR);
                if (app.length == 3) {
                    mList.add(new Movie(app[0], app[1], app[2]));
                }
            }

            MovieListAdapter movieListAdapter = new MovieListAdapter(getActivity(), mList, movie -> Navigation.findNavController(requireView()).navigate(FavoriteFragmentDirections.actionNavigationFavoriteToNavigationMovieCard(movie.getId())));
            binding.FavoriteRecyclerView.setAdapter(movieListAdapter);
        }



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(requireView()).navigate(FavoriteFragmentDirections.actionNavigationFavoriteToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(requireView()).navigate(FavoriteFragmentDirections.actionNavigationFavoriteToNavigationSettings());
                return true;
            default:return false;
        }
    }



}
