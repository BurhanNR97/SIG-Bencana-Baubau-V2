package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;

import java.util.ArrayList;
import java.util.List;

public class MapEditKec extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment supportMapFragment;
    GoogleMap googleMap;
    LinearLayout undo, reset;
    Polygon polygon;
    List<LatLng> latLngsList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    CheckBox cb;
    String latlang;
    String[] latlong = null;
    StringBuffer dataLat = new StringBuffer();
    StringBuffer dataLng = new StringBuffer();
    String[][] koordinat = null;
    Button simpan;
    KecDB db;
    long id;
    List<String> latitude = new ArrayList<>();
    List<String> longitude = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_edit_kec);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.editKec_maps);
        supportMapFragment.getMapAsync(this);

        undo = findViewById(R.id.btnUndoEdit);
        reset = findViewById(R.id.btnResetEdit);
        cb = findViewById(R.id.cb_poligon1);
        simpan = findViewById(R.id.btn_editMap);
        db = new KecDB(this);
        id = getIntent().getLongExtra("id", 0);

        Toast.makeText(getApplicationContext(), ""+id, Toast.LENGTH_SHORT).show();

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
                Intent intent = new Intent(MapEditKec.this, UbahKec.class);
                intent.putExtra("lat", dataLat.toString());
                intent.putExtra("lng", dataLng.toString());
                intent.putExtra("id", id);
                intent.putExtra("a", "1");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        LatLng kota = new LatLng(-5.434039, 122.667554);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kota));
        CameraPosition position = CameraPosition.builder()
                .target(kota)
                .zoom(12.0f)
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
                polygon.remove();
                for (Marker marker : markerList) marker.remove();
                latLngsList.clear();
                markerList.clear();
                latlong = new String[latLngsList.size()];
            }
        });

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (polygon!=null){
                    polygon.remove();
                }
                if (markerList.size() > 0){
                    Marker marker = markerList.get(markerList.size() - 1);
                    marker.remove();
                    latLngsList.remove(latLngsList.get(latLngsList.size() - 1));
                    markerList.remove(markerList.get(markerList.size() - 1));
                }
            }
        });
    }
}