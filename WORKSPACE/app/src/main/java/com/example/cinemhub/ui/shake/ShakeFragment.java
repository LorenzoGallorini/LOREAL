package com.example.cinemhub.ui.shake;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentShakeBinding;

import java.util.Objects;

import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;

public class ShakeFragment extends Fragment {

    private ShakeViewModel mViewModel;
    private final String TAG = "ShakeFragment";
    private FragmentShakeBinding binding;
    private ShakeDetector shakeDetector;
    public static ShakeFragment newInstance() {
        return new ShakeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShakeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_shake));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_shake);
        ShakeOptions options = new ShakeOptions()
                .background(false)
                .interval(1000)
                .shakeCount(2)
                .sensibility(1.3f);


            this.shakeDetector = new ShakeDetector(options).start(getContext(), new ShakeCallback() {
                @Override
                public void onShake() {
                    Log.d(TAG, "onShake");
                    //shakeDetector.stopShakeDetector(getActivity());
                    Navigation.findNavController(getView()).navigate(ShakeFragmentDirections.actionNavigationShakeToNavigationShake2());
                }

            });


        return view;
    }

    @Override
    public void onStop() {
        shakeDetector.stopShakeDetector(getContext());
        super.onStop();
    }

    @Override
    public void onDestroy() {
        shakeDetector.destroy(getContext());
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ShakeViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.search){
            Log.d(TAG, "onClick: SearchClick");
            Navigation.findNavController(getView()).navigate(ShakeFragmentDirections.actionNavigationShakeToNavigationSearch());
            return true;
        }else if(id==R.id.settings){
            Log.d(TAG, "onClick: SettingsClick");
            Navigation.findNavController(getView()).navigate(ShakeFragmentDirections.actionNavigationShakeToNavigationSettings());
            return true;
        }
        return false;
    }



}
