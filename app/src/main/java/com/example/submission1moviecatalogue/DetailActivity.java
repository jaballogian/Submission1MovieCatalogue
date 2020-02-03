package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private TextView movieTitleTextView, movieDateTextView, movieRatingTextView, movieDescriptionTextView;
    private ImageView movieCoverImageView;
    private ProgressBar progressBar;

    public static final String EXTRA_MOVIE = "extra_movie";

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

        showLoading(true);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
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

        showLoading(false);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
