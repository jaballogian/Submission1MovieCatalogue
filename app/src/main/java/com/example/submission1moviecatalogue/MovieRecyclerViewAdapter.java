package com.example.submission1moviecatalogue;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ListViewHolder> {

    private ArrayList<Movie> movieList;
    private OnItemClickCallback onItemClickCallback;

    public void setData(ArrayList<Movie> items) {
        movieList.clear();
        movieList.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(Movie movie);
    }

    public MovieRecyclerViewAdapter(ArrayList<Movie> list) {
        this.movieList = list;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycleview_fragment_movies, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
//        Movie movie= movieList.get(position);
//        Glide.with(holder.itemView.getContext())
//                .load(movie.getCover())
////                .apply(new RequestOptions().override(55, 55))
//                .into(holder.movieCoverImageView);
////        holder.movieCoverImageView.setImageResource(movie.getCover());
//        holder.movieTitleTextView.setText(movie.getTitle());
//        holder.movieDateTextView.setText(movie.getReleaseDate());
//        holder.movieRatingTextView.setText(movie.getRating());
//        holder.movieDescriptionTextView.setText(movie.getDescription());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onItemClickCallback.onItemClicked(movieList.get(holder.getAdapterPosition()));
//            }
//        });
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        ImageView movieCoverImageView;
        TextView movieTitleTextView, movieDateTextView, movieRatingTextView, movieDescriptionTextView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            movieCoverImageView = itemView.findViewById(R.id.movieCoverImageView);
            movieTitleTextView = itemView.findViewById(R.id.movieTitleTextView);
            movieDateTextView = itemView.findViewById(R.id.movieDateTextView);
            movieRatingTextView = itemView.findViewById(R.id.movieRatingTextView);
            movieDescriptionTextView = itemView.findViewById(R.id.movieDescriptionTextView);
        }

        void bind(Movie movie) {
            movieTitleTextView.setText(movie.getTitle());
            movieDateTextView.setText(movie.getReleaseDate());
            movieRatingTextView.setText(movie.getRating());
            movieDescriptionTextView.setText(movie.getDescription());
            Glide.with(itemView.getContext())
                .load(movie.getCover())
//                .apply(new RequestOptions().override(55, 55))
                .into(movieCoverImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(movieList.get(getAdapterPosition()));
                }
            });
        }
    }
}
