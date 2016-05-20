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
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
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

                List<Dater> da = null;
                try {
                    da = (List<Dater>) new getDatersAsync().execute().get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(gpsLocation.this, MapsActivity.class);
                i.putExtra("LOCATION", loc);
                Bundle b = new Bundle();
                b.putSerializable("ListDaters", (Serializable) da);
                i.putExtra("Daters",b);
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

    public class getDatersAsync extends AsyncTask<List<Dater>,Void,List<Dater>>{
        URL url;
        HttpURLConnection urlConnection;
        @Override
        protected List<Dater> doInBackground(List<Dater>... params) {
            try {
                url = new URL("http://android2-smcphbusiness.rhcloud.com/users");

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                List<Dater> daters = readStream(in);

                return daters;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }return null;
        }

        @Override
        protected void onPostExecute(List<Dater> s) {
        }

        private List<Dater> readStream(InputStream is) throws JSONException {
            float lat = 0;
            float lon = 0;
            Dater dater = null;
            List<Dater> daters = new ArrayList<>();
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = is.read();
                while(i != -1) {
                    bo.write(i);
                    i = is.read();
                }
                JSONArray jarray = new JSONArray(bo.toString());
                for(int j = 0; j < jarray.length();j++){
                JSONObject jsonobject = jarray.getJSONObject(j);
                    String name = jsonobject.optString("name").toString();

                    JSONArray jsonArraylocationg = jsonobject.getJSONArray("loc");
                    for(int k = 0; k<jsonArraylocationg.length();k++){
                        JSONObject jsonObject = jsonArraylocationg.getJSONObject(k);
                        lat = Float.parseFloat(jsonObject.optString("lat"));
                        lon = Float.parseFloat(jsonObject.optString("lon"));
                    }
                    dater = new Dater(name,lat,lon);
                    daters.add(dater);

                }

                return daters;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
