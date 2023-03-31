package com.sig.baubau.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.Polygon;
import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.R;
import com.sig.baubau.fragment.AlgoBanjir;
import com.sig.baubau.fragment.AlgoLongsor;
import com.sig.baubau.fragment.ListBanjir;
import com.sig.baubau.fragment.ListLongsor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class LongsorActivity extends AppCompatActivity {
    BanjirDB db;
    AnimatedBottomBar tab;
    Polygon polygon;
    ImageView kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longsor);

        Fragment fragmen = new ListLongsor();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLongsor, fragmen);
        ft.commit();
        db = new BanjirDB(this);
        kembali = findViewById(R.id.backFromLongsor);
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tab = findViewById(R.id.tabNavigationLongsor);
        tab.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
                Fragment fragment = null;
                Bundle data = new Bundle();
                LinearLayout ly = findViewById(R.id.lyKet1);
                switch (tab1.getId()){
                    case R.id.tabListLongsor:
                        fragment = new ListLongsor();
                        ly.setVisibility(View.VISIBLE);

                        fragment.setArguments(data);
                        break;
                    case R.id.tabAlgoLongsor:
                        fragment = new AlgoLongsor();
                        ly.setVisibility(View.GONE);
                        fragment.setArguments(data);
                        break;
                }
                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLongsor, fragment);
                    ft.commit();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LongsorActivity.this, MainActivity.class));
        finish();
    }
}