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

import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;

public class ShakeFragment extends Fragment {

    private ShakeViewModel mViewModel;
    private final String TAG = "ShakeFragment";
    private FragmentShakeBinding binding;

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
                .background(true)
                .interval(1000)
                .shakeCount(1)
                .sensibility(1.3f);

        if (((MainActivity) getActivity()).shakeDetector != null){
            ((MainActivity) getActivity()).shakeDetector.start(getContext());
        }else{
            ((MainActivity) getActivity()).shakeDetector = new ShakeDetector(options).start(getContext(), new ShakeCallback() {
                @Override
                public void onShake() {
                    Log.d(TAG, "onShake");
                    //((MainActivity) getActivity()).shakeDetector.stopShakeDetector(getActivity());
                    Navigation.findNavController(getView()).navigate(ShakeFragmentDirections.actionNavigationShakeToNavigationShake2());
                }

            });
        }


        return view;
    }

    @Override
    public void onStop() {
        ((MainActivity) getActivity()).shakeDetector.stopShakeDetector(getContext());
        ((MainActivity) getActivity()).shakeDetector.destroy(getContext());
        ((MainActivity) getActivity()).shakeDetector=null;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
                Navigation.findNavController(getView()).navigate(ShakeFragmentDirections.actionNavigationShakeToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(ShakeFragmentDirections.actionNavigationShakeToNavigationSettings());
                return true;
            default:return false;
        }
    }
}
