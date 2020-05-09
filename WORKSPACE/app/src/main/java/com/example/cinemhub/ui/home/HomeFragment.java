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

public class HomeFragment extends Fragment {

    private Button NowPlayingButton;
    private Button RatedButton;
    private Button ComingSoonButton;

  //  private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private final String TAG = "HomeFragment";
    //private OnFragmentInteractionListener mListener;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      //  homeViewModel =
        //        ViewModelProviders.of(this).get(HomeViewModel.class);
      //  View root = inflater.inflate(R.layout.fragment_home, container, false);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

       // NowPlayingButton=root.findViewById(R.id.show_all_now_plaing);
      //  RatedButton=root.findViewById(R.id.show_all_rated);
        //ComingSoonButton=root.findViewById(R.id.show_all_coming_soon);
        //Button Show1 = root.findViewById(R.id.show_all_now_plaing);
        //Button Show2 = root.findViewById(R.id.show_all_rated);
        //Button Show3 = root.findViewById(R.id.show_all_coming_soon);

        binding.showAllNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Button1");
                Fragment newFragment = new NowPlayingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.showAllRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Button1");
                Fragment newFragment = new NowPlayingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        binding.showAllComingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Button1");
                Fragment newFragment = new ComingSoonFragment();
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
