package com.edbono.www.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String d1 = getIntent().getExtras().getString("movie_name");
        String d2 = getIntent().getExtras().getString("release_date");
        String d3 = getIntent().getExtras().getString("overview");
        String d4 = getIntent().getExtras().getString("vote_average");
        String d5 = getIntent().getExtras().getString("poster_id");

        //what's remaining is to display all of this to the user , and to also d
        //get and display the image.
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





    }
}
