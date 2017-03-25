package com.coursework.coursework3;

import android.Manifest;
import android.app.Fragment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public final int CODE_PERMISSIONS = 1;
    public final String TAG = "IALOCATION";
    public TextView mTextView;
    private MyLocation mLocation;
    private SupportMapFragment mMap;

    public IALocationManager mLocationManager;
    public IALocationListener mLocationListener;
    public IALocationRequest mLocationRequest;

    public FirebaseDatabase mDatabase;
    public DatabaseReference dbref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Code taken from http://docs.indooratlas.com/android/dev-guide/getting-user-location.html. Start with getting access to sensors etc.
        String[] neededPermissions = {
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions( this, neededPermissions, CODE_PERMISSIONS );

        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.location_output);
        mTextView.setText(R.string.test);

        // First create a location manager.
        mLocationManager = IALocationManager.create(this);
        mDatabase = FirebaseDatabase.getInstance();

          // Get reference to the map fragement
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));

        updateMap(-34.0, 151.0);


    }

    @Override
    protected void onResume(){

        Log.d("RESUME","resumed");
        super.onResume();
        // set up the request and listener
        mLocationRequest = IALocationRequest.create();
        mLocationRequest.setFastestInterval(1000);


        mLocationListener = new IALocationListener() {
            @Override
            public void onLocationChanged(IALocation iaLocation) {

                mLocation = new MyLocation(iaLocation.getLatitude(), iaLocation.getLongitude());

                Log.d(TAG, "latitude " + iaLocation.getLatitude());
                Log.d(TAG, "latitude " + iaLocation.getLongitude());

                // at this point we should log the local in firebase...

                dbref = mDatabase.getReference("" + System.currentTimeMillis() + "");
                dbref.setValue(mLocation.getCurrentLocation());

                // and now update the map

                updateMap(iaLocation.getLatitude(), iaLocation.getLongitude());

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                dbref = mDatabase.getReference("" + System.currentTimeMillis() + "");
                dbref.setValue("" + i);
                mTextView.setText("" + i);
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

    public void updateMap(final Double lat, final Double lng){
        // get the map inside the fragement.
        mMap.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                GoogleMap m = googleMap;
                LatLng sydney = new LatLng(lat, lng);
                m.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                m.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        });
    }

}


