package com.sig.baubau.fragment;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.sig.baubau.DB.DBkoordinat;
import com.sig.baubau.DB.DBlistBanjir;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;

import java.util.ArrayList;

public class MapBanjir extends Fragment implements OnMapReadyCallback {
    GoogleMap googleMap;
    PolygonOptions polygonOptions;
    SupportMapFragment supportMapFragment;
    long id;
    Polygon polygon;

    public MapBanjir() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map_banjir, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapBanjiro);
        supportMapFragment.getMapAsync(this);

        id = getArguments().getLong("id");
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        KecDB dB = new KecDB(getContext());
        String sLat = "";
        String sLng = "";
        String kode = "";
        String latitude = "";
        String longitude = "";
        Cursor cur = dB.oneData(id);
        if (cur.moveToFirst()){
            sLat = cur.getString(cur.getColumnIndex(KecDB.row_lat));
            sLng = cur.getString(cur.getColumnIndex(KecDB.row_long));
            kode = cur.getString(cur.getColumnIndex(KecDB.row_kdKec));
        }
        String[] lat = sLat.split(",");
        String[] lng = sLng.split(",");

        DBkoordinat dBkoordinat = new DBkoordinat(getContext());
        Cursor cro = dBkoordinat.oneData(kode);
        if (cro.moveToFirst()){
            latitude = cro.getString(cro.getColumnIndex(DBkoordinat.row_lat));
            longitude = cro.getString(cro.getColumnIndex(DBkoordinat.row_long));
        }

        String ancaman = "";
        DBlistBanjir dBlistBanjir = new DBlistBanjir(getContext());
        Cursor cr = dBlistBanjir.oneData(id);
        if (cr.moveToFirst()){
            ancaman = cr.getString(cr.getColumnIndex(DBlistBanjir.row_ket));
        }
        ArrayList<LatLng> nn = new ArrayList<>();

        for (int i=0; i<lat.length; i++){
            nn.add(new LatLng(Double.parseDouble(lat[i]), Double.parseDouble(lng[i])));
        }
        polygonOptions = new PolygonOptions().addAll(nn);
        polygon = googleMap.addPolygon(polygonOptions);
        if (ancaman == "0") {
            polygonOptions.fillColor(Color.TRANSPARENT).strokeWidth(1).strokeColor(Color.TRANSPARENT);
        } else
        if (ancaman.equals("1")) {
            polygonOptions.fillColor(Color.GREEN).strokeWidth(1).strokeColor(Color.GREEN);
        } else
        if (ancaman.equals("2")) {
            polygonOptions.fillColor(Color.YELLOW).strokeWidth(1).strokeColor(Color.YELLOW);
        } else
        if (ancaman.equals("3")) {
            polygonOptions.fillColor(Color.RED).strokeWidth(1).strokeColor(Color.RED);
        }

        LatLng kota = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kota));
        CameraPosition position = CameraPosition.builder()
                .target(kota)
                .zoom(12.0f)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
    }
}