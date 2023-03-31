 package com.sig.baubau.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sig.baubau.Adapter.AdpKec;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.DB.UserDB;
import com.sig.baubau.R;

public class Kecamatan extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    KecDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kecamatan);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        db = new KecDB(this);
        listView = (ListView)findViewById(R.id.listKec);
        listView.setOnItemClickListener(this);
        Button tambah = findViewById(R.id.btnTambahKec);
        Button kembali = findViewById(R.id.backFromKec);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Kecamatan.this, TambahKecamatan.class);
                intent.putExtra("a", "0");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Kecamatan.this, AdminDashboard.class));
        finish();
    }

    public void setListView(){
        Cursor cursor = db.allData();
        AdpKec customCursorAdapter = new AdpKec(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDkec);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();

        AlertDialog.Builder builder = new AlertDialog.Builder(Kecamatan.this);
        builder.setCancelable(true);
        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteData(id);
                Toast.makeText(Kecamatan.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                onResume();
            }
        });
        builder.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Kecamatan.this, UbahKec.class);
                intent.putExtra("id", id);
                intent.putExtra("a", "0");
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