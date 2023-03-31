package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sig.baubau.DB.PoskoDB;
import com.sig.baubau.R;

public class TambahWilPosko extends AppCompatActivity {
    Button kembali, simpan;
    LinearLayout map;
    EditText alamat, nama, koordinat;
    PoskoDB db;
    String nomor = "";
    String lat = "";
    String lng = "";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_wil_posko);

        alamat = findViewById(R.id.Add_alamatposko);
        nama = findViewById(R.id.Add_namaposko);
        koordinat = findViewById(R.id.Add_koordinatposko);
        db = new PoskoDB(this);
        kembali = findViewById(R.id.btnBackTambahWilposko);
        nomor = getIntent().getStringExtra("no");
        if (nomor.equals("1")){
            nama.setText(getIntent().getStringExtra("nama"));
            alamat.setText(getIntent().getStringExtra("alamat"));
            koordinat.setText(getIntent().getStringExtra("latlng"));
        }
        simpan = findViewById(R.id.btnSimpanTambahWilposko);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        map = findViewById(R.id.btn_Addkoordinat);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TambahWilPosko.this, TambahMapPosko.class));
                finish();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String al = alamat.getText().toString().trim();
                String nm = nama.getText().toString().trim();
                String ko = koordinat.getText().toString().trim();
                if (nomor.equals("1")){
                    String[] ll = ko.split(",");
                    lat = ll[0].trim();
                    lng = ll[1].trim();
                }

                if (nm.isEmpty()){
                    nama.requestFocus();
                    nama.setError("Harus diisi");
                } else
                if (al.isEmpty()){
                    alamat.requestFocus();
                    alamat.setError("Harus diisi");
                } else
                if (ko.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Silahkan tabahkan titik koordinat", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues values = new ContentValues();
                    values.put(PoskoDB.row_nama, nm);
                    values.put(PoskoDB.row_alamat, al);
                    values.put(PoskoDB.row_lat, lat);
                    values.put(PoskoDB.row_lng, lng);
                    values.put(PoskoDB.row_jarak, "0");
                    db.insertData(values);
                    Toast.makeText(getApplicationContext(), "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TambahWilPosko.this, WilayahPosko.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), WilayahPosko.class));
        finish();
        super.onBackPressed();
    }
}