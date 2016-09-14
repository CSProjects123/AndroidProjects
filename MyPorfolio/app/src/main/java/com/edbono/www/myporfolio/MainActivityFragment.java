package com.edbono.www.myporfolio;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener  {

    public MainActivityFragment() {
    }

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        Button button = (Button) view.findViewById(R.id.button);
        Button button2 = (Button) view.findViewById(R.id.button2);
        Button button3 = (Button) view.findViewById(R.id.button3);
        Button button4 = (Button) view.findViewById(R.id.button4);
        Button button5 = (Button) view.findViewById(R.id.button5);
        Button button6 = (Button) view.findViewById(R.id.button6);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        switch(v.getId()){
            case R.id.button:
                CharSequence text = "Launching: Popular Movies";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
            case R.id.button2:
                CharSequence text2 = "Launching: Stock Hawk";
                Toast toast2 = Toast.makeText(context, text2, duration);
                toast2.show();
                break;
            case R.id.button3:
                CharSequence text3 = "Launching: Build It Bigger";
                Toast toast3 = Toast.makeText(context, text3, duration);
                toast3.show();
                break;
            case R.id.button4:
                CharSequence text4 = "Launching: Make Your App Material";
                Toast toast4 = Toast.makeText(context, text4, duration);
                toast4.show();
                break;
            case R.id.button5:
                CharSequence text5 = "Launching: Go Ubiquitous";
                Toast toast5 = Toast.makeText(context, text5, duration);
                toast5.show();
                break;
            case R.id.button6:
                CharSequence text6 = "Launching: Capstone";
                Toast toast6 = Toast.makeText(context, text6, duration);
                toast6.show();
                break;
        }

    }



}
