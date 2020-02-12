package com.example.submission1moviecatalogue;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class Session {

    private int moviesSize;
    private int tvSize;
    private String[] movies = new String[moviesSize];
    private String[] tvs = new String[tvSize];

    public int getMoviesSize() {
        return moviesSize;
    }

    public void setMoviesSize(int moviesSize) {
        this.moviesSize = moviesSize;
    }

    public int getTvSize() {
        return tvSize;
    }

    public void setTvSize(int tvSize) {
        this.tvSize = tvSize;
    }

    public String[] getMovies() {
        return movies;
    }

    public void setMovies(String[] movies) {
        this.movies = movies;
    }

    public String[] getTvs() {
        return tvs;
    }

    public void setTvs(String[] tvs) {
        this.tvs = tvs;
    }
}
