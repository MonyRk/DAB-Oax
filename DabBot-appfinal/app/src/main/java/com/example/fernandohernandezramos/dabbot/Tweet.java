package com.example.fernandohernandezramos.dabbot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;


public class Tweet extends AppCompatActivity implements OnMapReadyCallback {
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    GoogleMap map;
    MapView mapView;
    TextView twitCompleto;
    double latitudmapa;
    double longitudmapa;
    LatLng Posicion;


    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recibirInfo();

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView =  findViewById(R.id.mi_mapa);
        mapView.onCreate(mapViewBundle);

        mapView.getMapAsync(this);

    }

    private void recibirInfo (){
        Bundle extras = getIntent().getExtras();
        String infot =  extras.getString("twit");
        twitCompleto = (TextView) findViewById(R.id.twitCompleto);
        twitCompleto.setText(infot);
        String latitud1 = extras.getString("latitudjson");
        String longitud2 = extras.getString("longitudjson");

        latitudmapa = Double.parseDouble(latitud1);
        longitudmapa = Double.parseDouble(longitud2);
        Posicion = new LatLng(latitudmapa, longitudmapa);



    }


    public void onMapReady(GoogleMap mapa) {


        mapa.addMarker(new MarkerOptions().position(new LatLng(latitudmapa,longitudmapa)).title("Marker"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(Posicion)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mapa.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

       // mapa.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }




}


