package com.edbono.www.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

public class DetailActivity extends AppCompatActivity {

    int numberOfTrailers = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        String d1 = getIntent().getExtras().getString("movie_name");
        String d2 = getIntent().getExtras().getString("release_date");
        String d3 = getIntent().getExtras().getString("overview");
        String d4 = getIntent().getExtras().getString("vote_average");
        String d5 = getIntent().getExtras().getString("poster_id");
        String movie_id = getIntent().getExtras().getString("movie_id");

        String base_url = "http://image.tmdb.org/t/p/w185";
        String poster_url = base_url + d5;

        ImageView imageView = (ImageView)findViewById(R.id.imageViewForPoster);
        Picasso.with(this).load(poster_url).into(imageView);
        TextView textViewTitle = (TextView)findViewById(R.id.textViewForPosterTitle);
        TextView textViewOverview = (TextView)findViewById(R.id.textViewForPosterOverview);
        TextView textViewReleaseDate = (TextView)findViewById(R.id.textViewForPosterReleaseDate);
        TextView textViewVoterRating = (TextView)findViewById(R.id.textViewForPosterVoterRating);
        textViewTitle.setText(d1);
        textViewOverview.setText(d3);
        textViewReleaseDate.setText(d2);
        textViewVoterRating.setText(d4);

        FetchURLKey fetchURLKey = new FetchURLKey();
        fetchURLKey.execute(movie_id);

        FetchReview fetchReview = new FetchReview();
        fetchReview.execute(movie_id);

    }




    public class FetchURLKey extends AsyncTask<String, Void, String>{


        private final String LOG_TAG = FetchURLKey.class.getSimpleName();


        protected String doInBackground(String... params){

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            final String key = "239673e0d4f6d7699c13c4382188812e";
            String size = "w185";
            String data_with_video_id = new String();
            try{

                String movieID = params[0];
                final String base_url = "http://api.themoviedb.org/3/movie";
                Uri builtUri = Uri.parse(base_url).buildUpon().appendPath(movieID).appendPath("videos")
                        .appendQueryParameter("api_key", key).build();
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
                data_with_video_id = buffer.toString();
                return data_with_video_id;


            }catch(IOException e){
                Log.e(LOG_TAG, "Error", e);
                return null;
            }finally {

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

        protected void onPostExecute(String string){

            String dataWithMovieID = "movie with data id: " + string;
            Log.v("Movie data ID", dataWithMovieID);
            ArrayList<String> movieIDs = new ArrayList<String>();
            movieIDs = arrayListOfMovieIds(string);
            String movieTrailerURl = "http://www.youtube.com/watch?v=";
            ArrayList<String> fullTrailerUrls = new ArrayList<String>();

            for (String s: movieIDs){
                String trailerUrl = movieTrailerURl + s;
                fullTrailerUrls.add(trailerUrl);
                Log.v("YOUTUBEURL", (movieTrailerURl+s));
            }

            numberOfTrailers = fullTrailerUrls.size();
            String thesize = "thesize";
            thesize = thesize + Integer.toString(numberOfTrailers);
            Log.v("thesize", thesize);

            addButtons(fullTrailerUrls);

        }

        public void addButtons(final ArrayList<String> arrayListOfTrailerUrls){

            LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout);

            for (int i=0; i<arrayListOfTrailerUrls.size(); i++){

                final int temp_i = i;

                String trailer_number = "tariler: " + Integer.toString(i+1);
                Button bt = new Button(DetailActivity.this);
                bt.setText(trailer_number);
                layout.addView(bt);
                bt.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // in this step we should be creating a new intent to
                        // transfer to a new screen - youtube or browser
                        Intent videoPlayerIntent = new Intent(Intent.ACTION_VIEW);
                        String videoURLstring = "http://www.youtube.com/watch?v=" +  arrayListOfTrailerUrls.get(temp_i);
                        Uri videoUri = Uri.parse(videoURLstring);
                        videoPlayerIntent.setData(Uri.parse(arrayListOfTrailerUrls.get(temp_i)));
                        if (videoPlayerIntent.resolveActivity(getPackageManager()) != null){
                            Log.v("videoPlayerIntent", "videoPlayerIntent");
                            startActivity(videoPlayerIntent);
                        }
                    }
                });


            }

        }




        protected ArrayList<String> arrayListOfMovieIds(String string){
            ArrayList<String> parsedList = new ArrayList<String>();
            try{
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jArray = jsonObject.getJSONArray("results");
                for (int i=0; i<jArray.length(); i++){
                    JSONObject jObj = jArray.getJSONObject(i);
                    String key = jObj.getString("key");
                    if(key != null){
                        parsedList.add(key);
                    }else{
                        parsedList.add("key not found");
                    }
                }


            }catch(JSONException j){
                Log.e("arrayListOfMovieIds", j.getMessage(), j);
                j.printStackTrace();
            }
            return parsedList;

        }
    }




    public class FetchReview extends AsyncTask<String, Void, String>{

        private final String LOG_TAG = FetchReview.class.getSimpleName();

        protected String doInBackground(String... params){

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            final String key = "239673e0d4f6d7699c13c4382188812e";
            String data_with_reviews;
            try{
                Log.v("try Block reached", "did we even reach teh Try block?");

                String MovieID = params[0];
                final String base_url = "http://api.themoviedb.org/3/movie";
                Uri builtUri = Uri.parse(base_url).buildUpon().appendPath(MovieID).appendPath("reviews")
                        .appendQueryParameter("api_key", key).build();
                URL url = new URL(builtUri.toString());
                String stringReviewData = "json reviews address -> " + builtUri.toString();
                Log.v("reviews", stringReviewData);
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
                data_with_reviews = buffer.toString();
                return data_with_reviews;


            }catch(IOException e){
                Log.e(LOG_TAG, "Error", e);
                return null;
            }finally {

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

        protected void onPostExecute(String string){
            String json_movie_data = "json movie data" + string;
            Log.v("json movie data--> ", json_movie_data);
            final ArrayList<String> arrayListReviews = new ArrayList<String>();

            try{
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jArray = jsonObject.getJSONArray("results");
                // Let make an arrayList of all the reviews for this movie
                //ArrayList<String> arrayListReviews = new ArrayList<String>();

                for (int i=0; i<jArray.length(); i++){
                    Log.v("wtf", "wtf");
                    JSONObject jObj = jArray.getJSONObject(i);
                    String content = jObj.getString("content");
                    if(content != null){
                        arrayListReviews.add(content);
                        String modifiedContent = "modified content" + content;
                        Log.v("content", modifiedContent);

                    }else{
                        Log.v("content", "returned null");
                    }
                }

                // now that we have the reviews, we want to display them to the user in
                // a different activity by adding a button to take us there.

                LinearLayout layout = (LinearLayout) findViewById(R.id.linear_layout);
                Button read_reviews_button = new Button(DetailActivity.this);
                read_reviews_button.setText("Read Reviews");
                layout.addView(read_reviews_button);
                read_reviews_button.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // over hear I want to explicitly move to another activity
                        Intent reviewsIntent = new Intent(DetailActivity.this, ReviewsActivity.class);
                        reviewsIntent.putStringArrayListExtra("reviews_arrayList", arrayListReviews);
                        startActivity(reviewsIntent);

                    }
                });
            }catch(JSONException j){
                Log.e("arrayListOfMovieIds", j.getMessage(), j);
                j.printStackTrace();
            }


        }

    }
}
