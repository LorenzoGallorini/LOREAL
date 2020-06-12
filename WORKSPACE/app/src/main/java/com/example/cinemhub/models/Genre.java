package com.example.cinemhub.models;

/**
 * classe per la gestione dei Generi
 */
public class Genre {
    private int id;
    private String name;

    /**
     * Costruttore della classe Genre
     * @param id intero che indica in maniera univoca l'ID del genere dei film
     * @param name stringa che indica il nome del genere dei film
     */
    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
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


}
