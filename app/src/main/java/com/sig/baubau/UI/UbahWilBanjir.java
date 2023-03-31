package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;

import java.util.List;

public class UbahWilBanjir extends AppCompatActivity {
    Button kembali, simpan;
    EditText aman, rendah, sedang, tinggi;
    Spinner spKode;
    BanjirDB db;
    long id;
    KecDB kecDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_wil_banjir);

        db = new BanjirDB(this);
        kecDB = new KecDB(this);
        kembali = findViewById(R.id.btnBackEditWilBanjir);
        simpan = findViewById(R.id.btnSimpanEditWilBanjir);
        aman = findViewById(R.id.Edit_amanwilbanjir);
        rendah = findViewById(R.id.Edit_rendahwilbanjir);
        sedang = findViewById(R.id.Edit_sedangwilbanjir);
        tinggi = findViewById(R.id.Edit_tinggiwilbanjir);
        spKode = findViewById(R.id.Edit_kdwilbanjir);
        id = getIntent().getLongExtra("id", 0);

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
                String xtinggi = tinggi.getText().toString().trim();
                String kode = spKode.getSelectedItem().toString().substring(0,5).trim();

                ContentValues values = new ContentValues();
                values.put(BanjirDB.row_kdKec, kode);
                values.put(BanjirDB.row_tidak, xaman);
                values.put(BanjirDB.row_rendah, xrendah);
                values.put(BanjirDB.row_sedang, xsedang);
                values.put(BanjirDB.row_tinggi, xtinggi);
                db.updateData(values, id);
                Toast.makeText(getApplicationContext(), "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UbahWilBanjir.this, WilayahBanjir.class));
                finish();
            }
        });

        getData();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UbahWilBanjir.this, WilayahBanjir.class));
        finish();
        super.onBackPressed();
    }

    @SuppressLint("Range")
    private void getData(){
        Cursor cek = db.oneData(id);
        if (cek.moveToFirst()){
            String naman = cek.getString(cek.getColumnIndex(BanjirDB.row_tidak));
            String nrendah = cek.getString(cek.getColumnIndex(BanjirDB.row_rendah));
            String nsedang = cek.getString(cek.getColumnIndex(BanjirDB.row_sedang));
            String ntinggi = cek.getString(cek.getColumnIndex(BanjirDB.row_tinggi));

            aman.setText(naman);
            rendah.setText(nrendah);
            sedang.setText(nsedang);
            tinggi.setText(ntinggi);
            spKode.setSelection(Integer.parseInt(String.valueOf(id)) - 1);
        }
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