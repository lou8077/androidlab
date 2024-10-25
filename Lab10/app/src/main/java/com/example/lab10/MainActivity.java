package com.example.lab10;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        else
            initMap();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED)
                    finish();
                else
                    initMap();
            }
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // 檢查是否授權定位權限
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        // 顯示目前位置與目前位置的按鈕
        googleMap.setMyLocationEnabled(true);
        // 加入標記
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(25.033611, 121.565000));
        markerOptions.title("台北 101");
        markerOptions.draggable(true);
        googleMap.addMarker(markerOptions);
        markerOptions.position(new LatLng(25.047924, 121.517081));
        markerOptions.title("台北車站");
        markerOptions.draggable(true);
        googleMap.addMarker(markerOptions);
        // 繪製線段
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(new LatLng(25.033611, 121.565000));
        polylineOptions.add(new LatLng(25.032728, 121.564137));
        polylineOptions.add(new LatLng(25.047924, 121.517081));
        polylineOptions.color(Color.BLUE);
        Polyline polyline = googleMap.addPolyline(polylineOptions);
        polyline.setWidth(10);
        // 初始化地圖中心點及size
        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                        new LatLng(25.034, 121.545), 13)
        );
    }
}