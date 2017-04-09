package com.coursework.coursework3;

import android.Manifest;
import android.app.Fragment;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public final int CODE_PERMISSIONS = 1;
    public final String TAG = "IALOCATION";
    public TextView mTextView;
    private Location mLocation;
    private SupportMapFragment mMap;


    // Variables for Indoor Atlas
    public IALocationManager mLocationManager;
    public IALocationListener mLocationListener;
    public IALocationRequest mLocationRequest;

    // Variables for Firebase
    public FirebaseDatabase mDatabase;
    public DatabaseReference dbref;

    // List of points of interest.
    private Location[] mPOI = {
            new Location(51.522026, -0.130534, "Computer Thing",R.drawable.comp, 0.0f),
            new Location(51.522076, -0.130564, "Window", R.drawable.window, 0.0f),
            new Location(51.522100, -0.130464, "Entrance to room", R.drawable.door, 0.0f)
    };

    // list for keeping record of what is currently neary user location
    private ArrayList mNearby =  new ArrayList<String>();;
    // adapter to use to show use the nearby points of interest.
    private POIAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Example code taken from http://docs.indooratlas.com/android/dev-guide/getting-user-location.html. Start with getting access to sensors etc.
        String[] neededPermissions = {
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions( this, neededPermissions, CODE_PERMISSIONS );

        setContentView(R.layout.activity_main);

        // find and add text to the MapinfoFragment
        mTextView = (TextView) findViewById(R.id.location_output);
        mTextView.setText(R.string.searching);

        // First create a location manager.
        mLocationManager = IALocationManager.create(this);

        // Set up reference to Firebase.
        mDatabase = FirebaseDatabase.getInstance();

          // Get reference to the map fragement
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

          // Information for adaptors and lists from: https://developer.android.com/guide/topics/ui/declaring-layout.html#AdapterViews
        // http://stackoverflow.com/questions/4540754/dynamically-add-elements-to-a-listview-android

        adapter = new POIAdapter(this, mNearby);
        ListView t = (ListView) findViewById(R.id.poi_list);
        t.setAdapter(adapter);
    }

    @Override
    protected void onResume(){

        Log.d("RESUME","resumed");
        super.onResume();
        // set up the request and listener
        mLocationRequest = IALocationRequest.create();
        // 1 second update intervals
        mLocationRequest.setFastestInterval(1000);

        // the listener will check for location each second. It should then update firebase and check if there's a point of interest nearby.
        // if there is display the location to the use in the form of a list.
        mLocationListener = new IALocationListener() {
            @Override
            public void onLocationChanged(IALocation iaLocation) {
                // We have a new location.
                mLocation = new Location(iaLocation.getLatitude(), iaLocation.getLongitude(), "Your Location", R.mipmap.ic_launcher, iaLocation.getAccuracy());

                Log.d(TAG, "latitude " + iaLocation.getLatitude());
                Log.d(TAG, "latitude " + iaLocation.getLongitude());

                // print out locations information
                mTextView.setText(mLocation.getLocDescription() );


                // at this point we should log the local in firebase. Use current time as key.

                dbref = mDatabase.getReference("" + System.currentTimeMillis() + "");
                dbref.setValue(mLocation.getCurrentLocation());

                // and now update the map

                updateMap(mLocation);

                // Check if there's anything intersting nearby. if the is, show in MapInfoFragment.
                adapter.clear();

                for (Location l: mPOI
                     ) {

                    if (l.getDistance(mLocation) < 3){
                        // its point of interest. This will automatically update the  MapInfoFragment to tell the user.
                        adapter.add(l);
                    }
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
               Log.d("statchange", "" + i);
            }
        };

        // start asking for location updates.
        mLocationManager.requestLocationUpdates(mLocationRequest, mLocationListener );

    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeLocationUpdates(mLocationListener);
    }

    @Override
    protected void onDestroy() {
        mLocationManager.destroy();
        super.onDestroy();
    }

    public void updateMap(final Location loc){
        // get the map inside the fragement.
        mMap.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                GoogleMap m = googleMap;
                LatLng pos = new LatLng(loc.getCurrentLocation().get("lat"), loc.getCurrentLocation().get("lng"));
                // clear any exisitng markers etc.
                m.clear();

                // add a circle with radius based on accuracy measure from indoor atlas.
                m.addCircle(new CircleOptions().center(pos).radius(loc.getAccuracy()).fillColor(Color.argb(30,0,0,255)).strokeWidth(1).strokeColor(Color.GRAY));
                m.addMarker(new MarkerOptions().position(pos).title(loc.getLocDescription()));

                m.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,20));

            }
        });
    }

}


