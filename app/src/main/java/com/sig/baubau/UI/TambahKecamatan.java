package com.sig.baubau.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class  TambahKecamatan extends AppCompatActivity {
    EditText idd, kode, nama, lat, lng;
    Button simpan;
    LinearLayout map;
    KecDB db;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kecamatan);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        idd = findViewById(R.id.Add_idKec);
        map = findViewById(R.id.btn_AddLatLong);
        simpan = findViewById(R.id.btnSimpanTambahKec);
        kode = findViewById(R.id.Add_kdKec);
        nama = findViewById(R.id.Add_nmKec);
        lat = findViewById(R.id.Add_Lat);
        lng = findViewById(R.id.Add_Long);
        ContentValues values = new ContentValues();
        db = new KecDB(this);
        Button kembali = findViewById(R.id.btnBackTambahKec);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String ko = getIntent().getStringExtra("a");

        if (ko.equals("1")) {
            lat.setText(getIntent().getStringExtra("lat"));
            lng.setText(getIntent().getStringExtra("lng"));
        } else
        if (ko.equals("2")) {
            lat.setText(getIntent().getStringExtra("lat"));
            lng.setText(getIntent().getStringExtra("lng"));
        }

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(TambahKecamatan.this, MapAddKec.class);
                i.putExtra("a", "0");
                i.putExtra("lat", lat.getText().toString());
                i.putExtra("lng", lng.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TambahKecamatan.this, Kecamatan.class));
    }
/*void tambahData() {
        String url = "https://185.27.134.11/epiz_31554580/tambah_data";
        StringRequest respon = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> tambah = new HashMap<>();
                tambah.put("nik", kode.getText().toString());
                tambah.put("username", nama.getText().toString());
                tambah.put("password", bobot.getText().toString());
                return tambah;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(respon);
    }*/
}