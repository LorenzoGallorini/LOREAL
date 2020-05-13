package com.example.cinemhub.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.databinding.FragmentHomeBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.ui.comingsoon.ComingSoonFragment;
import com.example.cinemhub.ui.nowplaying.NowPlayingFragment;
import com.example.cinemhub.ui.peoplecard.PeopleCardFragment;
import com.example.cinemhub.ui.settings.SettingsFragment;

import java.io.InputStream;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private final String TAG = "HomeFragment";
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);

        homeViewModel=new ViewModelProvider(this).get(HomeViewModel.class);
        final Observer<List<Movie>> observer_now_playing=new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                Log.d(TAG, "lista tmdb nowplaying"+movies);
                for(int i=0;i<movies.size();i++){
                    Log.d(TAG, "film numero "+i+" "+movies.get(i).toString());

                }
                InputStream is = null;
                new DownloadImageTask((ImageView) binding.imageButton4)
                        .execute("https://image.tmdb.org/t/p/w500"+movies.get(0).getPoster_path());




            }
        };

        homeViewModel.getMovieNowPlaying("it-IT", 1).observe(getViewLifecycleOwner(), observer_now_playing);//TODO settare delle variabili globali per la lingua e per la pagina

        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_home));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_home);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.settings){
            Log.d(TAG, "onClick: SettingsClick");

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment,new SettingsFragment(), null);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }
        return false;
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
