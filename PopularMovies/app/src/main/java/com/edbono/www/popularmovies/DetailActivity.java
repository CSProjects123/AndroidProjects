package com.edbono.www.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String d1 = getIntent().getExtras().getString("movie_name");
        String d2 = getIntent().getExtras().getString("release_date");
        String d3 = getIntent().getExtras().getString("overview");
        String d4 = getIntent().getExtras().getString("vote_average");

        Log.v("TRANSFERED--", d1);
        Log.v("TRANSFERED--", d2);
        Log.v("TRANSFERED--", d3);
        Log.v("TRANSFERED--", d4);




    }
}
