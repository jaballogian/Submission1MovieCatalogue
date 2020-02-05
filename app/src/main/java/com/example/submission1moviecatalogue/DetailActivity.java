package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

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

public class DetailActivity extends AppCompatActivity {

    private TextView movieTitleTextView, movieDateTextView, movieRatingTextView, movieDescriptionTextView;
    private ImageView movieCoverImageView;
    private ProgressBar progressBar;
    private Button addToFavorite;
    private String type;

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
        addToFavorite = (Button) findViewById(R.id.addToFavorite);

        showLoading(true);

        type = getIntent().getStringExtra("type");

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

        addToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent toFavoriteActivity = new Intent(DetailActivity.this, FavoriteActivity.class);
//                toFavoriteActivity.putExtra("type", type);
//                startActivity(toFavoriteActivity);
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
