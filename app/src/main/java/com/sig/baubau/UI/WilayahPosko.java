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
import com.sig.baubau.Adapter.AdpWilayahPosko;
import com.sig.baubau.DB.PoskoDB;
import com.sig.baubau.R;

public class WilayahPosko extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Button kembali, tambah;
    ListView listView;
    PoskoDB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilayah_posko);

        db = new PoskoDB(this);
        listView = findViewById(R.id.listWilayahposko);
        kembali = findViewById(R.id.backFromWIlayahposko);
        tambah = findViewById(R.id.btnTambahWilayahposko);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WilayahPosko.this, TambahWilPosko.class);
                intent.putExtra("no", "0");
                startActivity(intent);
                finish();
            }
        });

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WilayahPosko.this, AdminDashboard.class));
        finish();
        super.onBackPressed();
    }

    public void setListView(){
        Cursor cursor = db.allData();
        AdpWilayahPosko customCursorAdapter = new AdpWilayahPosko(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        setListView();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView getId = (TextView)view.findViewById(R.id.listIDwilposko);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();

        AlertDialog.Builder builder = new AlertDialog.Builder(WilayahPosko.this);
        builder.setCancelable(true);
        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteData(id);
                Toast.makeText(WilayahPosko.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });
        builder.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(WilayahPosko.this, UbahWilPosko.class);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}