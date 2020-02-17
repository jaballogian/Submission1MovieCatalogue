package com.example.submission1moviecatalogue;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MainViewModel extends ViewModel {

    private static final String API_KEY = "58c087a2b6445fa861b67095aafefe77";
    private MutableLiveData<ArrayList<Movie>> listMovies = new MutableLiveData<>();
    private GetMoviesData getMoviesData;
    private String type, language, hint = "", link;
    final ArrayList<Movie> movies = new ArrayList<>();

    void setMovie (final String type, final String language) {

        this.type = type;
        this.language = language;
        getMoviesData = new GetMoviesData();
        getMoviesData.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    void setSearchMovie (final String type, final String language, final String hint){

        this.type = type;
        this.language = language;
        this.hint = hint;
        getMoviesData = new GetMoviesData();
        getMoviesData.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    LiveData<ArrayList<Movie>> getMovies() {
        return listMovies;
    }

    private class GetMoviesData extends AsyncTask<Void,Void,Void> {

        String result ="";
        static final String LOG_ASYNC = "DemoAsync";
        int totalMovies;

        @Override
        protected Void doInBackground(Void... voids) {

            if(hint.isEmpty()){
                link = "https://api.themoviedb.org/3/discover/" + type + "?api_key=" + API_KEY + "&language=" + language;
            }
            else {
                link = "https://api.themoviedb.org/3/search/" + type + "?api_key=" + API_KEY + "&language=" + language + "&query=" + hint;
            }

            try {
                URL url = new URL(link);
                Log.d("url", url.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line != null){
                    line = bufferedReader.readLine();
                    result = result + line;
                }
                Log.d("result", result);

                totalMovies = 0;

                JSONObject responseObject = new JSONObject(result);
                Log.d("responseObject", responseObject.toString());
                JSONArray resultsArray = responseObject.getJSONArray("results");
                Log.d("resultsObject", resultsArray.toString());

                for(int i = 0; i < resultsArray.length(); i++){

                    JSONObject JO = (JSONObject) resultsArray.get(i);

                    Movie movie= new Movie();

                    if(type.equals("movie")){

                        movie.setTitle(JO.get("title").toString());
                        movie.setReleaseDate(JO.get("release_date").toString());
                        movie.setDescription(JO.get("overview").toString());
                        movie.setRating(JO.get("vote_average").toString());
                        String imageURL = "https://image.tmdb.org/t/p/w185" + JO.get("poster_path").toString();
                        movie.setCover(imageURL);
                    }
                    else {

                        movie.setTitle(JO.get("original_name").toString());
                        movie.setReleaseDate(JO.get("first_air_date").toString());
                        movie.setDescription(JO.get("overview").toString());
                        movie.setRating(JO.get("vote_average").toString());
                        String imageURL = "https://image.tmdb.org/t/p/w185" + JO.get("poster_path").toString();
                        movie.setCover(imageURL);
                    }

                    movies.add(movie);
                    totalMovies++;
                }
                Log.d("movies_inViewModel", movies.toString());

                listMovies.postValue(movies);
                Log.d("listMovies", listMovies.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("U MalformedURLException", "U MalformedURLException ");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("U IOException", "U IOException");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("U JSONException", "U JSONException ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

    }
}
