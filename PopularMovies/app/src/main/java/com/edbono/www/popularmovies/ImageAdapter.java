package com.edbono.www.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mkothari on 10/7/16.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    ArrayList<String> urlArrayList = new ArrayList<String>();

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public ImageAdapter(Context c, ArrayList<String> arrays){
        mContext = c;
        urlArrayList = arrays;
    }

    public int getCount() {
        return urlArrayList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    ArrayList<String> url_collection = new ArrayList<String>();

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(3, 3, 3, 3);
        } else {
            imageView = (ImageView) convertView;
        }
        Log.v("the first url", urlArrayList.get(2));
        Picasso.with(mContext).load(urlArrayList.get(position)).into(imageView);
        return imageView;
    }
}