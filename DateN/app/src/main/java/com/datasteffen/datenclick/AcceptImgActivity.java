package com.datasteffen.datenclick;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


public class AcceptImgActivity extends AppCompatActivity implements LocationListener {

    private final static int DISTANCE_UPDATE = 1;
    private final static int TIME_UPDATE = 5;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private boolean LocationAvailable;
    public Location loc;
    ImageView im;
    Button btnback;
    Button btnforward;
    Profile p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_img);

        LocationAvailable = false;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkPermission()){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,TIME_UPDATE,DISTANCE_UPDATE,this);
        }else{
            requestPermissions();
        }


        im = (ImageView) findViewById(R.id.imageview1);
        btnback = (Button) findViewById(R.id.btnback);
        btnforward = (Button) findViewById(R.id.btnok);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AcceptImgActivity.this,MainActivity.class);
                startActivity(i);

            }
        });

        btnforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                Profile p =(Profile) intent.getSerializableExtra("from");
               //

            }
        });

        Bundle bundle = getIntent().getExtras();
        byte[] b = (byte[]) bundle.get("picture1");
        Bitmap image = BitmapFactory.decodeByteArray(b, 0, b.length);

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        matrix.postRotate(90);
        matrix.postTranslate(image.getWidth(), 0);
        image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
        im.setImageBitmap(image);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,out);

        out.toByteArray();
    }

    @Override
    public void onLocationChanged(Location location) {
        loc = location;

        Toast.makeText(getApplicationContext(), String.valueOf(location.getLongitude()),Toast.LENGTH_SHORT).show();

    }

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
            Toast.makeText(this, "Please activate your GPS, This App use your location", Toast.LENGTH_LONG).show();
        }else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        }
    }

}
