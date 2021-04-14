package com.example.navigationdrawer.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.navigationdrawer.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener,
        View.OnClickListener {

//    private MapViewModel mapViewModel;
    GoogleMap map;
    GoogleApiClient mGoogleApiclient;
    LocationRequest locationRequest;
    Location lastlocation;
    Marker currentLocationMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double longitude, latitude;
    double end_latitude, end_longitude;
    SearchView searchView;
    TimePicker timePicker;
    TextView textView;
    int mHour,mMin;
    private AdView mAdView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = v.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


//        mapViewModel =
//                ViewModelProviders.of(this).get(MapViewModel.class);

//        final TextView textView = root.findViewById(R.id.map);
//        mapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        Button b_to = (Button) v.findViewById(R.id.B_to);
        b_to.setOnClickListener(this);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            checkLocationPermission();
        }
        searchView = view.findViewById(R.id.sv_location);
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                MarkerOptions markerOptions = new MarkerOptions();

                if (!location.equals("")) {
                    Geocoder geocoder = new Geocoder(getActivity());
                    try {
                        addressList = geocoder.getFromLocationName(location, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < addressList.size(); i++) {
                        Address myAddress = addressList.get(i);
                        LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
                        markerOptions.position(latLng);
                        markerOptions.title("Your Search");
                        map.addMarker(markerOptions);
                        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission is granted
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiclient == null) {
                            buildGoogleApiClient();
                        }
                        map.setMyLocationEnabled(true);
                    }
                } else //permission denied
                {
                    Toast.makeText(getActivity(), "Permission Denied!", Toast.LENGTH_LONG).show();
                }
                return;
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                map.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            map.setMyLocationEnabled(true);
        }
        map.setOnMarkerDragListener(this);
        map.setOnMarkerClickListener(this);
    }


    //        LatLng pp = new LatLng(11.5448729,104.8921668);
//        MarkerOptions option = new MarkerOptions();
//        option.position(pp).title("current position");
//        map.addMarker(option);
//        map.moveCamera(CameraUpdateFactory.newLatLng(pp));


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.B_to:
                map.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(end_latitude,end_longitude));
                markerOptions.title("Destination");
                float results[] = new float[10];
                Location.distanceBetween(latitude,longitude,end_latitude,end_longitude,results);
                markerOptions.snippet("Distance = "+results[0]);
                map.addMarker(markerOptions);
//                Object dataTransfer[] = new Object[3];
//                String url = getDirectionsUrl();
//                GetDirectionsData getDirectionsData = new GetDirectionsData();
//                dataTransfer[0] = map;
//                dataTransfer[1] = url;
//                dataTransfer[2] = new LatLng(end_latitude, end_longitude);
//                getDirectionsData.execute(dataTransfer);
                break;
        }
    }

//    private String getDirectionsUrl()
//    {
//        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
//        googleDirectionsUrl.append("origin="+latitude+","+longitude);
//        googleDirectionsUrl.append("&destination="+end_latitude+","+end_longitude);
//        googleDirectionsUrl.append("&key="+"AIzaSyBlxOnh1Ng_tGYvbb5oj59qM0bxl_G_ysE");
//        return googleDirectionsUrl.toString();
//    }



    protected synchronized void buildGoogleApiClient ()
    {
        mGoogleApiclient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiclient.connect();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiclient, locationRequest, this);

        }

    }

    public boolean checkLocationPermission () {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;
        } else
            return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lastlocation = location;

        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        currentLocationMarker = map.addMarker(markerOptions);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomBy(10));

        if (mGoogleApiclient != null) {

            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiclient, this);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.setDraggable(true);
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        end_latitude = marker.getPosition().latitude;
        end_longitude = marker.getPosition().longitude;
    }
}
