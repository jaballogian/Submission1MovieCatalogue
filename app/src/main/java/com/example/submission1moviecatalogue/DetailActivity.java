package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.submission1moviecatalogue.FavoriteDetailActivity.EXTRA_MOVIE;
import static com.example.submission1moviecatalogue.FavoriteDetailActivity.EXTRA_POSITION;
import static com.example.submission1moviecatalogue.FavoriteDetailActivity.RESULT_ADD;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieTitleTextView = (TextView) findViewById(R.id.movieTitleTextView);
        movieDateTextView = (TextView) findViewById(R.id.movieDateTextView);
        movieRatingTextView = (TextView) findViewById(R.id.movieRatingTextView);
        movieDescriptionTextView = (TextView) findViewById(R.id.movieDescriptionTextView);
        movieCoverImageView = (ImageView) findViewById(R.id.movieCoverImageView);
        progressBar = (ProgressBar) findViewById(R.id.progressBarActivityDetail);
        addToFavorite = (Button) findViewById(R.id.addToFavorite);

        session = new Session();

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

                    Cursor cursor = movieHelper.queryAll();
                    ArrayList<String> titles = MappingHelper.mapCursorToString(cursor);
                    Log.d("titles", titles.toString());

                    int already = 0;
                    for(int i = 0; i < titles.size(); i++){

                        if(titles.get(i).equals(movie.getTitle())){

                            already++;
                        }
                    }

                    if(already == 0){

                        long result = movieHelper.insert(values);

                        if (result > 0) {
//                            movie.setId((int) result);
                            movie.setId(movie.getId());
                            setResult(RESULT_ADD, intent);
                            Toast.makeText(DetailActivity.this, getString(R.string.added_to_favorite), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(DetailActivity.this, getString(R.string.failed_to_add_data), Toast.LENGTH_SHORT).show();
                        }
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

                    Cursor cursor = movieHelperTV.queryAll();
                    ArrayList<String> titles = MappingHelperTV.mapCursorToStringTV(cursor);
                    Log.d("titles", titles.toString());

                    int already = 0;
                    for(int i = 0; i < titles.size(); i++){

                        if(titles.get(i).equals(movie.getTitle())){

                            already++;
                        }
                    }

                    if(already == 0){

                        long result = movieHelperTV.insert(values);

                        if (result > 0) {
//                            movie.setId((int) result);
                            movie.setId(movie.getId());
                            setResult(RESULT_ADD, intent);
                            Toast.makeText(DetailActivity.this, getString(R.string.added_to_favorite), Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(DetailActivity.this, getString(R.string.failed_to_add_data), Toast.LENGTH_SHORT).show();
                        }
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
