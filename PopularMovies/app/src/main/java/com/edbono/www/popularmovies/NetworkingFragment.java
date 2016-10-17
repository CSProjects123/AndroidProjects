package com.edbono.www.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NetworkingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class NetworkingFragment extends Fragment {

    public NetworkingFragment() {
        // Required empty public constructor
    }

    public class NewClass{
        ArrayList<String> movieTitles;
        ArrayList<String> overview;
        ArrayList<String> release_date;
        ArrayList<String> vote_average;
        ArrayList<String> poster_ids;
        ArrayList<String> movie_id;


        public NewClass(){
            movieTitles = new ArrayList<String>();
            overview = new ArrayList<String>();
            release_date = new ArrayList<String>();
            vote_average = new ArrayList<String>();
            poster_ids = new ArrayList<String>();
            movie_id = new ArrayList<String>();

        }
    }

    static boolean order_popular_movies = true;

    @Override
    public void onStart(){
        super.onStart();
        updateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_networking, container, false);
        return fragmentView;

    }



    public class NetworkingTask extends AsyncTask<String, Void, String[]>{


        ArrayList<String> arrayListOfMovieTitles = new ArrayList<String>();
        NewClass n2 = new NewClass();


        String movieName = new String();

        private final String LOG_TAG = NetworkingTask.class.getSimpleName();

        String[] testStringArray = {"testString", "testString"};

        protected String[] doInBackground(String...params){

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            final String key = "239673e0d4f6d7699c13c4382188812e";
            String size = "w185";
            String movie_data_string = null;


            try{
                Log.v("being triggered", "being triggered");
                final String base_url = "http://api.themoviedb.org/3/movie";
                final String popular_movies_motive = "popular";
                final String top_rated_motive = "top_rated";
                String orderPref = "";
                if (order_popular_movies){
                    orderPref = popular_movies_motive;
                }else{
                    orderPref = top_rated_motive;
                }
                Uri builtUri = Uri.parse(base_url).buildUpon().appendPath(orderPref).appendQueryParameter("api_key", key).build();
                Log.v("builtUri", builtUri.toString());
                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movie_data_string = buffer.toString();
                Log.v("theJSON", movie_data_string);
                //ArrayList<String> posterIDs = posterIds(movie_data_string);


                NewClass newClass = getNewClass(movie_data_string);
                ArrayList<String> posterIDs = newClass.poster_ids;

                n2 = newClass;

                final String base_url_posters = "http://image.tmdb.org/t/p/w185";
                ArrayList<String> posterURLs = new ArrayList<String>();
                for (String s: posterIDs){
                    String posterUrl = base_url_posters+s;
                    posterURLs.add(posterUrl);
                    Log.v("URl", posterUrl);
                }
                String[] stringArray = posterURLs.toArray(new String[0]);
                return stringArray;

            } catch (IOException e){
                Log.e(LOG_TAG, "Error", e);
                return null;
            } finally {

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }

            }

        }




        @Override
        protected void onPostExecute(String[] strings){

            ArrayList<String> newArrayList = new ArrayList<String>();

            for (String s: strings){
                newArrayList.add(s);
           }
            GridView gridViewTrial = (GridView) getActivity().findViewById(R.id.gridviewtwo);
            ImageAdapter imageAdapter = new ImageAdapter(getActivity(), newArrayList);
            gridViewTrial.setAdapter(imageAdapter);
            gridViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                    //String movie_name = arrayListOfMovieTitles.get(position);
                    String movie_name = n2.movieTitles.get(position);
                    String overview = n2.overview.get(position);
                    String release_date = n2.release_date.get(position);
                    String vote_average = n2.vote_average.get(position);
                    String poster_id = n2.poster_ids.get(position);
                    String movie_id = n2.movie_id.get(position);

                    detailIntent.putExtra("movie_name", movie_name);
                    detailIntent.putExtra("overview", overview);
                    detailIntent.putExtra("release_date", release_date);
                    detailIntent.putExtra("vote_average", vote_average);
                    detailIntent.putExtra("poster_id", poster_id);
                    detailIntent.putExtra("movie_id", movie_id);

                    startActivity(detailIntent);

                }
            });

        }

    }


    private NewClass getNewClass(String json_string){

        NewClass n1 = new NewClass();


        try{
            JSONObject jObject = new JSONObject(json_string);

            JSONArray jArray = jObject.getJSONArray("results");
            for (int i=0; i<jArray.length(); i++){
                JSONObject jObj = jArray.getJSONObject(i);
                String original_title = jObj.getString("original_title");
                String release_date = jObj.getString("release_date");
                String vote_average = jObj.getString("vote_average");
                String overview = jObj.getString("overview");
                String poster_path = jObj.getString("poster_path");
                String movie_id = jObj.getString("id");

                if(original_title != null){
                    n1.movieTitles.add(original_title);
                }else{
                    n1.movieTitles.add("not found");
                }
                if(release_date != null){
                    n1.release_date.add(release_date);
                }else{
                    n1.release_date.add("not found");
                }
                if(vote_average != null){
                    n1.vote_average.add(vote_average);
                }else{
                    n1.overview.add("not found");
                }
                if(overview != null){
                    n1.overview.add(overview);
                }else{
                    n1.overview.add("not found");
                }
                if(poster_path != null){
                    n1.poster_ids.add(poster_path);
                }else{
                    n1.poster_ids.add("not found");
                }
                if(movie_id!= null){
                    n1.movie_id.add(movie_id);
                }else{
                    n1.movie_id.add("not found");
                }

            }

        }catch (JSONException j) {
            Log.e("posterIDFunction", j.getMessage(), j);
            j.printStackTrace();
        }

        return n1;
    }



    public void updateMovies(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String orderType = prefs.getString(getString(R.string.order), getString(R.string.pref_order_popular_movies));
        // send the order type to the NetworkingTask class
        if (!orderType.equals(getString(R.string.pref_order_popular_movies))){
            order_popular_movies = false;
        }else{
            order_popular_movies = true;
        }
        NetworkingTask updateTask = new NetworkingTask();
        updateTask.execute();
    }



}
