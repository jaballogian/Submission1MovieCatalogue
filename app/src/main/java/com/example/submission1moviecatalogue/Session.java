package com.example.submission1moviecatalogue;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class Session {

//    private int moviesSize;
//    private int tvSize;
//    private String[] movies = new String[moviesSize];
//    private String[] tvs = new String[tvSize];
    private SharedPreferences sharedPreferences;

    public Session(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public boolean getDailyReminderSetting(){
        return sharedPreferences.getBoolean("dailyReminder", false);
    }

    public void setDailyReminderSetting(boolean dailyReminderSetting){
        sharedPreferences.edit().putBoolean("dailyReminder", dailyReminderSetting).commit();
    }

    public boolean getReleaseTodaySetting(){
        return sharedPreferences.getBoolean("releaseToday", false);
    }

    public void setReleaseTodaySetting(boolean releaseTodaySetting){
        sharedPreferences.edit().putBoolean("releaseToday", releaseTodaySetting).commit();
    }

    public int getMoviesSize() {
        return sharedPreferences.getInt("moviesSize",0);
    }

    public void setMoviesSize(int moviesSize) {
        sharedPreferences.edit().putInt("moviesSize", moviesSize).commit();
    }

    public int getTvSize() {
        return sharedPreferences.getInt("tvSize",0);
    }

    public void setTvSize(int tvSize) {
        sharedPreferences.edit().putInt("tvSize", tvSize).commit();
    }

//    public String[] getMovies() {
//        return sharedPreferences.getStringSet("movies", );
//    }
//
//    public void setMovies(String[] movies) {
//        this.movies = movies;
//    }
//
//    public String[] getTvs() {
//        return tvs;
//    }
//
//    public void setTvs(String[] tvs) {
//        this.tvs = tvs;
//    }


}
