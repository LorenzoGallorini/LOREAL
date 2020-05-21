package com.example.cinemhub.ui.moviecard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.CreditsListHorizontalAdapter;
import com.example.cinemhub.databinding.FragmentMovieCardBinding;
import com.example.cinemhub.models.GetVideosApiTmdbResponse;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.MovieCreditsApiTmdbResponse;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.VideoApiTmdbResponse;
import com.example.cinemhub.ui.peoplecard.PeopleCardFragment;
import com.example.cinemhub.ui.settings.SettingsFragment;
import com.example.cinemhub.utils.Constants;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.Set;

public class MovieCardFragment extends Fragment {
    private YouTubePlayer mYoutubePlayer;
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
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_movie_card);




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        binding.CreditsRecyclerView.setLayoutManager(layoutManager);
        binding.CreditsCrewRecyclerView.setLayoutManager(layoutManager2);


        mViewModel=new ViewModelProvider(this).get(MovieCardViewModel.class);

        final Observer<Movie> observer_details=new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                Log.d(TAG, "movie tmdb details"+movie);

                if(movie.getPoster_path()!=null&&!movie.getPoster_path().equals("")){
                    Picasso.get().load(Constants.IMAGE_BASE_URL + movie.getPoster_path()).into(binding.filmImage);
                }else{
                    binding.filmImage.setImageResource(R.drawable.no_image_avaiable);
                }

                binding.filmTextTitle.setText(movie.getTitle());
                binding.descriptionValue.setText(movie.getDescription());
                binding.RatingValue.setText(Double.toString(movie.getVote_average()));



                if(movie.isAdult())
                    binding.AdultValue.setText((getString(R.string.Adult)));
                else
                    binding.AdultValue.setVisibility(View.INVISIBLE);
                binding.RuntimeValue.setText(Integer.toString(movie.getRuntime())+"'");
                binding.ReleaseDateValue.setText(movie.getReleaseDateFORMATTED());
                binding.RevenueValue.setText(movie.getRevenueFORMATTED()+" €");

                binding.GenresValue.setText(movie.getGenresTostring());


                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                        Constants.FAVOURITE_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
                Set<String> preferiti;
                try {
                    preferiti=sharedPreferences.getStringSet(Constants.FAVOURITE_SHARED_PREF_NAME,null);
                    if (preferiti == null)
                        preferiti = new HashSet<String>();
                }
                catch(Exception e)
                {
                    preferiti = new HashSet<String>();
                }
                if (preferiti.contains(Integer.toString(movie.getId()))) {
                    binding.MovieCardFavouriteButton.setImageResource(R.drawable.startminum_yellow);
                }
                else
                {
                    binding.MovieCardFavouriteButton.setImageResource(R.drawable.startplus_yellow);
                }
                binding.MovieCardFavouriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Set<String> preferiti;
                        Toast toast;
                        try {
                            preferiti=sharedPreferences.getStringSet(Constants.FAVOURITE_SHARED_PREF_NAME,null);
                            if (preferiti == null)
                                preferiti = new HashSet<String>();
                        }
                        catch(Exception e)
                        {
                            preferiti = new HashSet<String>();
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        if (preferiti.contains(Integer.toString(movie.getId()))) {
                            Set<String> in = new HashSet<String>(preferiti);
                            in.remove(Integer.toString(movie.getId()));
                            editor.remove(Constants.FAVOURITE_SHARED_PREF_NAME);
                            editor.putStringSet(Constants.FAVOURITE_SHARED_PREF_NAME, in);
                            toast = Toast.makeText(getContext(), "Rimosso " + movie.getTitle() + " dai tuoi preferiti", Toast.LENGTH_SHORT);

                            binding.MovieCardFavouriteButton.setImageResource(R.drawable.startplus_yellow);
                        }
                        else {

                            Set<String> in = new HashSet<String>(preferiti);
                            in.add(Integer.toString(movie.getId()));
                            editor.putStringSet(Constants.FAVOURITE_SHARED_PREF_NAME, in);


                            toast = Toast.makeText(getContext(), "Aggiunto " + movie.getTitle() + " ai tuoi preferiti", Toast.LENGTH_SHORT);

                            binding.MovieCardFavouriteButton.setImageResource(R.drawable.startminum_yellow);
                        }
                        editor.commit();
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        binding.MovieCardFavouriteButton.setClickable(false);


                        new CountDownTimer(2000, 2000) { //Set Timer for 5 seconds
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                binding.MovieCardFavouriteButton.setClickable(true);
                            }
                        }.start();
                    }

                });

            }
        };


        final Observer<MovieCreditsApiTmdbResponse> observer_credits = new Observer<MovieCreditsApiTmdbResponse>() {
            @Override
            public void onChanged(MovieCreditsApiTmdbResponse movieCreditsApiTmdbResponse) {

                CreditsListHorizontalAdapter creditsListHorizontalAdapter = new CreditsListHorizontalAdapter(getActivity(),
                        People.toList(movieCreditsApiTmdbResponse.getCast()), new CreditsListHorizontalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(People people) {
                        Navigation.findNavController(view).navigate(MovieCardFragmentDirections.actionNavigationMovieCardToNavigationPeopleCard(people.getId()));

                    }
                });
                CreditsListHorizontalAdapter creditsCrewListHorizontalAdapter = new CreditsListHorizontalAdapter(getActivity(),
                        People.toList(movieCreditsApiTmdbResponse.getCrew()), new CreditsListHorizontalAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(People people) {
                        Navigation.findNavController(view).navigate(MovieCardFragmentDirections.actionNavigationMovieCardToNavigationPeopleCard(people.getId()));
                    }
                });
                binding.CreditsCrewRecyclerView.setAdapter(creditsCrewListHorizontalAdapter);
                binding.CreditsRecyclerView.setAdapter(creditsListHorizontalAdapter);
            }
        };


        final Observer<GetVideosApiTmdbResponse> observer_videos = new Observer<GetVideosApiTmdbResponse>() {
            @Override
            public void onChanged(GetVideosApiTmdbResponse getVideosApiTmdbResponse) {
                VideoApiTmdbResponse[] results = getVideosApiTmdbResponse.getResults();
                int i = 0;
                boolean found= false;
                VideoApiTmdbResponse result=null;
                while (i<results.length && !found)
                {
                    if(results[i].getSite().equals(Constants.PLATFORM_VIDEO))
                    {
                        result=results[i];
                        if(results[i].getType().equals(Constants.TYPE_VIDEO))
                            found=true;
                    }
                    i++;
                }
                if(result != null)
                {
                    YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

                    setRetainInstance(true);
                    VideoApiTmdbResponse finalResult = result;
                    youTubePlayerFragment.initialize(Constants.API_KEY_YOUTUBE, new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                            if (!b) {
                                mYoutubePlayer = youTubePlayer;
                                mYoutubePlayer.setShowFullscreenButton(true);
                                mYoutubePlayer.cueVideo(finalResult.getKey());
                                mYoutubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);
                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    });
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.replace(R.id.fl_youtube, (Fragment) youTubePlayerFragment);
                    transaction.commit();
                }
            }
        };

        //Bundle bundle = getArguments();
        //int value = bundle.getInt("MovieId");

        int value=MovieCardFragmentArgs.fromBundle(getArguments()).getMovieId();


        mViewModel.getMovieDetails(value,getString(R.string.API_LANGUAGE)).observe(getViewLifecycleOwner(), observer_details);
        mViewModel.getMovieCredits(value).observe(getViewLifecycleOwner(), observer_credits);
        mViewModel.getVideos(value,getString(R.string.API_LANGUAGE)).observe(getViewLifecycleOwner(), observer_videos);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MovieCardViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.search){
            Log.d(TAG, "onClick: SearchClick");
            Navigation.findNavController(getView()).navigate(MovieCardFragmentDirections.actionNavigationMovieCardToNavigationSearch());
            return true;
        }else if(id==R.id.settings){
            Log.d(TAG, "onClick: SettingsClick");
            Navigation.findNavController(getView()).navigate(MovieCardFragmentDirections.actionNavigationMovieCardToNavigationSettings());
            return true;
        }
        else if(id==android.R.id.home) {

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
