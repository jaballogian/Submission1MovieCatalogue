package com.example.submission1moviecatalogue;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import static com.example.submission1moviecatalogue.DatabaseContractTV.AUTHORITY;
import static com.example.submission1moviecatalogue.DatabaseContractTV.TABLE_NAME;
import static com.example.submission1moviecatalogue.DatabaseContractTV.TVColumns.CONTENT_URI_TV;

public class ContentProviderTV extends ContentProvider {
    public ContentProviderTV() {
    }

    private static final int TV = 1;
    private static final int TV_ID = 2;
    private MovieHelperTV movieHelperTV;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {

        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, TV);

        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                TV_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case TV_ID:
                deleted = movieHelperTV.deleteById(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_TV, null);
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long added;
        switch (sUriMatcher.match(uri)) {
            case TV:
                added = movieHelperTV.insert(values);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_TV, null);
        return Uri.parse(CONTENT_URI_TV + "/" + added);
    }

    @Override
    public boolean onCreate() {
        movieHelperTV = MovieHelperTV.getInstance(getContext());
        movieHelperTV.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case TV:
                cursor = movieHelperTV.queryAll();
                break;
            case TV_ID:
                cursor = movieHelperTV.queryById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updated;
        switch (sUriMatcher.match(uri)) {
            case TV_ID:
                updated = movieHelperTV.update(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI_TV, null);
        return updated;
    }
}
