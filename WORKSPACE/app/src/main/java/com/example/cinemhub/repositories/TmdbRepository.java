package com.example.cinemhub.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.cinemhub.models.ComingSoonApiTmdbResponse;
import com.example.cinemhub.models.GenreApiTmdbResponse;
import com.example.cinemhub.models.GetVideosApiTmdbResponse;
import com.example.cinemhub.models.Movie;
import com.example.cinemhub.models.MovieApiTmdbResponse;
import com.example.cinemhub.models.MovieCreditsApiTmdbResponse;
import com.example.cinemhub.models.NowPlayingApiTmdbResponse;
import com.example.cinemhub.models.People;
import com.example.cinemhub.models.PeopleApiTmdbResponse;
import com.example.cinemhub.models.PeopleCreditsApiTmdbResponse;
import com.example.cinemhub.models.RecommendationsApiTmdbResponse;
import com.example.cinemhub.models.Resource;
import com.example.cinemhub.models.SearchMovieApiTmdbResponse;
import com.example.cinemhub.models.SearchPeopleApiTmdbResponse;
import com.example.cinemhub.models.TopRatedApiTmdbResponse;
import com.example.cinemhub.services.TmdbServices;
import com.example.cinemhub.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Repository layer per ottenere film e personaggi del cinema
 * In questa classe andiamo a gestire i risultati delle chiamate all'API TMDB
 * Questo è uno strato di intermezzo tra il Service e il ViewModel
 */
public class TmdbRepository {
    private static TmdbRepository instance;
    private TmdbServices tmdbServices;
    private final String TAG="TmdbRepository";

    /**
     * Costruttore privato per permettere di istanziare al di fuori della classe.
     */
    private TmdbRepository(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.API_TMDB_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        tmdbServices = retrofit.create(TmdbServices.class);
    }

    /**
     * Metodo per ottenere l'istanza
     * @return l'istanza dell'oggetto
     */
    public static synchronized TmdbRepository getInstance(){
        if(instance==null){
            instance=new TmdbRepository();
        }
        return instance;
    }

