package com.example.submission1moviecatalogue;

import android.provider.BaseColumns;

public class DatabaseContractTV {

    static String TABLE_NAME = "tv";
    static final class TVColumns implements BaseColumns {

        static String TITLE = "title";
        static String DESCRIPTION = "description";
        static String RELEASE_DATE = "release_date";
        static String RATING = "rating";
        static String COVER = "cover";

    }
}
