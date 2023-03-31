package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;

public class UbahKec extends AppCompatActivity {
    EditText txID, txKode, txNama, txLat, txLng;
    LinearLayout map;
    KecDB db;
    long id;
    Button kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_kec);

        txID = findViewById(R.id.Edit_idKec);
        txKode = findViewById(R.id.Edit_kdKec);
        txNama = findViewById(R.id.Edit_nmKec);
        txLat = findViewById(R.id.Edit_Lat);
        txLng = findViewById(R.id.Edit_Long);
        map = findViewById(R.id.btn_EditLatLong);
        kembali = findViewById(R.id.btnBackUbahKec);

        db = new KecDB(this);
        id = getIntent().getLongExtra("id", 0);
        getData();

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UbahKec.this, MapEditKec.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("Range")
    private void getData(){
        Cursor cek = db.oneData(id);
        if (cek.moveToFirst()){
            String kode = cek.getString(cek.getColumnIndex(KecDB.row_kdKec));
            String nama = cek.getString(cek.getColumnIndex(KecDB.row_nmKec));
            String lat  = cek.getString(cek.getColumnIndex(KecDB.row_lat));
            String lang = cek.getString(cek.getColumnIndex(KecDB.row_long));

            txID.setText(""+id);
            txKode.setText(kode);
            txNama.setText(nama);

            String ko = getIntent().getStringExtra("a");
            if (ko.equals("1")) {
                txLat.setText(getIntent().getStringExtra("lat"));
                txLng.setText(getIntent().getStringExtra("lng"));
            } else
            if (ko.equals("2")) {
                txLat.setText(getIntent().getStringExtra("lat"));
                txLng.setText(getIntent().getStringExtra("lng"));
            } else
            if (ko.equals("0")) {
                txLat.setText(lat);
                txLng.setText(lang);
            }
        }
    }

    public void simpan(View v){
        String kode = txKode.getText().toString().trim();
        String nama = txNama.getText().toString().trim();
        String lat = txLat.getText().toString().trim();
        String lng = txLng.getText().toString().trim();

        if (kode.isEmpty()){
            txKode.requestFocus();
            txKode.setError("Data tidak boleh kosong");
        } else
        if (nama.isEmpty()){
            txNama.requestFocus();
            txNama.setError("Data tidak boleh kosong");
        } else {
            ContentValues values = new ContentValues();
            values.put(KecDB.row_kdKec, kode);
            values.put(KecDB.row_nmKec, nama);
            values.put(KecDB.row_lat, lat);
            values.put(KecDB.row_long, lng);
            db.updateData(values, id);
            Toast.makeText(UbahKec.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UbahKec.this, Kecamatan.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UbahKec.this, Kecamatan.class));
        finish();
    }
}