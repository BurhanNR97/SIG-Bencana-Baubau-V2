package com.sig.baubau.UI;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.DB.LongsorDB;
import com.sig.baubau.DB.UserDB;
import com.sig.baubau.R;

public class AdminDashboard extends AppCompatActivity {
    KecDB db;
    UserDB userDB;
    LongsorDB longsorDB;
    BanjirDB banjirDB;
    TextView kecamatan, banjir, longsor, posko, akun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.ungu));
        }

        kecamatan = findViewById(R.id.jmlKec);
        banjir = findViewById(R.id.jmlBanjir);
        longsor = findViewById(R.id.jmlLongsor);
        posko = findViewById(R.id.jmlPosko);
        akun = findViewById(R.id.jmlAkun);
        db = new KecDB(this);
        userDB = new UserDB(this);
        banjirDB = new BanjirDB(this);
        longsorDB = new LongsorDB(this);

        Cursor c1 = db.allData();
        if (c1.moveToNext()){
            kecamatan.setText(""+c1.getCount());
        }

        Cursor c2 = userDB.allData();
        if (c2.moveToNext()){
            akun.setText(""+c2.getCount());
        }

        Cursor c3 = banjirDB.allData();
        if (c3.moveToNext()){
            banjir.setText(""+c3.getCount());
        }

        Cursor c4 = longsorDB.allData();
        if (c4.moveToNext()){
            longsor.setText(""+c4.getCount());
        }
    }

    public void kecamatan(View view){
        startActivity(new Intent(AdminDashboard.this, Kecamatan.class));
        finish();
    }

    public void akun(View view){
        startActivity(new Intent(AdminDashboard.this, AkunPenggguna.class));
        finish();
    }

    public void banjir(View view){
        startActivity(new Intent(AdminDashboard.this, WilayahBanjir.class));
        finish();
    }

    public void longsor(View view){
        startActivity(new Intent(AdminDashboard.this, WilayahLongsor.class));
        finish();
    }

    public void posko(View v){
        startActivity(new Intent(AdminDashboard.this, WilayahPosko.class));
        finish();
    }

    public void keluar(View view){
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(R.string.app_name)
                .setMessage("Anda yakin ingin logout?")
                .setPositiveButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setNegativeButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                })
                .show();
    }
}