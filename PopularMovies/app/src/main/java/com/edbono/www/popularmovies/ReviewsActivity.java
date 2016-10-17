package com.edbono.www.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        ArrayList<String> arr = getIntent().getStringArrayListExtra("reviews_arrayList");
        String sizee = "the size is: " + Integer.toString(arr.size());
        Log.v("The size is ", sizee);

        LinearLayout layout = (LinearLayout)findViewById(R.id.reviews_layout);

        //
        for (int i=0; i<arr.size(); i++){
            // create a text view
            TextView textView = new TextView(ReviewsActivity.this);
            textView.setText(arr.get(i));
            layout.addView(textView);

        }




    }
}
