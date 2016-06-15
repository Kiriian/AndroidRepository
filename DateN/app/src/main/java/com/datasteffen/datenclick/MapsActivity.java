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
import java.util.Iterator;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<ActiveProfile> activeIteratedProfileList = new ArrayList<>();
        Iterator<ActiveProfile> it = null;
        Bundle bundle = getIntent().getExtras();
        ActiveProfile activeProfile = (ActiveProfile)bundle.get("ownlocation");

        for (it = activeProfileList.iterator(); it.hasNext();)
        {
            ActiveProfile a = it.next();
            if (activeProfile.getEmail().equals(a.getEmail()))
            {
                setMarkermaster(activeProfile);
                it.remove();
            } else
            {
                setMarker(a);
                activeIteratedProfileList.add(a);
            }

        }
        ArrayAdapter arrayAdapter = new ActiveProfileAdapter(this,activeIteratedProfileList);
        ListView listview = (ListView)findViewById(R.id.listviewid);
        listview.setAdapter(arrayAdapter);
    }


    private void setMarkermaster(ActiveProfile activeProfile){

        if(activeProfile.getImgbytes() != null){

          LatLng locate = new LatLng(activeProfile.getLat(),activeProfile.getLon());
            MarkerOptions options = new MarkerOptions().title(activeProfile.getName()).position(locate)
                   .icon(BitmapDescriptorFactory.fromBitmap(bytesToBitmap(activeProfile.getImgbytes())));
           mMap.addMarker(options);
           mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locate, 15f));
}}

    private void setMarker(ActiveProfile activeProfile){

        if(activeProfile.getImgbytes() != null){

           LatLng locate = new LatLng(activeProfile.getLat(),activeProfile.getLon());
           MarkerOptions options = new MarkerOptions().title(activeProfile.getName()).position(locate)
                   .icon(BitmapDescriptorFactory.fromBitmap(bytesToBitmap(activeProfile.getImgbytes())));
           mMap.addMarker(options);


        }}



    public static Bitmap bytesToBitmap (byte[] imageBytes)
    {
        byte[] byte1 = imageBytes;
        Bitmap bitmap = BitmapFactory.decodeByteArray(byte1, 0, imageBytes.length);
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Bitmap resize = Bitmap.createScaledBitmap(bitmap,100,100,false);

        return resize;
    }

    public static <T> List<T> copyIterator(Iterator<T> iterator) {
        List<T> copy = new ArrayList<T>();
        while (iterator.hasNext())
            copy.add(iterator.next());
        return copy;
    }



}