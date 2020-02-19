package com.example.submission1moviecatalogue;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

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

    }

    @Override
    public void onDataSetChanged() {

        Cursor cursorMovie = movieHelper.queryAll();
        ArrayList<String> titlesMovie = MappingHelper.mapCursorToStringImage(cursorMovie);
        Log.d("titlesMovie", titlesMovie.toString());

        Cursor cursorTV= movieHelperTV.queryAll();
        ArrayList<String> titlesTV = MappingHelperTV.mapCursorToStringImageTV(cursorTV);
        Log.d("titlesTV ", titlesTV.toString());

        for(int i = 0; i < titlesMovie.size(); i++){

            byte [] encodeByte = Base64.decode(titlesMovie.get(i),Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            mWidgetItems.add(bitmap);
        }

        for(int j = 0; j < 0; j++){

            byte [] encodeByte = Base64.decode(titlesTV.get(j),Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            mWidgetItems.add(bitmap);
        }
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
}
