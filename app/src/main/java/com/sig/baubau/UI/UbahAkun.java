package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sig.baubau.DB.UserDB;
import com.sig.baubau.R;

public class UbahAkun extends AppCompatActivity {
    EditText nik, username, password;
    Button kembali, simpan;
    UserDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_akun);

        nik = findViewById(R.id.Edit_kdAkun);
        username = findViewById(R.id.Edit_username);
        password = findViewById(R.id.Edit_password);
        kembali = findViewById(R.id.btnBackUbahAkun);
        simpan = findViewById(R.id.btnSimpanUbahAkun);
        db = new UserDB(this);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mNik = nik.getText().toString().trim();
                String mUsername = username.getText().toString().trim();
                String mPassword = password.getText().toString().trim();
                ContentValues values = new ContentValues();
                String id = getIntent().getStringExtra(UserDB.row_id);

                if (mNik.isEmpty()){
                    nik.requestFocus();
                    nik.setError("Masukkan NIK");
                } else
                if (mUsername.isEmpty()){
                    username.requestFocus();
                    username.setError("Masukkan username");
                } else
                if (mPassword.isEmpty()){
                    password.requestFocus();
                    password.setError("Masukkan password");
                } else {
                    Boolean cek = db.checkEmail(mUsername);
                    if (cek == true){
                        username.requestFocus();
                        username.selectAll();
                        username.setError("Username sudah dipakai");
                    } else {
                        values.put(UserDB.row_nik, mNik);
                        values.put(UserDB.row_email, mUsername);
                        values.put(UserDB.row_password, mPassword);
                        db.updateData(values, id);
                        Toast.makeText(UbahAkun.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UbahAkun.this, AkunPenggguna.class));
        finish();
        super.onBackPressed();
    }
}