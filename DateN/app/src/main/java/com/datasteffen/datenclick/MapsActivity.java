package com.datasteffen.datenclick;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<ActiveProfile> activeProfileList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try{
            activeProfileList = new AsyncGetActiveProfile().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        ArrayAdapter arrayAdapter = new ActiveProfileAdapter(this,activeProfileList);
        ListView listview = (ListView)findViewById(R.id.listviewid);
        listview.setAdapter(arrayAdapter);


        Bundle bundle = getIntent().getExtras();
        ActiveProfile activeProfile = (ActiveProfile)bundle.get("ownlocation");
        Profile profile = (Profile)bundle.get("from");

        setMarkermaster(activeProfile);

        for (ActiveProfile a :activeProfileList) {

            if(activeProfile.getName().equals(a.getName())){


           }else {

                setMarker(a);

            }
      }
    }


    private void setMarkermaster(ActiveProfile activeProfile){
        Marker marker = null;

        if(activeProfile.getImgbytes() != null){

          LatLng locate = new LatLng(activeProfile.getLat(),activeProfile.getLon());
            MarkerOptions options = new MarkerOptions().title(activeProfile.getName()).position(locate)
                   .icon(BitmapDescriptorFactory.fromBitmap(bytesToBitmap(activeProfile.getImgbytes())));
          marker = mMap.addMarker(options);
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locate, 15f));
}}

    private void setMarker(ActiveProfile activeProfile){
        Marker marker = null;

        if(activeProfile.getImgbytes() != null){

           LatLng locate = new LatLng(activeProfile.getLat(),activeProfile.getLon());
           MarkerOptions options = new MarkerOptions().title(activeProfile.getName()).position(locate)
                   .icon(BitmapDescriptorFactory.fromBitmap(bytesToBitmap(activeProfile.getImgbytes())));
           marker = mMap.addMarker(options);


        }}



    public static Bitmap bytesToBitmap (byte[] imageBytes)
    {
        byte[] byte1 = imageBytes;
        Bitmap bitmap = BitmapFactory.decodeByteArray(byte1, 0, imageBytes.length);
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        matrix.postRotate(90);
        matrix.postTranslate(bitmap.getWidth(), 0);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap resize = Bitmap.createScaledBitmap(bitmap,100,100,false);

        return resize;
    }



}