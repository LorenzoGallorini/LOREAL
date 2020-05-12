package com.example.cinemhub.repositories;

public class MovieRepository {
    private static MovieRepository instance;
    private MovieRepository(){}

    public static MovieRepository getInstance(){
        if(instance==null){
            instance=new MovieRepository();
        }
        return instance;
    }
}
