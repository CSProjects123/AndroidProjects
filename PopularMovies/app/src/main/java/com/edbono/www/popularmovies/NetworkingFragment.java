package com.edbono.www.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_networking, container, false);
        return fragmentView;

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
                // call a function to parse the JSON and get back the array of posterIds
                ArrayList<String> posterIDs = posterIds(movie_data_string);
                for (String s:posterIDs){
                }
                // I now want to make a ArrayList of string URLs for the posters.
                final String base_url_posters = "http://image.tmdb.org/t/p/w185";
                ArrayList<String> posterURLs = new ArrayList<String>();
                for (String s: posterIDs){
                    String posterUrl = base_url_posters+s;
                    posterURLs.add(posterUrl);
                }
                //converting the ArrayList into an array of strings and returning it.
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
            String y = getActivity().getLocalClassName();
            Log.v("classname", y);
            ((MainActivity)getActivity()).setUrlArrayList(newArrayList);
            GridView gridViewTrial = (GridView) getActivity().findViewById(R.id.gridviewtwo);
            ImageAdapter imageAdapter = new ImageAdapter(getActivity(), newArrayList);
            gridViewTrial.setAdapter(imageAdapter);

            gridViewTrial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), "mymymy", Toast.LENGTH_SHORT).show();

                }
            });



        }

    }

    private ArrayList<String> posterIds(String json_string){

        ArrayList<String> arrayListOfPosters = new ArrayList<String>();
        try{
            JSONObject jObject = new JSONObject(json_string);

            JSONArray jArray = jObject.getJSONArray("results");
            for (int i=0; i<jArray.length(); i++){
                JSONObject jObj = jArray.getJSONObject(i);
                String posterID = jObj.getString("poster_path");
                if(posterID != null){
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
