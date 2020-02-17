package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import static com.example.submission1moviecatalogue.FavoriteDetailActivity.EXTRA_MOVIE;

public class SearchActivity extends AppCompatActivity {

    private String hint, type;
    private int tab;
    private RecyclerView movieRecyclerView;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ProgressBar progressBar;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private MainViewModel mainViewModel;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressBar = (ProgressBar) findViewById(R.id.progressBarActivitySearch);
        movieRecyclerView = (RecyclerView) findViewById(R.id.moviesRecylerViewActivitySearch);

        hint = getIntent().getExtras().getString("hint");
        tab = getIntent().getExtras().getInt("currentTab");

        Log.d("hintSearchActivity", hint);
        Log.d("tabSearchActivity", String.valueOf(tab));

        if(tab == 0){
            type = "movie";
        }
        else if(tab == 1){
            type = "tv";
        }

        session = new Session();

        showRecyclerList();

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        mainViewModel.setSearchMovie(type, "en-US", hint);
        showLoading(true);

        mainViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                Log.d("movies", movies.toString());
                if (movies != null) {
                    movieRecyclerViewAdapter.setData(movies);
                    Log.d("status_fragment_movies", "movies is not null");
                    showLoading(false);
                }
                else {

                    Log.d("status_fragment_movies", "movies is null");
                }
            }
        });

    }

    private void showRecyclerList(){
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(movies);
        movieRecyclerViewAdapter.notifyDataSetChanged();
        movieRecyclerView.setAdapter(movieRecyclerViewAdapter);

        session.setMoviesSize(movieRecyclerViewAdapter.getItemCount());

        movieRecyclerViewAdapter.setOnItemClickCallback(new MovieRecyclerViewAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                showSelectedMovie(movie);
            }
        });
    }

    private void showSelectedMovie(Movie movie) {

        Intent moveWithObjectIntent = new Intent(this, DetailActivity.class);
        moveWithObjectIntent.putExtra(EXTRA_MOVIE, movie);
        moveWithObjectIntent.putExtra("type", type);
        startActivity(moveWithObjectIntent);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
