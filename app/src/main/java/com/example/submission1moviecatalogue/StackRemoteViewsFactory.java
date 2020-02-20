package com.example.submission1moviecatalogue;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private MovieHelper movieHelper;
    private MovieHelperTV movieHelperTV;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

        movieHelper = MovieHelper.getInstance(mContext);
        movieHelperTV = MovieHelperTV.getInstance(mContext);

        movieHelper.open();
        movieHelperTV.open();

        addItemsToWidget();
    }

    @Override
    public void onDataSetChanged() {

        addItemsToWidget();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putInt(AppWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private void addItemsToWidget(){

        Cursor cursorMovie = movieHelper.queryAll();
        ArrayList<String> titlesMovie = MappingHelper.mapCursorToStringImage(cursorMovie);
        Log.d("titlesMovie", titlesMovie.toString());

        Cursor cursorTV= movieHelperTV.queryAll();
        ArrayList<String> titlesTV = MappingHelperTV.mapCursorToStringImageTV(cursorTV);
        Log.d("titlesTV ", titlesTV.toString());

        for(int i = 0; i < titlesMovie.size(); i++){

            mWidgetItems.add(getBitmapFromURL(titlesMovie.get(i)));
        }

        for(int j = 0; j < titlesTV.size(); j++){

            mWidgetItems.add(getBitmapFromURL(titlesTV.get(j)));
        }
    }

    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
