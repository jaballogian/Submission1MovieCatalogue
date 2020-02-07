package com.example.submission1moviecatalogue;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVShowsFragment extends Fragment implements LoadMoviesCallbackTV{

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private MovieHelperTV movieHelperTV;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    public FavoriteTVShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_favorite_tvshows, container, false);

        recyclerView = view.findViewById(R.id.favoriteMoviesRecylerViewFragmentFavoriteTVShows);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        favoriteAdapter = new FavoriteAdapter(getActivity());
        recyclerView.setAdapter(favoriteAdapter);

        movieHelperTV = movieHelperTV.getInstance(getContext());
        movieHelperTV.open();

        new FavoriteTVShowsFragment.LoadNotesAsync(movieHelperTV, this).execute();

        if (savedInstanceState == null) {
            // proses ambil data
            new FavoriteTVShowsFragment.LoadNotesAsync(movieHelperTV, this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favoriteAdapter.setListFavorite(list);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favoriteAdapter.getListFavorite());
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
    @Override
    public void postExecute(ArrayList<Movie> movies) {

        if (movies.size() > 0) {
            favoriteAdapter.setListFavorite(movies);
        } else {
            favoriteAdapter.setListFavorite(new ArrayList<Movie>());
        }
    }

    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<Movie>> {

        private final WeakReference<MovieHelperTV> weakNoteHelper;
        private final WeakReference<LoadMoviesCallbackTV> weakCallback;

        private LoadNotesAsync(MovieHelperTV movieHelperTV, LoadMoviesCallbackTV loadMoviesCallbackTV) {
            weakNoteHelper = new WeakReference<>(movieHelperTV);
            weakCallback = new WeakReference<>(loadMoviesCallbackTV);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }
        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            Cursor dataCursor = weakNoteHelper.get().queryAll();
            return MappingHelper.mapCursorToArrayList(dataCursor);
        }
        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null) {
//            // Akan dipanggil jika request codenya ADD
//            if (requestCode == REQUEST_ADD) {
//                if (resultCode == RESULT_ADD) {
//                    Movie movie = data.getParcelableExtra(EXTRA_MOVIE);
//                    favoriteAdapter.addItem(movie);
//                    recyclerView.smoothScrollToPosition(favoriteAdapter.getItemCount() - 1);
////                    showSnackbarMessage("Satu item berhasil ditambahkan");
//                }
//            }
//            // Update dan Delete memiliki request code sama akan tetapi result codenya berbeda
//            else if (requestCode == REQUEST_UPDATE) {
//                if (resultCode == RESULT_UPDATE) {
//                    Movie movie = data.getParcelableExtra(EXTRA_MOVIE);
//                    int position = data.getIntExtra(EXTRA_POSITION, 0);
//                    favoriteAdapter.updateItem(position, movie);
//                    recyclerView.smoothScrollToPosition(position);
////                    showSnackbarMessage("Satu item berhasil diubah");
//                }
//                else if (resultCode == RESULT_DELETE) {
//                    int position = data.getIntExtra(EXTRA_POSITION, 0);
//                    favoriteAdapter.removeItem(position);
////                    showSnackbarMessage("Satu item berhasil dihapus");
//                }
//            }
//        }
//    }

}

interface LoadMoviesCallbackTV {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);

//    void onActivityResult(int requestCode, int resultCode, Intent data);
}
