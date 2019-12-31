package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;
    private ListView listView;
    private String[] movieTitle, movieDescription, movieRating, movieDate;
    private TypedArray movieCover;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.movieListView);
        movieAdapter = new MovieAdapter(this);
        listView.setAdapter(movieAdapter);

        initializeListView();
        addItemToListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie movie = new Movie();
                movie.setTitle(movieTitle[position]);
                movie.setReleaseDate(movieDate[position]);
                movie.setRating(movieRating[position]);
                movie.setDescription(movieDescription[position]);
                movie.setCover(movieCover.getResourceId(position, -1));

                Intent moveWithObjectIntent = new Intent(MainActivity.this, DetailActivity.class);
                moveWithObjectIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
                startActivity(moveWithObjectIntent);
            }
        });
    }

    private void initializeListView(){

        movieTitle = getResources().getStringArray(R.array.movieTitle);
        movieDescription= getResources().getStringArray(R.array.movieDescription);
        movieRating = getResources().getStringArray(R.array.movieRating);
        movieDate = getResources().getStringArray(R.array.movieDate);
        movieCover = getResources().obtainTypedArray(R.array.movieCover);
    }

    private void addItemToListView() {

        movies = new ArrayList<>();
        for (int i = 0; i < movieTitle.length; i++) {
            Movie movie = new Movie();
            movie.setCover(movieCover.getResourceId(i, -1));
            movie.setTitle(movieTitle[i]);
            movie.setDescription(movieDescription[i]);
            movie.setRating(movieRating[i]);
            movie.setReleaseDate(movieDate[i]);
            movies.add(movie);
        }
        movieAdapter.setMovies(movies);
    }
}