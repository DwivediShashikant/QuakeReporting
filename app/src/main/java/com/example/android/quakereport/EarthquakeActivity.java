/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.android.quakereport.arrayList.earthquakes;

public class EarthquakeActivity extends AppCompatActivity {
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";
    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Create a fake list of earthquake locations.
        ArrayList<quake> earthquakes = arrayList.earthquakes;
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);

        if(earthquakes.size()== 0){
            Log.w(LOG_TAG,"empty");
        }

    }
    public class EarthquakeAsyncTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... urls) {
            Utils.fetchEarthQuake(urls[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void v){
            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            // Create a new {@link quakeAdapter} of earthquakes
            quakeAdapter adapter = new quakeAdapter(EarthquakeActivity.this, earthquakes,0);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);
        }

    }

}
