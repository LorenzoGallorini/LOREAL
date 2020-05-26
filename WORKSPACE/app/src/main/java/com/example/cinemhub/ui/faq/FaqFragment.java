package com.example.cinemhub.ui.faq;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentFaqBinding;

public class FaqFragment extends Fragment {

    private FaqViewModel mViewModel;
    private FragmentFaqBinding binding;
    private String TAG="FaqFragment";

    public static FaqFragment newInstance() {
        return new FaqFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFaqBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.faq_text));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_settings);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FaqViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.search).setEnabled(false);
        menu.findItem(R.id.search).setVisible(false);
        menu.findItem(R.id.settings).setEnabled(false);
        menu.findItem(R.id.settings).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return false;
    }

}
