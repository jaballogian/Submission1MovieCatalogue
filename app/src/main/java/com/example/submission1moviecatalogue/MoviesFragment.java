
package com.example.submission1moviecatalogue;


import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private RecyclerView movieRecyclerView;
    private ArrayList<Movie> movies = new ArrayList<>();

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_movies, container, false);

        movieRecyclerView = (RecyclerView) view.findViewById(R.id.moviesRecylerView);
        movieRecyclerView.setHasFixedSize(true);

        movies.addAll(getListHeroes());
        showRecyclerList();

        return view;
    }

    public ArrayList<Movie> getListHeroes() {

        String[] title = getResources().getStringArray(R.array.movieTitle);
        String[] description = getResources().getStringArray(R.array.movieDescription);
        String[] releaseDate = getResources().getStringArray(R.array.movieDate);
        String[] rating = getResources().getStringArray(R.array.movieRating);
        TypedArray cover = getResources().obtainTypedArray(R.array.movieCover);
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            Movie movie = new Movie();
            movie.setTitle(title[i]);
            movie.setDescription(description[i]);
            movie.setReleaseDate(releaseDate[i]);
            movie.setRating(rating[i]);
            movie.setCover(cover.getResourceId(i, -1));
            movieArrayList.add(movie);
        }
        return movieArrayList;
    }

    private void showRecyclerList(){
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(movies);
        movieRecyclerView.setAdapter(movieRecyclerViewAdapter);
    }

}
