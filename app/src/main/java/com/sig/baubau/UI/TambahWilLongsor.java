package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.DB.LongsorDB;
import com.sig.baubau.R;

import java.util.List;

public class TambahWilLongsor extends AppCompatActivity {
    Button kembali, simpan;
    EditText aman, rendah, sedang;
    Spinner spKode;
    LongsorDB db;
    KecDB kecDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_wil_longsor);

        db = new LongsorDB(this);
        kecDB = new KecDB(this);
        kembali = findViewById(R.id.btnBackTambahWillongsor);
        simpan = findViewById(R.id.btnSimpanTambahWillongsor);
        aman = findViewById(R.id.Add_amanwillongsor);
        rendah = findViewById(R.id.Add_rendahwillongsor);
        sedang = findViewById(R.id.Add_sedangwillongsor);
        spKode = findViewById(R.id.Add_kdwillongsor);

        loadSpinner();
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String xaman = aman.getText().toString().trim();
                String xrendah = rendah.getText().toString().trim();
                String xsedang = sedang.getText().toString().trim();
                String kode = spKode.getSelectedItem().toString().substring(0,5).trim();

                ContentValues values = new ContentValues();
                values.put(LongsorDB.row_kdKec, kode);
                values.put(LongsorDB.row_tidak, xaman);
                values.put(LongsorDB.row_rendah, xrendah);
                values.put(LongsorDB.row_sedang, xsedang);

                Boolean cek = db.cekKode(kode);
                if (cek == true) {
                    Toast.makeText(getApplicationContext(), "Data " + kode +" sudah ada", Toast.LENGTH_SHORT).show();
                    spKode.requestFocus();
                } else
                if (cek == false) {
                    db.insertData(values);
                    Toast.makeText(getApplicationContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(TambahWilLongsor.this, WilayahLongsor.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TambahWilLongsor.this, WilayahLongsor.class));
        finish();
        super.onBackPressed();
    }

    private void loadSpinner() {
        List<String> kodekec = kecDB.ambilData();

        // Creating adapter for spinner
        ArrayAdapter<String> dataKode = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, kodekec);

        // Drop down layout style - list view with radio button
        dataKode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spKode.setAdapter(dataKode);
    }
}