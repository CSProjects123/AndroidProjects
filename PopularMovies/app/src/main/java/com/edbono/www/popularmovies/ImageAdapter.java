package com.edbono.www.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by mkothari on 10/7/16.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    ArrayList<String> url_collection = new ArrayList<String>();

    public void setUrlCollection(ArrayList<String> arr){
        url_collection = arr;

        for (String s:url_collection){
            Log.v("urlcollection", s);
        }
        Log.v("THESIZEARR", Integer.toString(arr.size()));


    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            //containerView =  (ImageView) LayoutInflater.from(mContext).inflate(R.layout.item_movie_grid, parent, false);
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        //Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/ghL4ub6vwbYShlqCFHpoIRwx2sm.jpg").into(imageView);
        //Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/6FxOPJ9Ysilpq0IgkrMJ7PubFhq.jpg").into(imageView);
        return imageView;
    }


    // references to our    images
    private Integer[] mThumbIds = {
            R.mipmap.me, R.mipmap.dog,
            R.mipmap.me, R.mipmap.dog,
            R.mipmap.me, R.mipmap.dog,
            R.mipmap.me, R.mipmap.dog,
            R.mipmap.me, R.mipmap.dog,
            R.mipmap.interstellar, R.mipmap.interstellar,

    };
}