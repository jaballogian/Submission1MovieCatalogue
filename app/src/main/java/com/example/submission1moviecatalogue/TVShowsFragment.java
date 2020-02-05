package com.example.submission1moviecatalogue;


import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowsFragment extends Fragment {

    private RecyclerView movieRecyclerView;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ProgressBar progressBar;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private MainViewModel mainViewModel;
    private String type = "tv";

    public TVShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_tvshows, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarFragmentTVShows);
        movieRecyclerView = (RecyclerView) view.findViewById(R.id.tvShowsRecylerView);
//        movieRecyclerView.setHasFixedSize(true);
//        movies.addAll(getListHeroes());
        showRecyclerList();

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MainViewModel.class);

        mainViewModel.setMovie(type, "en-US");
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

        return view;
    }

    private void showRecyclerList(){
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(movies);
        movieRecyclerViewAdapter.notifyDataSetChanged();
        movieRecyclerView.setAdapter(movieRecyclerViewAdapter);

        movieRecyclerViewAdapter.setOnItemClickCallback(new MovieRecyclerViewAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie movie) {
                showSelectedMovie(movie);
            }
        });
    }

    private void showSelectedMovie(Movie movie) {

        Intent moveWithObjectIntent = new Intent(getContext(), DetailActivity.class);
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
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
