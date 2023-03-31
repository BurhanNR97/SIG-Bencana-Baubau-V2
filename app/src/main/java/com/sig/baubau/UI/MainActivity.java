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

import com.sig.baubau.R;

public class MainActivity extends AppCompatActivity {
    TextView tvBanjir, tvLongsor, tvTentang, tvPosko;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        tvBanjir = findViewById(R.id.menuBanjir);
        tvLongsor = findViewById(R.id.menuLongsor);
        tvPosko = findViewById(R.id.menuPosko);
        tvTentang = findViewById(R.id.menuTentang);

        tvBanjir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BanjirActivity.class));
                finish();
            }
        });

        tvLongsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LongsorActivity.class));
                finish();
            }
        });

        tvPosko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Posko.class));
                finish();
            }
        });
    }

    public void formLogin(View v){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}