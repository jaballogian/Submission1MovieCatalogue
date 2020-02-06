package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

        showLoading(true);

        movieHelper = MovieHelper.getInstance(getApplicationContext());

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

                Intent intent = new Intent();
                intent.putExtra(EXTRA_MOVIE, movie);
                intent.putExtra(EXTRA_POSITION, position);

                ContentValues values = new ContentValues();
                values.put(DatabaseContract.MovieColumns.TITLE, movie.getTitle());
                values.put(DatabaseContract.MovieColumns.DESCRIPTION, movie.getDescription());
                values.put(DatabaseContract.MovieColumns.RELEASE_DATE, movie.getReleaseDate());
                values.put(DatabaseContract.MovieColumns.RATING, movie.getRating());
                values.put(DatabaseContract.MovieColumns.COVER, movie.getCover());

                long result = movieHelper.insert(values);
                if (result > 0) {
                    movie.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    finish();
                } else {
                    Toast.makeText(DetailActivity.this, getString(R.string.failed_to_add_data), Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(DetailActivity.this, getString(R.string.added_to_favorite), Toast.LENGTH_LONG).show();
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
