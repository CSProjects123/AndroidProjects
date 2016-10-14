package com.edbono.www.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String data = getIntent().getExtras().getString("movie_name");
        String temp = "movie name: ";
        temp = temp + data;
        Log.v("movie name", temp);
    }
}
