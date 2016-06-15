package com.datasteffen.datenclick;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class AcceptImgActivity extends AppCompatActivity {

    LocationListener mlocListener;
    private final static int DISTANCE_UPDATE = 1;
    private final static int TIME_UPDATE = 5;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private boolean LocationAvailable;
    public Location loc;
    ImageView im;
    Button btnback;
    Button btnforward;
    Profile p = null;
    byte[] b = null;

    ActiveProfile activeProfile = new ActiveProfile();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_img);
        Bundle bundle = getIntent().getExtras();

        LocationAvailable = false;

        p = (Profile) bundle.get("from");
        b = (byte[]) bundle.get("picture1");

        mlocListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                loc = location;
                btnforward.setEnabled(true);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                if (checkPermission()) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_UPDATE, DISTANCE_UPDATE, mlocListener);
                } else {
                    requestPermissions();
                }
            }

            @Override
            public void onProviderDisabled(String provider) {

                if (checkPermission()) {
                    locationManager.removeUpdates(mlocListener);
                }
            }
        };

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_UPDATE, DISTANCE_UPDATE, mlocListener);
        } else {
            requestPermissions();
        }


        im = (ImageView) findViewById(R.id.imageview1);
        btnback = (Button) findViewById(R.id.btnback);
        btnforward = (Button) findViewById(R.id.btnok);
        btnforward.setEnabled(false);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AcceptImgActivity.this, MainActivity.class);
                i.putExtra("from", p);
                startActivity(i);

            }
        });

        btnforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activeProfile.setName(p.getName());
                activeProfile.setLat((float) loc.getLatitude());
                activeProfile.setLon((float) loc.getLongitude());
                activeProfile.setEmail(p.getEmail());
                activeProfile.setImgbytes(b);

                Intent i = new Intent(AcceptImgActivity.this, MapsActivity.class);
                i.putExtra("ownlocation", activeProfile);
                i.putExtra("from", p);
                i.putExtra("picture1", b);
                new AsyncTaskSendactiveprofileToDb().execute();
                startActivity(i);

            }
        });

        byte[] b = (byte[]) bundle.get("picture1");
        Bitmap image = BitmapFactory.decodeByteArray(b, 0, b.length);

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        matrix.postRotate(90);
        matrix.postTranslate(image.getWidth(), 0);
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        im.setImageBitmap(image);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(CompressFormat.JPEG, 50, out);

        out.toByteArray();
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (checkPermission()) {
            locationManager.removeUpdates(mlocListener);
        } else {
            requestPermissions();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, TIME_UPDATE, DISTANCE_UPDATE, mlocListener);
        } else {
            requestPermissions();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (checkPermission()) {
            locationManager.removeUpdates(mlocListener);
        } else {
            requestPermissions();
        }
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

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){

        }else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }



    public class AsyncTaskSendactiveprofileToDb extends AsyncTask<ActiveProfile,Void,ActiveProfile> {


        @Override
        protected ActiveProfile doInBackground(ActiveProfile... params) {

            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL("http://android2-smcphbusiness.rhcloud.com/users/activeprofile");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                Bitmap image = BitmapFactory.decodeByteArray(activeProfile.getImgbytes(), 0,activeProfile.getImgbytes().length);

                Matrix matrix = new Matrix();

                matrix.postTranslate(image.getWidth(), 0);
                image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                Bitmap resize = Bitmap.createScaledBitmap(image,100,100,false);
                resize.compress(CompressFormat.JPEG, 75, out);


                String bytes = Base64.encodeToString(out.toByteArray(), Base64.NO_WRAP);


                OutputStream os = urlConnection.getOutputStream();
                OutputStreamWriter wr = new OutputStreamWriter(os);
               String jsonObject = activeProfile.toJson();

                JSONObject js = new JSONObject();
                js.put("email",activeProfile.getEmail());
                js.put("lat",activeProfile.getLat());
                js.put("lon", activeProfile.getLon());
                js.put("image",bytes);

                wr.write(js.toString());
                wr.flush();
                wr.close();
                os.close();

                InputStream ins = urlConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(ins);
                BufferedReader in = new BufferedReader(isr);

                String inputLine;
                String result= "";

                while( (inputLine = in.readLine()) != null )
                    result += inputLine;

                in.close();


            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }
            return null;
        }
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }


}