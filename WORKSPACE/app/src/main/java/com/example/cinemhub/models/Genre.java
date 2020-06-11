package com.example.cinemhub.models;

/**
 * classe per la gestione dei Generi
 */
public class Genre {
    private int id;
    private String name;

    /**
     * costruttore della classe Genre
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
