package com.example.submission1moviecatalogue;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "com.example.submission1moviecatalogue.MOVIE";
    private static final String SCHEME = "content";

    static String TABLE_NAME = "movie";
    static final class MovieColumns implements BaseColumns {

        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String RELEASE_DATE = "release_date";
        static String RATING = "rating";
        static String COVER = "cover";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
