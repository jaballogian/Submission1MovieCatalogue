package com.example.submission1moviecatalogue;

import android.database.Cursor;

import java.util.ArrayList;

public class MappingHelperTV {

    public static ArrayList<Movie> mapCursorToArrayListTV(Cursor notesCursor) {
        ArrayList<Movie> movieList = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(DatabaseContractTV.TVColumns._ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContractTV.TVColumns.TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContractTV.TVColumns.DESCRIPTION));
            String releaseDate = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContractTV.TVColumns.RELEASE_DATE));
            String rating = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContractTV.TVColumns.RATING));
            String cover = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContractTV.TVColumns.COVER));
            movieList.add(new Movie(id, title, description, releaseDate, rating, cover));
        }
        return movieList;
    }

    public static ArrayList<String> mapCursorToStringTV(Cursor notesCursor) {
        ArrayList<String> strings = new ArrayList<>();
        while (notesCursor.moveToNext()) {
            String string = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE));
            strings.add(string);
        }
        return strings;
    }
}
