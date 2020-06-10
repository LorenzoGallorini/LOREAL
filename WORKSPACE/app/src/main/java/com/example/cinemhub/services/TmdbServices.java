package com.example.cinemhub.services;

import com.example.cinemhub.models.ComingSoonApiTmdbResponse;
import com.example.cinemhub.models.GenreApiTmdbResponse;
import com.example.cinemhub.models.GetVideosApiTmdbResponse;
import com.example.cinemhub.models.MovieApiTmdbResponse;
import com.example.cinemhub.models.MovieCreditsApiTmdbResponse;
import com.example.cinemhub.models.NowPlayingApiTmdbResponse;
import com.example.cinemhub.models.PeopleApiTmdbResponse;
import com.example.cinemhub.models.PeopleCreditsApiTmdbResponse;
import com.example.cinemhub.models.RecommendationsApiTmdbResponse;
import com.example.cinemhub.models.SearchMovieApiTmdbResponse;
import com.example.cinemhub.models.SearchPeopleApiTmdbResponse;
import com.example.cinemhub.models.TopRatedApiTmdbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
/**
 * rappresenta le chiamate HTTP per usare l'API di https://developers.themoviedb.org/3/
 */
public interface TmdbServices {
    /**
     * Riceve una lista di Movie attualmente al cinema
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @param region filtra i risultati in base al paese scelto
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("movie/now_playing")
    Call<NowPlayingApiTmdbResponse> getNowPlaying (@Query("language") String language,
                                                   @Query("page") int page,
                                                   @Query("api_key") String api_key,
                                                   @Query("region") String region);
    /**
     * Riceve una lista di Movie considerati i migliori
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @param region filtra i risultati in base al paese scelto
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("movie/top_rated")
    Call<TopRatedApiTmdbResponse> getTopRated (@Query("language") String language,
                                               @Query("page") int page,
                                               @Query("api_key") String api_key,
                                               @Query("region") String region);
    /**
     * Riceve una lista di Movie che dovranno uscire
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @param region filtra i risultati in base al paese scelto
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("movie/upcoming")
    Call<ComingSoonApiTmdbResponse> getComingSoon (@Query("language") String language,
                                                   @Query("page") int page,
                                                   @Query("api_key") String api_key,
                                                   @Query("region") String region);
    /**
     * riceve una lista di Movie raccomandati in base al film passato attraverso il movie_id
     * @param movie_id ID del film del quale si vogliono sapere i raccomandati
     * @param language attributo per la lingua
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("movie/{movie_id}/recommendations")
    Call<RecommendationsApiTmdbResponse> getRecommendations (@Path("movie_id") int movie_id,
                                                             @Query("language") String language,
                                                             @Query("page") int page,
                                                             @Query("api_key") String api_key);
    /**
     * riceve le informazioni riguardanti il film passato attraverso il movie_id
     * @param movie_id ID del film del quale si vogliono sapere i dettagli
     * @param language attributo per la lingua
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("movie/{movie_id}")
    Call<MovieApiTmdbResponse> getMovieDetails (@Path("movie_id") int movie_id,
                                                @Query("language") String language,
                                                @Query("api_key") String api_key);
    /**
     *riceve le informazioni riguardanti il cast e la crew del film passato attraverso il movie_id
     * @param movie_id ID del film del quale si vogliono sapere i Credits
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("movie/{movie_id}/credits")
    Call<MovieCreditsApiTmdbResponse> getMovieCredits (@Path("movie_id") int movie_id,
                                                       @Query("api_key") String api_key);
    /**
     * Riceve le informazioni della persona passata tramite person_id
     * @param person_id ID della persona della quale si vogliono sapere i dettagli
     * @param language attributo per la lingua
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("person/{person_id}")
    Call<PeopleApiTmdbResponse> getPeopleDetails (@Path("person_id") int person_id,
                                                  @Query("language") String language,
                                                  @Query("api_key") String api_key);
    /**
     * Riceve i credits di un film attraverso person_id
     * @param person_id ID della persona della quale si vogliono sapere i credits
     * @param language attributo per la lingua
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("person/{person_id}/movie_credits")
    Call<PeopleCreditsApiTmdbResponse> getPeopleCredits(@Path("person_id") int person_id,
                                                        @Query("language") String language,
                                                        @Query("api_key") String api_key);
    /**
     * Riceve le informazioni riguardanti il video del film passato attraverso il movie_id
     * @param movie_id ID del film del quale si vuole vedere il video
     * @param language attributo per la lingua
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("movie/{movie_id}/videos")
    Call<GetVideosApiTmdbResponse> getVideos(@Path("movie_id") int movie_id,
                                             @Query("language") String language,
                                             @Query("api_key") String api_key);
    /**
     * Riceve la lista dei generi ufficiali per i film
     * @param language attributo per la lingua
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("genre/movie/list")
    Call<GenreApiTmdbResponse> getGenres(@Query("language") String language,
                                         @Query("api_key") String api_key);
    /**
     * Riceve una lista di persone in base alla parola cercata
     * @param language attributo per la lingua
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @param query stringa da cercare
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param include_adult valore per includere o meno immagini per adulti
     * @param region filtra i risultati in base al paese scelto
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("search/person")
    Call<SearchPeopleApiTmdbResponse> getSearchPeople(@Query("language") String language,
                                                      @Query("api_key") String api_key,
                                                      @Query("query") String query,
                                                      @Query("page") int page,
                                                      @Query("include_adult") boolean include_adult,
                                                      @Query("region") String region);
    /**
     * Riceve una lista di film in base alla parola cercata
     * @param language attributo per la lingua
     * @param api_key la nostra API KEY personale che ci permette di fare la chiamata
     * @param query stringa da cercare
     * @param page numero della pagina della quale si vuole la lista di Movie
     * @param include_adult valore per includere o meno immagini per adulti
     * @param region filtra i risultati in base al paese scelto
     * @param primary_release_year data di rilascio del film da cercare
     * @return un oggetto custom creato appositamente per questa API
     */
    @GET("search/movie")
    Call<SearchMovieApiTmdbResponse> getSearchMovie(@Query("language") String language,
                                                    @Query("api_key") String api_key,
                                                    @Query("query") String query,
                                                    @Query("page") int page,
                                                    @Query("include_adult") boolean include_adult,
                                                    @Query("region") String region,
                                                    @Query("primary_release_year") int primary_release_year);
}
