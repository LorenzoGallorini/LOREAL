package com.example.cinemhub.models;
/**
 * Classe CastApiTmdbResponse
 * Classe custom creata ad-hoc per l'utilizzo nelle API di TMDB
 */
public class CastApiTmdbResponse {
    private int cast_id;
    private String character;
    private String credit_id;
    private int gender;
    private int id;
    private String name;
    private int order;
    private String profile_path;

    /**
     * Metodo costruttore per creare la classe
     * @param cast_id di tipo int che identifica univocamente il membro del cast
     * @param character di tipo string che dice il nomo del personaggio che interpreta
     * @param credit_id
     * @param gender di tipo int per identificare il genere
     * @param id di tipo int che identifica univocamente il membro del cast nel db
     * @param name di tipo string e rappresemnta il nome della persona
     * @param order di tipo int che serve per tenere l'ordine con cui l'API ha restituito i membri del cast
     * @param profile_path di tipo string indirizzo dell'immagine
     */
    public CastApiTmdbResponse(int cast_id, String character, String credit_id, int gender, int id, String name, int order, String profile_path) {
        this.cast_id = cast_id;
        this.character = character;
        this.credit_id = credit_id;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.order =  order;
        this.profile_path = profile_path;
    }

    /**
     * Costruttore di copia partendo da un oggetto dello stesso tipo
     * @param castApiTmdbResponse
     */
    public CastApiTmdbResponse(CastApiTmdbResponse castApiTmdbResponse) {
        this.cast_id = castApiTmdbResponse.getCast_id();
        this.character = castApiTmdbResponse.getCharacter();
        this.credit_id = castApiTmdbResponse.getCredit_id();
        this.gender = castApiTmdbResponse.getGender();
        this.id = castApiTmdbResponse.getId();
        this.name = castApiTmdbResponse.getName();
        this.order = castApiTmdbResponse.getOrder();
        this.profile_path = castApiTmdbResponse.getProfile_path();
    }

    /**SEZIONE GETTER & SETTER*/

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
