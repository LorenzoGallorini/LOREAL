package com.example.cinemhub.ui.toprated;

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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinemhub.MainActivity;
import com.example.cinemhub.R;
import com.example.cinemhub.adapters.MovieListVerticalAdapter;
import com.example.cinemhub.databinding.FragmentTopRatedBinding;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.utils.Constants;

import java.util.List;

/**
 * classe TopRatedFragment
 * classe utilizzata per visualizzare i Film che sono attualmente nelle sale ed è raggiungibile cliccando
 * nel relativo pulsante "vedi tutti" presente nell'HomeFragment
 */
public class TopRatedFragment extends Fragment {

    private TopRatedViewModel mViewModel;/**< attributo per la gestione del NowPlayingFragment*/
    private final String TAG = "TopRatedFragment";
    private FragmentTopRatedBinding binding;/**< attributo per poter gestire gli oggetti all'interno del Fragment*/
    private MovieListVerticalAdapter movieListVerticalAdapter;/**< attributo per contenere l'Adapter per la RecyclerView*/
    int topRatedRVSpanCount=3;/**< attributo per il numero massimo delle colonne della RecyclerView*/

    private int totalItemCount;/**< attributo per il numero totale di oggetti nella RecyclerView*/
    private int lastVisibleItem;/**< attributo per l'ultimo oggetto visibile nella RecyclerView*/
    private int threshold=1;/**< attributo per tenere conto dell'elemento aggiuntivo grafico della RecyclerView*/


    /**
     * costruttore di TopRatedFragment
     * @return Fragment di tipo TopRatedFragment
     */
    public static TopRatedFragment newInstance() {
        return new TopRatedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //istanziamo l'oggetto TopRatedViewModel
        mViewModel=new ViewModelProvider(getActivity()).get(TopRatedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //andiamo a prendere il FragmentTopRatedBinding mettendolo in binding per poter utilizzare gli oggetti presenti nel Fragment
        binding = FragmentTopRatedBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //settiamo il titolo del TopRatedFragment nell'ActionBar della MainActivity ed il colore
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.title_top_rated));
        ((MainActivity) getActivity()).menuColorSettings(R.id.navigation_top_rated);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //creiamo il LayoutManager per gestire la RecyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),topRatedRVSpanCount);
        binding.TopRatedRecyclerView.setLayoutManager(layoutManager);

        //recuperiamo i valori checkAdult e region dalla SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.CINEM_HUB_SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE);
        boolean checkAdult = sharedPreferences.getBoolean(Constants.ADULT_SHARED_PREF_NAME, false);
        String region = sharedPreferences.getString(Constants.REGION_SHARED_PREF_NAME, null);

        //creiamo l'Adapter per gestire la RecyclerView
        movieListVerticalAdapter = new MovieListVerticalAdapter(getActivity(), getMovieList(getString(R.string.API_LANGUAGE), checkAdult, region), new MovieListVerticalAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Movie movie) {
                Navigation.findNavController(getView()).navigate(TopRatedFragmentDirections.actionNavigationTopRatedToNavigationMovieCard(movie.getId()));
            }
        });
        binding.TopRatedRecyclerView.setAdapter(movieListVerticalAdapter);

        //andiamo ad intercettare l'evento di scroll della RecyclerView
        binding.TopRatedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //aggiorniamo i valori del totalItemCount e del lastVisibleItem
                totalItemCount = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                //controlliamo se dobbiamo aggiungere elementi alla RecyclerView
                if((totalItemCount <= (lastVisibleItem + threshold) && dy>0 && !mViewModel.isLoading()) &&
                                mViewModel.getMovieLiveData().getValue() != null &&
                                mViewModel.getCurrentResults()!=mViewModel.getMovieLiveData().getValue().getTotalResult()
                ){
                    //creiamo un oggetto di tipo Resource per aggiungere nuovi elementi alla RecyclerView
                    Resource<List<Movie>> movieListResource=new Resource<>();
                    MutableLiveData<Resource<List<Movie>>> movieListMutableLiveData = mViewModel.getMovieLiveData();

                    //controlliamo che la chiamata sia andata a buon fine e che ci siano altri dati da visualizzare
                    if(movieListMutableLiveData!=null && movieListMutableLiveData.getValue() != null){
                        //aggiorniamo i valori della Resource
                        mViewModel.setLoading(true);
                        List<Movie> currentMovieList = movieListMutableLiveData.getValue().getData();
                        currentMovieList.add(null);
                        movieListResource.setData(currentMovieList);
                        movieListResource.setStatusMessage(movieListMutableLiveData.getValue().getStatusMessage());
                        movieListResource.setTotalResult(movieListMutableLiveData.getValue().getTotalResult());
                        movieListResource.setStatusCode(movieListMutableLiveData.getValue().getStatusCode());
                        movieListResource.setLoading(true);
                        movieListMutableLiveData.postValue(movieListResource);

                        //incrementiamo il valore di page per visualizzare nuove pagine nei risultati
                        int page=mViewModel.getPage() + 1;
                        mViewModel.setPage(page);

                        //chiediamo altri risultati tramite il ViewModel
                        mViewModel.getMoreMovieTopRated(getString(R.string.API_LANGUAGE), checkAdult, region);
                    }
                }
            }
        });

        //creiamo l'observer per cattura la modifica della lista dei film
        final Observer<Resource<List<Movie>>> observer_top_rated=new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(Resource<List<Movie>> movies) {
                Log.d(TAG, "lista tmdb comingsoon"+movies);

                //inseriamo nell'Adapter i dati
                movieListVerticalAdapter.setData(movies.getData());

                //controlliamo che movies non stia ancora caricando i risultati
                if(!movies.isLoading()){
                    mViewModel.setLoading(false);
                    mViewModel.setCurrentResults(movies.getData().size());
                }
            }
        };
        //se la region è diversa da null effuttiamo una chiamata al DB
        if(region!=null){
            mViewModel.getMovieTopRated(getString(R.string.API_LANGUAGE), checkAdult, region).observe(getViewLifecycleOwner(), observer_top_rated);
        } else {
            Log.d(TAG, "ERROR:region cannot be null");
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //controlliamo tramite l'ID quale icona è stata selezionata e indirizziamo l'utente nel Fragment corretto
        switch (item.getItemId()){
            case R.id.search:
                Log.d(TAG, "onClick: SearchClick");
                Navigation.findNavController(getView()).navigate(TopRatedFragmentDirections.actionNavigationTopRatedToNavigationSearch());
                return true;
            case R.id.settings:
                Log.d(TAG, "onClick: SettingsClick");
                Navigation.findNavController(getView()).navigate(TopRatedFragmentDirections.actionNavigationTopRatedToNavigationSettings());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:return false;
        }
    }

    /**
     * metodo per la richiesta della List di film
     * @param language stringa che contiene la lingua del dispositivo
     * @param checkAdult booleano per il Parental Control
     * @param region stringa che contiene il paese selezionato
     * @return List di Movie contenente i risultati della chiamata
     */
    private List<Movie> getMovieList(String language, boolean checkAdult, String region){
        Resource<List<Movie>> movieListResult=mViewModel.getMovieTopRated(language, checkAdult, region).getValue();
        if(movieListResult != null){
            return movieListResult.getData();
        }
        return null;
    }
}
