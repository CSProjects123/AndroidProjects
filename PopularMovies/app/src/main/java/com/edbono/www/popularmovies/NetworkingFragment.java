package com.edbono.www.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NetworkingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class NetworkingFragment extends Fragment {

    public NetworkingFragment() {
        Log.v("AAAAA", "AAAAA");
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("abcdef", "abcdef");
        return inflater.inflate(R.layout.fragment_networking, container, false);

    }

    public class NetworkingTask extends AsyncTask<String, Void, String[]>{

        String[] testStringArray = {"testString", "testString"};

        protected String[] doInBackground(String...params){

            return testStringArray;
        }

    }



}
