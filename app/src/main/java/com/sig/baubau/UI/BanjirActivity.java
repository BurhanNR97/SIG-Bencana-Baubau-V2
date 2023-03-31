package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.Polygon;
import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.DBlistBanjir;
import com.sig.baubau.R;
import com.sig.baubau.fragment.AlgoBanjir;
import com.sig.baubau.fragment.ListBanjir;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class BanjirActivity extends AppCompatActivity {
    BanjirDB db;
    int k = 4;
    AnimatedBottomBar tab;
    Polygon polygon;
    ImageView kembali;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banjir);
        Fragment fragmen = new ListBanjir();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameBanjir, fragmen);
        ft.commit();
        db = new BanjirDB(this);
        kembali = findViewById(R.id.backFromBanjir);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tab = findViewById(R.id.tabNavigationBanjir);
        tab.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
                Fragment fragment = null;
                Bundle data = new Bundle();
                LinearLayout ly = findViewById(R.id.lyKet);
                switch (tab1.getId()){
                    case R.id.tabListBanjir:
                        fragment = new ListBanjir();
                        ly.setVisibility(View.VISIBLE);

                        fragment.setArguments(data);
                        break;
                    case R.id.tabAlgoBanjir:
                        fragment = new AlgoBanjir();
                        ly.setVisibility(View.GONE);
                        fragment.setArguments(data);
                        break;
                }
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameBanjir, fragment);
                    ft.commit();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BanjirActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}