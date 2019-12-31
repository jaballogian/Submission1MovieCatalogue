package com.example.submission1moviecatalogue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(itemView);
        Movie movie = (Movie) getItem(position);
        viewHolder.bind(movie);

        return itemView;
    }

    private class ViewHolder{

        private TextView movieTitle, movieDescription, movieRating, movieDate;
        private ImageView movieCover;

        ViewHolder(View view) {

            movieTitle = view.findViewById(R.id.movieTitleTextView);
            movieDescription = view.findViewById(R.id.movieDescriptionTextView);
            movieRating = view.findViewById(R.id.movieRatingTextView);
            movieDate= view.findViewById(R.id.movieDateTextView);
            movieCover = view.findViewById(R.id.movieCoverImageView);
        }
        void bind(Movie movie) {

            movieTitle.setText(movie.getTitle());
            movieDescription.setText(movie.getDescription());
            movieRating.setText(movie.getRating());
            movieDate.setText(movie.getReleaseDate());
            movieCover.setImageResource(movie.getCover());
        }
    }
}
