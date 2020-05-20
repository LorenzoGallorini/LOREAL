package com.example.cinemhub.ui.comingsoon;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.MovieListVerticalAdapter;
import com.example.cinemhub.databinding.FragmentComingSoonBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.search.SearchFragment;
import com.example.cinemhub.ui.settings.SettingsFragment;

import java.util.List;

public class ComingSoonFragment extends Fragment {

    private ComingSoonViewModel mViewModel;
    private final String TAG = "ComingSoonFragment";
    private FragmentComingSoonBinding binding;

    public static ComingSoonFragment newInstance() {
        return new ComingSoonFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(getActivity()).get(ComingSoonViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentComingSoonBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_coming_soon));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        binding.ComingSoonRecyclerView.setLayoutManager(layoutManager);


        final Observer<List<Movie>> observer_coming_soon=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);
                MovieListVerticalAdapter movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(),movies);
                binding.ComingSoonRecyclerView.setAdapter(movieListVerticalAdapter);

            }
        };
        mViewModel.getMovieComingSoon(getString(R.string.API_LANGUAGE), 1).observe(getViewLifecycleOwner(), observer_coming_soon);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ComingSoonViewModel.class);
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_coming_soon);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.search){
            Log.d(TAG, "onClick: SearchClick");
            fragmentTransactionMethod(new SearchFragment());
            return true;
        }else if(id==R.id.settings){
            Log.d(TAG, "onClick: SettingsClick");
            fragmentTransactionMethod(new SettingsFragment());
            return true;
        }
        return false;
    }

    private void fragmentTransactionMethod (Fragment newFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
