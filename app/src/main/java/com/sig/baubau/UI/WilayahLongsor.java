package com.sig.baubau.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sig.baubau.Adapter.AdpWilayahBanjir;
import com.sig.baubau.Adapter.AdpWilayahLongsor;
import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.LongsorDB;
import com.sig.baubau.R;

public class WilayahLongsor extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    LongsorDB db;
    Button tambah, kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilayah_longsor);

        db = new LongsorDB(this);
        listView = findViewById(R.id.listWilayahLongsor);
        kembali = findViewById(R.id.backFromWIlayahlongsor);
        tambah = findViewById(R.id.btnTambahWilayahLongsor);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WilayahLongsor.this, TambahWilLongsor.class));
                finish();
            }
        });

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WilayahLongsor.this, AdminDashboard.class));
        finish();
        super.onBackPressed();
    }

    public void setListView(){
        Cursor cursor = db.allData();
        AdpWilayahLongsor customCursorAdapter = new AdpWilayahLongsor(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView getId = (TextView)view.findViewById(R.id.listIDwillongsor);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();

        AlertDialog.Builder builder = new AlertDialog.Builder(WilayahLongsor.this);
        builder.setCancelable(true);
        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteData(id);
                Toast.makeText(WilayahLongsor.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });
        builder.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(WilayahLongsor.this, UbahWilLongsor.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }
}