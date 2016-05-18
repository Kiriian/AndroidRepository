package com.datasteffen.testandroidgps;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.Handler;

public class gpsLocation extends AppCompatActivity implements LocationListener {

    TextView longituview;
    TextView laditudeview;
    Button button2;
    private LocationManager locationManager;
    private final static int DISTANCE_UPDATE = 1;
    private final static int TIME_UPDATE = 5;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private boolean LocationAvailable;
    public Location loc;
    boolean k = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_location);

        LocationAvailable = false;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkPermission()){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,TIME_UPDATE,DISTANCE_UPDATE,this);
        }else{
            requestPermissions();
        }

        button2 = (Button)findViewById(R.id.button2);
        button2.setEnabled(false);
        button2.setText("Waiting for gps signal");


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(gpsLocation.this, MapsActivity.class);
                i.putExtra("LOCATION", loc);
                startActivity(i);

            }
        });
 }

    private boolean checkPermission(){

        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(result == PackageManager.PERMISSION_GRANTED){
            LocationAvailable = true;
            return true;
        }else{
            LocationAvailable = false;
            return false;
        }
    }

    public void requestPermissions(){

        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this,"Please activate your GPS, This App use your location",Toast.LENGTH_LONG).show();
        }else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onLocationChanged(final Location location) {

        loc = location;
        laditudeview = (TextView) findViewById(R.id.latitude);
        longituview = (TextView) findViewById(R.id.longitude);

        button2.setBackgroundColor(Color.GREEN);
        button2.setText("Ready to search");
        button2.setEnabled(true);

        laditudeview.setText(String.valueOf(location.getLatitude()));
        longituview.setText(String.valueOf(location.getLongitude()));

       } ;

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        if (checkPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_UPDATE, DISTANCE_UPDATE, this);
        }else{requestPermissions();}

    }

    @Override
    public void onProviderDisabled(String provider) {

        if(checkPermission()){
            locationManager.removeUpdates(this);
        }
    }

}
