package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.DBkoordinat;
import com.sig.baubau.DB.DBlistBanjir;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;

import java.util.ArrayList;

public class TampilMAppp extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    PolygonOptions polygonOptions;
    SupportMapFragment supportMapFragment;
    long id;
    Polygon polygon;
    TextView nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_m_appp);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        supportMapFragment.getMapAsync(this);
    }

    public void kembali(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("Range")
    @Override
    public void onMapReady(GoogleMap gMap) {
        id = getIntent().getLongExtra("id", 0);
        googleMap = gMap;
        nama = findViewById(R.id.namaKecancaman);
        KecDB dB = new KecDB(this);
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

        DBkoordinat dBkoordinat = new DBkoordinat(this);
        Cursor cro = dBkoordinat.oneData(kode);
        if (cro.moveToFirst()){
            latitude = cro.getString(cro.getColumnIndex(DBkoordinat.row_lat));
            longitude = cro.getString(cro.getColumnIndex(DBkoordinat.row_long));
        }

        String ancaman = "";
        String namaKec = "";
        BanjirDB banjirDB = new BanjirDB(this);
        Cursor cr = banjirDB.oneData(id);
        if (cr.moveToFirst()){
            namaKec = cr.getString(cr.getColumnIndex(BanjirDB.row_kdKec));
            ancaman = cr.getString(cr.getColumnIndex(BanjirDB.row_ket));
        }

        Cursor cf = dB.namaKec(namaKec);
        if (cf.moveToFirst()){
            nama.setText(cf.getString(cf.getColumnIndex(KecDB.row_nmKec)));
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

        LatLng kota = new LatLng(-5.470011, 122.597684);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(kota));
        CameraPosition position = CameraPosition.builder()
                .target(kota)
                .zoom(12.0f)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
    }
}