package app.com.example.android.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.v("oncreate triggered", "oncreate triggered");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onStart(){
        super.onStart();
        Log.v("onstart triggered", "onstart triggered");
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.v("onresume triggered", "onresume triggered");
    }


    @Override
    public void onPause(){
        super.onPause();
        Log.v("onpause triggered", "onpause triggered");
    }


    @Override
    public void onStop(){
        super.onStop();
        Log.v("onstop triggered", "onstop triggered");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.v("ondestroy triggered", "ondestroy triggered");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsFromMainIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsFromMainIntent);
            return true;
        }
        if (id == R.id.preferred_location){


            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String postalCode = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
            // two things we need to do. First, check if there is a MAPS  activity.
            // Second, pass in the postal code to be displayed on the maps
            // the first one is resolved by calling resolveActivity on intent
            // so first, create the intent
            // intent filters are for receiving
            //step one make the intent
            Uri mapUri = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q", postalCode).build();
            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
            mapIntent.setData(mapUri);
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                Log.v("map intent1234", "map intent1234");
                startActivity(mapIntent);
            }

        }
        return super.onOptionsItemSelected(item);
    }
}
