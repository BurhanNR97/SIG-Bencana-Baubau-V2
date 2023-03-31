package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sig.baubau.DB.UserDB;
import com.sig.baubau.R;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText username, password;
    TextView btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        UserDB db = new UserDB(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                Boolean res = db.checkUser(user,pass);

                if (user.isEmpty()){
                    username.requestFocus();
                    username.setError("Masukkan username");
                } else
                if (pass.isEmpty()){
                    password.requestFocus();
                    password.setError("Masukkan password");
                } else {
                    if (res == true){
                        Intent intent = new Intent(getApplicationContext(), AdminDashboard.class);
                        intent.putExtra("user", user);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}