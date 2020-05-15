package com.example.cinemhub.ui.toprated;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentTopRatedBinding;
import com.example.cinemhub.ui.search.SearchFragment;
import com.example.cinemhub.ui.settings.SettingsFragment;

public class TopRatedFragment extends Fragment {

    private TopRatedViewModel mViewModel;
    private final String TAG = "TopRatedFragment";
    private FragmentTopRatedBinding binding;

    public static TopRatedFragment newInstance() {
        return new TopRatedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTopRatedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_top_rated));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(TopRatedViewModel.class);
        // TODO: Use the ViewModel
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
