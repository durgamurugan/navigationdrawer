
package com.example.navigationdrawer;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetDirectionsData extends AsyncTask<Object, String, String> {
    GoogleMap map;
    String url;
    String googleDirectionsData;
    String duration, distance;
    LatLng latLng;

    @Override
    protected String doInBackground(Object... objects) {
        map = (GoogleMap)objects[0];
        url = (String)objects[1];
        latLng = (LatLng)objects[2];

        DownloadUrl downloadUrl = new DownloadUrl();
        try {
            googleDirectionsData = downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleDirectionsData;
    }

    @Override
    protected void onPostExecute(String s) {
        HashMap<String,String> directionsList = null;
        DataParser parser = new DataParser();
        directionsList = parser.parseDirections(s);
        duration = directionsList.get("duration");
        distance = directionsList.get("distance");

        map.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);
        markerOptions.title("Duration="+duration);
        markerOptions.snippet("Distance="+distance);
        map.addMarker(markerOptions);
    }
}
