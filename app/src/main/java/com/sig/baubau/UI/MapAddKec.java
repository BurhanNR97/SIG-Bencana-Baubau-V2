package com.sig.baubau.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapAddKec extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment supportMapFragment;
    GoogleMap googleMap;
    LinearLayout undo, reset;
    Polygon polygon;
    List<LatLng> latLngsList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    CheckBox cb;
    String latlang;
    String[] latlong = null;
    String[][] koordinat = null;
    List<String> latitude = new ArrayList<>();
    List<String> longitude = new ArrayList<>();
    Button simpan;
    KecDB db;
    StringBuffer dataLat = new StringBuffer();
    StringBuffer dataLng = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_add_kec);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.addKec_maps);
        supportMapFragment.getMapAsync(this);

        undo = findViewById(R.id.btnrundoAdd);
        reset = findViewById(R.id.btnrresetAdd);
        cb = findViewById(R.id.cb_poligon);
        simpan = findViewById(R.id.btn_addMap);
        db = new KecDB(this);

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<latitude.size()-1; i++){
                    if (latitude.size()-1 == i){
                        dataLat.append(latitude.get(i));
                        dataLng.append(longitude.get(i));
                    } else {
                        dataLat.append(latitude.get(i)).append(',');
                        dataLng.append(longitude.get(i)).append(',');
                    }
                }
                Toast.makeText(getApplicationContext(), "Koordinat disimpan", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MapAddKec.this, TambahKecamatan.class);
                intent.putExtra("lat", dataLat.toString());
                intent.putExtra("lng", dataLng.toString());
                intent.putExtra("a", "1");
                startActivity(intent);
                finish();
            }
        });
    }

    public void kembali(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MapAddKec.this, TambahKecamatan.class);
        if (latitude.size() == 0){
            intent.putExtra("a", "2");
            intent.putExtra("lat", dataLat.toString());
            intent.putExtra("lng", dataLng.toString());
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        LatLng kota = new LatLng(-5.434039, 122.667554);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kota));
        CameraPosition position = CameraPosition.builder()
                .target(kota)
                .zoom(13.0f)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                Marker marker = googleMap.addMarker(markerOptions);

                latLngsList.add(latLng);
                latlang = latLng.toString().substring(10, latLng.toString().length() - 1);
                latlong = latlang.split(",");
                latitude.add(latlong[0]);
                longitude.add(latlong[1]);
                markerList.add(marker);

                //polygon = googleMap.addPolygon(polygonOptions);
                //polygon.setStrokeColor(Color.GREEN);
                /*if (markerList.size() >= 2){
                    PolygonOptions polygonOptions = new PolygonOptions().add(latLngsList.get(latLngsList.size() - 2), latLngsList.get(latLngsList.size() - 1))
                            .clickable(true);
                    polygon = googleMap.addPolygon(polygonOptions);
                    polygon.setStrokeColor(Color.GREEN);
                    if (polygon == null) return;
                }*/
            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latLngsList.size() > 0){
                    if (cb.isChecked()){
                        PolygonOptions polygonOptions = new PolygonOptions().addAll(latLngsList);
                        polygon = googleMap.addPolygon(polygonOptions);
                        polygon.setStrokeColor(Color.GREEN);
                    } else {
                        polygon.setStrokeColor(Color.TRANSPARENT);
                    }
                } else {
                    cb.setChecked(false);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (polygon!=null){
                    polygon.remove();
                }
                for (Marker marker : markerList) marker.remove();
                latLngsList.clear();
                markerList.clear();
                latlong = new String[latLngsList.size()];
                latitude.clear();
                longitude.clear();
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (polygon!=null){
                    polygon.remove();
                }
                Marker marker = markerList.get(markerList.size() - 1);
                marker.remove();
                latLngsList.remove(latLngsList.get(latLngsList.size() - 1));
                markerList.remove(markerList.get(markerList.size() - 1));
                latitude.remove(latitude.get(latitude.size() - 1));
                longitude.remove(longitude.get(longitude.size() - 1));
            }
        });
    }
}