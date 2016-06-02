package com.datasteffen.datenclick;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity{

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Button c;
    private static int TAKE_PICTURE= -1;
    MySurfaceView ms;
    Profile p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ms = new MySurfaceView(getApplicationContext(),null);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);

        }else{
            addCameraview();
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        }

        c = (Button) findViewById(R.id.button);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                final Profile p =(Profile) bundle.get("from");

                Intent i = new Intent(MainActivity.this,AcceptImgActivity.class);
                byte[] b = ms.TakePicture();

                i.putExtra("picture1",b);
                i.putExtra("from",p);

                startActivity(i);
            }
        });
    }

    private void addCameraview(){

        ms = new MySurfaceView(getApplicationContext(),null);
        RelativeLayout relayout = (RelativeLayout)findViewById(R.id.mainlayoutid);
        relayout.addView(ms);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    addCameraview();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    this.finish();
                    System.exit(0);
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}