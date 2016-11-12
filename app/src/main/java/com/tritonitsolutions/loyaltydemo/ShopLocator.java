package com.tritonitsolutions.loyaltydemo;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tritonitsolutions.layaltydemo.R;

/**
 * Created by TritonDev on 13/10/2015.
 */
public class ShopLocator extends ActionBarActivity {
    Toolbar toolbar;
    GoogleMap googleMap;
    Marker marker;
    static final LatLng latLng = new LatLng(12.9966403, 80.2517553);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoplocator_layout);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ShopLocator");
        try {
            loadingMap();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void loadingMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map_shop)).getMap();
            googleMap.setMyLocationEnabled(true);
            googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location loc) {

                    if (googleMap != null) {
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                        marker = googleMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));

                    }
                }
            });


        }
    }
    protected void onResume() {
        super.onResume();
        //loadingMap();

    }
}
