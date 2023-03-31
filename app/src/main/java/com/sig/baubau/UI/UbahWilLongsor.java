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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.DB.LongsorDB;
import com.sig.baubau.R;

import java.util.List;

public class UbahWilLongsor extends AppCompatActivity {
    Button kembali, simpan;
    EditText aman, rendah, sedang;
    Spinner spKode;
    LongsorDB db;
    long id;
    KecDB kecDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_wil_longsor);

        db = new LongsorDB(this);
        kecDB = new KecDB(this);
        kembali = findViewById(R.id.btnBackEditWillongsor);
        simpan = findViewById(R.id.btnSimpanEditWillongsor);
        aman = findViewById(R.id.Edit_amanwillongsor);
        rendah = findViewById(R.id.Edit_rendahwillongsor);
        sedang = findViewById(R.id.Edit_sedangwillongsor);
        spKode = findViewById(R.id.Edit_kdwillongsor);
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
                String kode = spKode.getSelectedItem().toString().substring(0,5).trim();

                ContentValues values = new ContentValues();
                values.put(BanjirDB.row_kdKec, kode);
                values.put(BanjirDB.row_tidak, xaman);
                values.put(BanjirDB.row_rendah, xrendah);
                values.put(BanjirDB.row_sedang, xsedang);
                db.updateData(values, id);
                Toast.makeText(getApplicationContext(), "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UbahWilLongsor.this, WilayahLongsor.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UbahWilLongsor.this, WilayahLongsor.class));
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

    @SuppressLint("Range")
    private void getData(){
        Cursor cek = db.oneData(id);
        if (cek.moveToFirst()){
            String naman = cek.getString(cek.getColumnIndex(LongsorDB.row_tidak));
            String nrendah = cek.getString(cek.getColumnIndex(LongsorDB.row_rendah));
            String nsedang = cek.getString(cek.getColumnIndex(LongsorDB.row_sedang));

            aman.setText(naman);
            rendah.setText(nrendah);
            sedang.setText(nsedang);
            spKode.setSelection(Integer.parseInt(String.valueOf(id)) - 1);
        }
    }
}