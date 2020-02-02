package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private TextView movieTitleTextView, movieDateTextView, movieRatingTextView, movieDescriptionTextView;
    private ImageView movieCoverImageView;

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
    }
}
