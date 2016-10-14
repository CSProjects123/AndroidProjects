package com.edbono.www.popularmovies;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by mkothari on 10/13/16.
 */
public class SettingsActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.order)));
    }
    private void bindPreferenceSummaryToValue(Preference preference){

        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference, PreferenceManager
        .getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value){
        Log.v("generatedd", value.toString());
        String stringValue = value.toString();
        if (preference instanceof ListPreference){
            ListPreference listPreference = (ListPreference) preference;
            Log.v("GET_P", stringValue);
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if(prefIndex >=0){
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }

        }
        return true;
    }

}