    /**
     * Metodo per ottenere la lista di film attualmente al cinema
     * @param movieNowPlaying l'oggetto LiveData associato alla Resource che contiene la lista di Movie
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param checkAdult valore booleano utilizzato per il Parental Control
     * @param region stringa contenente il paese scelto
     */
    public void getNowPlaying(MutableLiveData<Resource<List<Movie>>> movieNowPlaying, String language, int page, boolean checkAdult, String region){

        Call<NowPlayingApiTmdbResponse> call = tmdbServices.getNowPlaying(language , page, Constants.API_TMDB_KEY, region);
        call.enqueue(new Callback<NowPlayingApiTmdbResponse>() {
            @Override
            public void onResponse(Call<NowPlayingApiTmdbResponse> call, Response<NowPlayingApiTmdbResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Resource<List<Movie>> resource = new Resource<>();
                    List<Movie> results = new ArrayList<Movie>();
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        if(!checkAdult || !response.body().getResults().get(i).isAdult()){
                            results.add(new Movie(response.body().getResults().get(i)));
                        }                    }
                    if (movieNowPlaying.getValue() != null && movieNowPlaying.getValue().getData() != null) {
                        List<Movie> currentMovieList = movieNowPlaying.getValue().getData();
                        currentMovieList.remove(currentMovieList.size() - 1);
                        currentMovieList.addAll(results);
                        resource.setData(currentMovieList);
                    } else {
                        resource.setData(results);
                    }
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    resource.setLoading(false);
                    movieNowPlaying.postValue(resource);
                }else if (response.errorBody()!=null){
                    Log.d(TAG, "ERROR: getNowPlaying=null");
                    Resource<List<Movie>> resource=new Resource<>();
                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message()+" - "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    movieNowPlaying.postValue(resource);
                }
            }
            @Override
            public void onFailure(Call<NowPlayingApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * Metodo per ottenere la lista di film considerati i migliori
     * @param movieTopRated l'oggetto LiveData associato alla Resource che contiene la lista di Movie
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param checkAdult valore booleano utilizzato per il Parental Control
     * @param region stringa contenente il paese scelto
     */
    public void getTopRated(MutableLiveData<Resource<List<Movie>>> movieTopRated, String language, int page, boolean checkAdult, String region){
        Call<TopRatedApiTmdbResponse> call= tmdbServices.getTopRated(language, page, Constants.API_TMDB_KEY, region);
        call.enqueue(new Callback<TopRatedApiTmdbResponse>() {
            @Override
            public void onResponse(Call<TopRatedApiTmdbResponse> call, Response<TopRatedApiTmdbResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Resource<List<Movie>> resource=new Resource<>();
                    List<Movie> results = new ArrayList<Movie>();
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        if(!checkAdult || !response.body().getResults().get(i).isAdult()){
                            results.add(new Movie(response.body().getResults().get(i)));
                        }
                    }
                    if (movieTopRated.getValue() != null && movieTopRated.getValue().getData() != null) {
                        List<Movie> currentMovieList = movieTopRated.getValue().getData();
                        currentMovieList.remove(currentMovieList.size() - 1);
                        currentMovieList.addAll(results);
                        resource.setData(currentMovieList);
                    } else {
                        resource.setData(results);
                    }
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    resource.setLoading(false);
                    movieTopRated.postValue(resource);
                } else if (response.errorBody()!=null){
                    Log.d(TAG, "ERROR: getTopRated=null");
                    Resource<List<Movie>> resource=new Resource<>();
                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message()+" - "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    movieTopRated.postValue(resource);
                }
            }

            @Override
            public void onFailure(Call<TopRatedApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * Metodo per ottenere la lista di film che dovranno uscire al cinema
     * @param movieComingSoon l'oggetto LiveData associato alla Resource che contiene la lista di Movie
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param checkAdult valore booleano utilizzato per il Parental Control
     * @param region stringa contenente il paese scelto
     */
    public void getComingSoon(MutableLiveData<Resource<List<Movie>>> movieComingSoon, String language, int page, boolean checkAdult, String region){
        Call<ComingSoonApiTmdbResponse> call= tmdbServices.getComingSoon(language, page, Constants.API_TMDB_KEY, region);
        call.enqueue(new Callback<ComingSoonApiTmdbResponse>() {
            @Override
            public void onResponse(Call<ComingSoonApiTmdbResponse> call, Response<ComingSoonApiTmdbResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Resource<List<Movie>> resource = new Resource<>();
                    List<Movie> results = new ArrayList<Movie>();
                    for (int i = 0; i < response.body().getResults().size(); i++) {
                        if(!checkAdult || !response.body().getResults().get(i).isAdult()){
                            results.add(new Movie(response.body().getResults().get(i)));
                        }                    }
                    if (movieComingSoon.getValue() != null && movieComingSoon.getValue().getData() != null) {
                        List<Movie> currentMovieList = movieComingSoon.getValue().getData();
                        currentMovieList.remove(currentMovieList.size() - 1);
                        currentMovieList.addAll(results);
                        resource.setData(currentMovieList);
                    } else {
                        resource.setData(results);
                    }
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    resource.setLoading(false);
                    movieComingSoon.postValue(resource);
                }else if (response.errorBody()!=null){
                    Log.d(TAG, "ERROR: getComingSoon=null");
                    Resource<List<Movie>> resource=new Resource<>();
                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message()+" - "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    movieComingSoon.postValue(resource);
                }
            }

            @Override
            public void onFailure(Call<ComingSoonApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * Metodo per ottenere la lista di film consigliati
     * @param movieRecommendations  l'oggetto LiveData associato alla Resource che contiene la lista di Movie
     * @param movie_id ID del film del quale si vogliono avere i film consigliati
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param checkAdult valore booleano utilizzato per il Parental Control
     */
    public void getRecommendations(MutableLiveData<Resource<List<Movie>>> movieRecommendations, int movie_id,String language, int page, boolean checkAdult){
        Call<RecommendationsApiTmdbResponse> call= tmdbServices.getRecommendations(movie_id, language, page, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<RecommendationsApiTmdbResponse>() {
            @Override
            public void onResponse(Call<RecommendationsApiTmdbResponse> call, Response<RecommendationsApiTmdbResponse> response) {
                Resource<List<Movie>> resource=new Resource();
                if (response.isSuccessful() && response.body() != null) {

                    List<MovieApiTmdbResponse> movies = response.body().getResults();
                    Log.d(TAG, "callback recommendations ok");
                    List<Movie> res = new ArrayList<Movie>();
                    for (int i = 0; i < movies.size(); i++) {
                        if(!checkAdult||!movies.get(i).isAdult()){
                            res.add(new Movie(movies.get(i)));
                        }
                    }
                    resource.setData(res);
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());

                }
                else if (response.errorBody()!=null){
                    Log.d(TAG, "ERROR: getRaccomandation=null");

                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message()+" - "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                movieRecommendations.postValue(resource);
            }

            @Override
            public void onFailure(Call<RecommendationsApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * metodo per ottenere i dettagli di un film
     * @param movieDetails l'oggetto LiveData associato alla Resource che contiene il Movie
     * @param movie_id ID del film del quale si vuole sapere i dettagli
     * @param language attributo per la lingua
     */
    public void getMovieDetails(MutableLiveData<Resource<Movie>> movieDetails, int movie_id, String language){
        Call<MovieApiTmdbResponse> call= tmdbServices.getMovieDetails(movie_id, language, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<MovieApiTmdbResponse>() {
            @Override
            public void onResponse(Call<MovieApiTmdbResponse> call, Response<MovieApiTmdbResponse> response) {
                Resource<Movie> resource=new Resource();
                if(response.isSuccessful() && response.body()!=null) {

                    MovieApiTmdbResponse movie = response.body();
                    Log.d(TAG, "callback details ok");
                    resource.setData(new Movie(movie));
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());

                }
                else if (response.errorBody()!=null){
                    Log.d(TAG, "ERROR: getMovieDetails=null");

                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message()+" - "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                movieDetails.postValue(resource);
            }

            @Override
            public void onFailure(Call<MovieApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * metodo per ottenere i credits di un film
     * @param credits l'oggetto LiveData associato alla Resource che contiene il MovieCreditsApiTmdbResponse
     * @param movie_id ID del film del quale si vogliono sapere i credits
     */
    public void getMovieCredits(MutableLiveData<Resource<MovieCreditsApiTmdbResponse>> credits, int movie_id){
        Call<MovieCreditsApiTmdbResponse> call= tmdbServices.getMovieCredits(movie_id, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<MovieCreditsApiTmdbResponse>() {
            @Override
            public void onResponse(Call<MovieCreditsApiTmdbResponse> call, Response<MovieCreditsApiTmdbResponse> response) {
                Resource<MovieCreditsApiTmdbResponse> resource = new Resource();
                if (response.isSuccessful() && response.body() != null) {

                    resource.setData(response.body());
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());

                    Log.d(TAG, "callback credits ok");

                } else if (response.errorBody() != null) {
                    Log.d(TAG, "ERROR: getTopRated=null");

                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message() + " - " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                credits.postValue(resource);
            }

            @Override
            public void onFailure(Call<MovieCreditsApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * metodo per ottenere le informazioni di una persona
     * @param peopleDetails l'oggetto LiveData associato alla Resource che contiene il People
     * @param person_id ID della persona della quale si vuole avere le informazioni
     * @param language attributo per la lingua
     */
    public void getPeopleDetails (MutableLiveData<Resource<People>> peopleDetails, int person_id, String language){
        Call<PeopleApiTmdbResponse> call= tmdbServices.getPeopleDetails(person_id, language, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<PeopleApiTmdbResponse>() {
            @Override
            public void onResponse(Call<PeopleApiTmdbResponse> call, Response<PeopleApiTmdbResponse> response) {
                Resource<People> resource=new Resource();
                if(response.isSuccessful() && response.body()!=null){

                    PeopleApiTmdbResponse people=response.body();
                    Log.d(TAG, "callback peopledetails ok");
                    resource.setData(new People(people));
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());

                } else if (response.errorBody() != null) {
                    Log.d(TAG, "ERROR: getTopRated=null");

                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message() + " - " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                peopleDetails.postValue(resource);

            }

            @Override
            public void onFailure(Call<PeopleApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * metodo per i credits di un film del quale fa parte la persona che passiamo in input
     * @param credits l'oggetto LiveData associato alla Resource che contiene il PeopleCreditsApiTmdbResponse
     * @param person_id ID della persona della quale si vogliono sapere i credits del film di cui fa parte
     * @param language attributo per la lingua
     */
    public void getPeopleCredits(MutableLiveData<Resource<PeopleCreditsApiTmdbResponse>> credits, int person_id, String language){
        Call<PeopleCreditsApiTmdbResponse> call= tmdbServices.getPeopleCredits(person_id, language,  Constants.API_TMDB_KEY);
        call.enqueue(new Callback<PeopleCreditsApiTmdbResponse>() {
            @Override
            public void onResponse(Call<PeopleCreditsApiTmdbResponse> call, Response<PeopleCreditsApiTmdbResponse> response) {
                Resource<PeopleCreditsApiTmdbResponse> resource=new Resource();
                if(response.isSuccessful() && response.body()!=null) {
                    Log.d(TAG, "callback peoplecredits ok");
                    resource.setData(response.body());
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());

                }
                else if (response.errorBody() != null) {
                    Log.d(TAG, "ERROR: getTopRated=null");

                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message() + " - " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                credits.postValue(resource);
            }

            @Override
            public void onFailure(Call<PeopleCreditsApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * metodo per ottenere il video trailer di un film
     * @param videos l'oggetto LiveData associato alla Resource che contiene il GetVideosApiTmdbResponse
     * @param movie_id ID del film del quale si vuole ottenere il video
     * @param language attributo per la lingua
     */
    public void getVideos(MutableLiveData<Resource<GetVideosApiTmdbResponse>> videos, int movie_id, String language)
    {
        Call<GetVideosApiTmdbResponse> call= tmdbServices.getVideos(movie_id,language, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<GetVideosApiTmdbResponse>() {
            @Override
            public void onResponse(Call<GetVideosApiTmdbResponse> call, Response<GetVideosApiTmdbResponse> response) {
                Resource<GetVideosApiTmdbResponse> resource=new Resource();
                if(response.isSuccessful() && response.body()!=null) {

                    resource.setData(response.body());
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());

                    Log.d(TAG, "callback getVideos ok");

                }
                else if (response.errorBody() != null) {
                    Log.d(TAG, "ERROR: getTopRated=null");

                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message() + " - " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                videos.postValue(resource);
            }

            @Override
            public void onFailure(Call<GetVideosApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * metodo per ottenere la lista completa dei generi per un film
     * @param genres l'oggetto LiveData associato alla Resource che contiene il GenreApiTmdbResponse
     * @param language attributo per la lingua
     */
    public void getGenres(MutableLiveData<Resource<GenreApiTmdbResponse>> genres, String language){
        Call<GenreApiTmdbResponse> call= tmdbServices.getGenres(language, Constants.API_TMDB_KEY);
        call.enqueue(new Callback<GenreApiTmdbResponse>() {
            @Override
            public void onResponse(Call<GenreApiTmdbResponse> call, Response<GenreApiTmdbResponse> response) {
                Resource<GenreApiTmdbResponse> resource=new Resource();
                if(response.isSuccessful() && response.body()!=null) {
                    resource.setData(response.body());
                    resource.setTotalResult(1);
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    Log.d(TAG, "callback getGenre ok");
                }
                else if (response.errorBody() != null) {
                    Log.d(TAG, "ERROR: getGenre=null");

                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message() + " - " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                genres.postValue(resource);
            }
            @Override
            public void onFailure(Call<GenreApiTmdbResponse> call, Throwable t) {
            }
        });
    }

    /**
     * metodoche che permette la ricerca di un film
     * @param movieSearch l'oggetto LiveData associato alla Resource che contiene una List di Movie
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param checkAdult valore booleano utilizzato per il Parental Control
     * @param query stringa utilizzata per la ricerca
     * @param region stringa contenente il paese scelto
     * @param year filtra l'anno da cercare
     * @param categorie filtra la categoria da cercare
     */
    public void getSearchMovie(MutableLiveData<Resource<List<Movie>>> movieSearch, String language, int page, boolean checkAdult,String query,String region,int year,int categorie){
        Call<SearchMovieApiTmdbResponse> call= tmdbServices.getSearchMovie(language,Constants.API_TMDB_KEY,query,page,!checkAdult,region,year);
        call.enqueue(new Callback<SearchMovieApiTmdbResponse>() {
            @Override
            public void onResponse(Call<SearchMovieApiTmdbResponse> call, Response<SearchMovieApiTmdbResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Resource<List<Movie>> resource = new Resource<>();
                    List<Movie> results = new ArrayList<Movie>();

                    for (int i = 0; i < response.body().getResults().length; i++) {
                        if (categorie == -1) {
                            results.add(response.body().getResults()[i]);
                        }
                        else
                        {
                            int y = 0;
                            boolean found = false;
                            while (y<response.body().getResults()[i].getGenre_ids().length && !found) {
                                if (response.body().getResults()[i].getGenre_ids()[y] == categorie) {
                                    results.add(response.body().getResults()[i]);
                                    found = true;
                                }
                                y++;
                            }
                        }
                    }
                    if (movieSearch.getValue() != null && movieSearch.getValue().getData() != null) {
                        List<Movie> currentMovieList = movieSearch.getValue().getData();
                        currentMovieList.remove(currentMovieList.size()-1);
                        currentMovieList.addAll(results);
                        resource.setData(currentMovieList);
                    } else {
                        resource.setData(results);
                    }
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    resource.setLoading(false);
                    movieSearch.postValue(resource);
                }else if (response.errorBody()!=null){
                    Log.d(TAG, "ERROR: getSearchMovie=null");
                    Resource<List<Movie>> resource=new Resource<>();
                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message()+" - "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    movieSearch.postValue(resource);
                }
            }

            @Override
            public void onFailure(Call<SearchMovieApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }

    /**
     * metodoche che permette la ricerca di una persona
     * @param peopleSearch l'oggetto LiveData associato alla Resource che contiene una List di People
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param checkAdult valore booleano utilizzato per il Parental Control
     * @param query stringa utilizzata per la ricerca
     * @param region stringa contenente il paese scelto
     */
    public void getSearchPeople(MutableLiveData<Resource<List<People>>> peopleSearch, String language, int page, boolean checkAdult,String query,String region){
        Call<SearchPeopleApiTmdbResponse> call= tmdbServices.getSearchPeople(language,Constants.API_TMDB_KEY,query,page,!checkAdult,region);
        call.enqueue(new Callback<SearchPeopleApiTmdbResponse>() {
            @Override
            public void onResponse(Call<SearchPeopleApiTmdbResponse> call, Response<SearchPeopleApiTmdbResponse> response) {
                if(response.isSuccessful() && response.body()!=null) {
                    Resource<List<People>> resource = new Resource<>();
                    List<People> results = new ArrayList<People>();
                    for (int i = 0; i < response.body().getResults().length; i++) {
                        if(!checkAdult || !response.body().getResults()[i].isAdult()){
                            results.add(response.body().getResults()[i]);
                        }
                    }
                    if (peopleSearch.getValue() != null && peopleSearch.getValue().getData() != null) {
                        List<People> currentMovieList = peopleSearch.getValue().getData();
                        currentMovieList.remove(currentMovieList.size() - 1);
                        currentMovieList.addAll(results);
                        resource.setData(currentMovieList);
                    } else {
                        resource.setData(results);
                    }
                    resource.setTotalResult(response.body().getTotal_results());
                    resource.setStatusCode(response.code());
                    resource.setStatusMessage(response.message());
                    resource.setLoading(false);
                    peopleSearch.postValue(resource);
                }else if (response.errorBody()!=null){
                    Log.d(TAG, "ERROR: getComingSoon=null");
                    Resource<List<People>> resource=new Resource<>();
                    resource.setStatusCode(response.code());
                    try {
                        resource.setStatusMessage(response.message()+" - "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    peopleSearch.postValue(resource);
                }
            }
            @Override
            public void onFailure(Call<SearchPeopleApiTmdbResponse> call, Throwable t) {
                Log.d(TAG, "Error:"+t.toString());
            }
        });
    }
}
