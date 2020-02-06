package com.example.submission1moviecatalogue;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_NAME = "movie";
    static final class MovieColumns implements BaseColumns {

        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String RELEASE_DATE = "release_date";
        static String RATING = "rating";
        static String COVER = "cover";

    }
}
