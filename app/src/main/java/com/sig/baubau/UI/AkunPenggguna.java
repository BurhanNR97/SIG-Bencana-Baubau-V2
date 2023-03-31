package com.sig.baubau.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sig.baubau.Adapter.AdpAkun;
import com.sig.baubau.Adapter.AdpKec;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.DB.UserDB;
import com.sig.baubau.R;

public class AkunPenggguna extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    UserDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_penggguna);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        listView = (ListView)findViewById(R.id.listAkun);
        listView.setOnItemClickListener(this);
        db = new UserDB(this);
        Button kembali = findViewById(R.id.backFromAkun);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button tambah = findViewById(R.id.btnTambahAkun);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunPenggguna.this, TambahAkun.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AkunPenggguna.this, AdminDashboard.class));
        finish();
    }

    public void setListView(){
        Cursor cursor = db.allData();
        AdpAkun customCursorAdapter = new AdpAkun(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listIDakun);
        final long id = Long.parseLong(getId.getText().toString());
        Cursor cur = db.oneData(id);
        cur.moveToFirst();

        AlertDialog.Builder builder = new AlertDialog.Builder(AkunPenggguna.this);
        builder.setCancelable(true);
        builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Cursor cekk = db.allData();
                if (cekk.getCount() == 1){
                    Toast toast = Toast.makeText(AkunPenggguna.this, "Data harus minimal 1", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    db.deleteData(id);
                    Toast.makeText(AkunPenggguna.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                    onResume();
                }
            }
        });
        builder.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AkunPenggguna.this, UbahAkun.class);
                intent.putExtra(UserDB.row_id, id);
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