package com.xkoders.zuncallandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.xkoders.zuncallandroid.R;
import com.xkoders.zuncallandroid.constants.SHARED_PREF_IDS;
import com.xkoders.zuncallandroid.constants.URLS;
import com.xkoders.zuncallandroid.utils.LocalPreferences;


public class Preferences extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);



        /*Preference pref = (Preference) findPreference("languageName");
        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent mIntent = preference.getIntent();
                startActivityForResult(preference.getIntent(), 1);
                return true;
            }
        });*/

        Preference serverPref = findPreference(SHARED_PREF_IDS.API_URL);
        serverPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });
        Preference methodGetAllPeoplesPref = findPreference(SHARED_PREF_IDS.GET_ALL_CALLS);
        methodGetAllPeoplesPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary((String) newValue);
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                Toast.makeText(getApplicationContext(), data.getExtras().getString("languageName"), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        Preference p = null;
        String value = "";
        switch (key) {
            case SHARED_PREF_IDS.API_URL:
                value = sp.getString(key, URLS.API_URL);
                p = findPreference(key);
                p.setSummary(value);
                LocalPreferences.saveApiUrl(getApplicationContext(), key, value);
                break;

            case SHARED_PREF_IDS.GET_ALL_CALLS:
                value = sp.getString(key, URLS.GET_ALL_CALLS);
                p = findPreference(key);
                p.setSummary(value);
                LocalPreferences.saveGetAllCalls(getApplicationContext(), key, value);
                break;

            case SHARED_PREF_IDS.STORE_CALLS:
                boolean flag = sp.getBoolean(key, true);
                p = findPreference(key);
                LocalPreferences.setCachePreferred(getApplicationContext(), key, true);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        Preference p = findPreference(SHARED_PREF_IDS.API_URL);
        p.setSummary(sp.getString(SHARED_PREF_IDS.API_URL, URLS.API_URL));

        p = findPreference(SHARED_PREF_IDS.GET_ALL_CALLS);
        p.setSummary(sp.getString(SHARED_PREF_IDS.GET_ALL_CALLS, URLS.GET_ALL_CALLS));

    }

    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


}
