package com.example.cinemhub.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentHomeBinding;
import com.example.cinemhub.ui.comingsoon.ComingSoonFragment;
import com.example.cinemhub.ui.nowplaying.NowPlayingFragment;
import com.example.cinemhub.ui.peoplecard.PeopleCardFragment;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private final String TAG = "HomeFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.textViewShowAllNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AllNowPlayingClick");
                Fragment newFragment = new NowPlayingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.textView2ShowAllTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AllRatedClick");
                Fragment newFragment = new NowPlayingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        
        binding.textViewShowAllComingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: AllComingSoonClick");
                Fragment newFragment = new ComingSoonFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: image4");
                Fragment newFragment = new PeopleCardFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
