package com.edbono.www.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public void onStart(){
        super.onStart();
        updateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout f^or this fragment
        return inflater.inflate(R.layout.fragment_networking, container, false);

    }

    public class NetworkingTask extends AsyncTask<String, Void, String[]>{

        private final String LOG_TAG = NetworkingTask.class.getSimpleName();

        String[] testStringArray = {"testString", "testString"};

        protected String[] doInBackground(String...params){

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            final String key = "239673e0d4f6d7699c13c4382188****";
            String size = "w185";
            String movie_data_string = null;


            try{
                final String base_url = "http://api.themoviedb.org/3/movie";
                final String popular_movies_motive = "popular";
                final String top_rated_motive = "top_rated";
                Uri builtUri = Uri.parse(base_url).buildUpon().appendPath(popular_movies_motive).appendQueryParameter("api_key", key).build();
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
                Log.v("JSON movie data", movie_data_string);
                // call a function to parse the JSON and get back the array of posterIds
                ArrayList<String> posterIDs = posterIds(movie_data_string);
                for (String s:posterIDs){
                    Log.v("posterlist", s);
                }
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

            return null;
        }

    }

    private ArrayList<String> posterIds(String json_string){

        ArrayList<String> arrayListOfPosters = new ArrayList<String>();
        Log.v("json string", json_string);
        try{
            JSONObject jObject = new JSONObject(json_string);

            JSONArray jArray = jObject.getJSONArray("results");
            Log.v("json array", jArray.toString());
            // iterate over the values of the array
            Log.v("length of json array", Integer.toString(jArray.length()));
            for (int i=0; i<jArray.length(); i++){
                // get the json object at the index i
                JSONObject jObj = jArray.getJSONObject(i);
                // for this json object, get the poster ID string and add it to the arrayList
                String posterID = jObj.getString("poster_path");
                Log.v("posting", posterID);
                if(posterID != null){
                    Log.v("notNull", "notNull");
                   arrayListOfPosters.add(posterID);
                }
            }

        }catch (JSONException j) {
            Log.e("posterIDFunction", j.getMessage(), j);
            j.printStackTrace();
        }
        return arrayListOfPosters;
    }


    private void updateMovies(){
        NetworkingTask updateTask = new NetworkingTask();
        updateTask.execute();

    }

}
