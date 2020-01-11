package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;
//    private ListView listView;
    private String[] movieTitle, movieDescription, movieRating, movieDate;
    private TypedArray movieCover;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);

//        listView = findViewById(R.id.movieListView);
        movieAdapter = new MovieAdapter(this);
//        listView.setAdapter(movieAdapter);

//        initializeListView();
//        addItemToListView();

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Movie movie = new Movie();
//                movie.setTitle(movieTitle[position]);
//                movie.setReleaseDate(movieDate[position]);
//                movie.setRating(movieRating[position]);
//                movie.setDescription(movieDescription[position]);
//                movie.setCover(movieCover.getResourceId(position, -1));
//
//                Intent moveWithObjectIntent = new Intent(MainActivity.this, DetailActivity.class);
//                moveWithObjectIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
//                startActivity(moveWithObjectIntent);
//            }
//        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.language_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}