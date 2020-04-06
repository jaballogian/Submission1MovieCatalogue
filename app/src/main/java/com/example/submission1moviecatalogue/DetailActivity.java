package com.example.submission1moviecatalogue;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.submission1moviecatalogue.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.example.submission1moviecatalogue.DatabaseContractTV.TVColumns.CONTENT_URI_TV;
import static com.example.submission1moviecatalogue.FavoriteDetailActivity.EXTRA_MOVIE;
import static com.example.submission1moviecatalogue.FavoriteDetailActivity.EXTRA_POSITION;

public class DetailActivity extends AppCompatActivity {

    private TextView movieTitleTextView, movieDateTextView, movieRatingTextView, movieDescriptionTextView;
    private ImageView movieCoverImageView;
    private ProgressBar progressBar;
    private Button addToFavorite;
    private String type;
    private Movie movie;
    private int position;
    private MovieHelper movieHelper;
    private MovieHelperTV movieHelperTV;
    private Session session;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieTitleTextView = (TextView) findViewById(R.id.movieTitleTextView);
        movieDateTextView = (TextView) findViewById(R.id.movieDateTextView);
        movieRatingTextView = (TextView) findViewById(R.id.movieRatingTextView);
        movieDescriptionTextView = (TextView) findViewById(R.id.movieDescriptionTextView);
        movieCoverImageView = (ImageView) findViewById(R.id.movieCoverImageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBarActivityDetail);
        addToFavorite = (Button) findViewById(R.id.addToFavorite);

        session = new Session(this);

        showLoading(true);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelperTV = MovieHelperTV.getInstance(getApplicationContext());

        movieHelper.open();
        movieHelperTV.open();

        type = getIntent().getStringExtra("type");

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        movieTitleTextView.setText(movie.getTitle());
        movieDateTextView.setText(movie.getReleaseDate());
        movieRatingTextView.setText(movie.getRating());
        movieDescriptionTextView.setText(movie.getDescription());
        Log.d("movie.getCover", String.valueOf(movie.getCover()));
//        movieCoverImageView.setImageResource(movie.getCover());
        Glide.with(this)
                .load(movie.getCover())
//                .apply(new RequestOptions().override(55, 55))
                .into(movieCoverImageView);

        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        showLoading(false);

        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type.equals("movie")){

                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_MOVIE, movie);
                    intent.putExtra(EXTRA_POSITION, position);

                    ContentValues values = new ContentValues();
                    values.put(DatabaseContract.MovieColumns.TITLE, movie.getTitle());
                    values.put(DatabaseContract.MovieColumns.DESCRIPTION, movie.getDescription());
                    values.put(DatabaseContract.MovieColumns.RELEASE_DATE, movie.getReleaseDate());
                    values.put(DatabaseContract.MovieColumns.RATING, movie.getRating());
                    values.put(DatabaseContract.MovieColumns.COVER, movie.getCover());

                    int already = 0;
                    try {

                        WeakReference<DetailActivity> weakContext = new WeakReference<>(DetailActivity.this);
//                    Context context = weakContext.get();
                        Cursor dataCursor = getContentResolver().query(DatabaseContract.MovieColumns.CONTENT_URI, null, null, null, null);
                        ArrayList<Movie> movies = MappingHelper.mapCursorToArrayList(dataCursor);
//                    Cursor cursor = movieHelper.queryAll();
//                    ArrayList<String> titles = MappingHelper.mapCursorToString(cursor);

                        Log.d("titlesDetailActivity", movies.toString());

                        for(int i = 0; i < movies.size(); i++){

                            if(movies.get(i).getTitle().equals(movie.getTitle())){

                                already++;
                            }
                        }
                    }
                    catch (Exception error){

                    }

                    if(already == 0){

                        getContentResolver().insert(CONTENT_URI, values);
                        Toast.makeText(DetailActivity.this, getString(R.string.added_to_favorite), Toast.LENGTH_LONG).show();
                        Intent toFavoriteActivity = new Intent(DetailActivity.this, FavoriteActivity.class);
                        startActivity(toFavoriteActivity);
                        finish();
//                        long result = movieHelper.insert(values);
//
//                        if (result > 0) {
////                            movie.setId((int) result);
//                            movie.setId(movie.getId());
//                            setResult(RESULT_ADD, intent);
//                            Toast.makeText(DetailActivity.this, getString(R.string.added_to_favorite), Toast.LENGTH_LONG).show();
//                            finish();
//                        } else {
//                            Toast.makeText(DetailActivity.this, getString(R.string.failed_to_add_data), Toast.LENGTH_SHORT).show();
//                        }
                    }
                    else {

                        Toast.makeText(DetailActivity.this, getString(R.string.already_in_favorite), Toast.LENGTH_SHORT).show();
                    }

                }
                else if(type.equals("tv")){

                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_MOVIE, movie);
                    intent.putExtra(EXTRA_POSITION, position);

                    ContentValues values = new ContentValues();
                    values.put(DatabaseContractTV.TVColumns.TITLE, movie.getTitle());
                    values.put(DatabaseContractTV.TVColumns.DESCRIPTION, movie.getDescription());
                    values.put(DatabaseContractTV.TVColumns.RELEASE_DATE, movie.getReleaseDate());
                    values.put(DatabaseContractTV.TVColumns.RATING, movie.getRating());
                    values.put(DatabaseContractTV.TVColumns.COVER, movie.getCover());

                    int already = 0;
                    try {

                        WeakReference<DetailActivity> weakContext = new WeakReference<>(DetailActivity.this);
                        Context context = weakContext.get();
                        Cursor dataCursor = context.getContentResolver().query(CONTENT_URI_TV, null, null, null, null);
                        ArrayList<Movie> tvs = MappingHelperTV.mapCursorToArrayListTV(dataCursor);
//                    Cursor cursor = movieHelperTV.queryAll();
//                    ArrayList<String> titles = MappingHelperTV.mapCursorToStringTV(cursor);
                        Log.d("titlesDetailActivity", tvs.toString());

                        for(int i = 0; i < tvs.size(); i++){

                            if(tvs.get(i).getTitle().equals(movie.getTitle())){

                                already++;
                            }
                        }
                    }
                    catch (Exception error){

                    }

                    if(already == 0){

                        getContentResolver().insert(CONTENT_URI_TV, values);
                        Toast.makeText(DetailActivity.this, getString(R.string.added_to_favorite), Toast.LENGTH_LONG).show();
                        Intent toFavoriteActivity = new Intent(DetailActivity.this, FavoriteActivity.class);
                        startActivity(toFavoriteActivity);
                        finish();
//                        long result = movieHelperTV.insert(values);
//
//                        if (result > 0) {
////                            movie.setId((int) result);
//                            movie.setId(movie.getId());
//                            setResult(RESULT_ADD, intent);
//                            Toast.makeText(DetailActivity.this, getString(R.string.added_to_favorite), Toast.LENGTH_LONG).show();
//                            finish();
//                        } else {
//                            Toast.makeText(DetailActivity.this, getString(R.string.failed_to_add_data), Toast.LENGTH_SHORT).show();
//                        }
                    }
                    else {

                        Toast.makeText(DetailActivity.this, getString(R.string.already_in_favorite), Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
