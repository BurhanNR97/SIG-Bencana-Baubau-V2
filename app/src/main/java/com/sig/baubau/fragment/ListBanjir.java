package com.sig.baubau.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.sig.baubau.Adapter.AdpBanjir;
import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.DBlistBanjir;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;
import com.sig.baubau.UI.TampilMAppp;

public class ListBanjir extends Fragment {
    GoogleMap googleMap;
    KecDB kecDB;
    DBlistBanjir dBlistBanjir;
    PolygonOptions polygonOptions;
    long id;
    Polygon polygon;
    BanjirDB db;
    ListView listView;

    public ListBanjir() {
        // Required empty public constructor
    }

    @SuppressLint("Range")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_banjir, container, false);
        kecDB = new KecDB(v.getContext());
        dBlistBanjir = new DBlistBanjir(v.getContext());
        db = new BanjirDB(v.getContext());
        listView = v.findViewById(R.id.listLayoutBanjir);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View vie, int i, long l) {
                TextView getId = (TextView)vie.findViewById(R.id.tvIDBanjir0);
                final long id = Long.parseLong(getId.getText().toString());
                Cursor cur = db.oneData(id);
                cur.moveToFirst();
                Intent intent = new Intent(v.getContext(), TampilMAppp.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        return v;
    }

    public void setListView(){
        Cursor cursor = db.allData();
        AdpBanjir customCursorAdapter = new AdpBanjir(getContext(), cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setListView();
    }

    @SuppressLint("Range")
    private void tampil(){
        Cursor cur = dBlistBanjir.allData();
        Cursor cursor = kecDB.allData();
        char[] data = new char[0];
        String[][] koordinat = null;
        if (cur.moveToFirst()){
            data = String.valueOf(cur.getString(cur.getColumnIndex(DBlistBanjir.row_ket))).toCharArray();
        }

        if (cursor.moveToNext()){
            koordinat = new String[cursor.getCount()][4];
            for (int i=0; i<cursor.getCount(); i++){
                koordinat[i][0] = cursor.getString(cursor.getColumnIndex(KecDB.row_kdKec));
                koordinat[i][1] = String.valueOf(data[i]);
                koordinat[i][2] = cursor.getString(cursor.getColumnIndex(KecDB.row_lat));
                koordinat[i][3] = cursor.getString(cursor.getColumnIndex(KecDB.row_long));
            }
        }

        int n=0;
        while (n < 8) {
            polygonOptions = new PolygonOptions();
            for (int i=0; i<data.length; i++){
                String sLat = koordinat[i][2].substring(1,koordinat[i][2].length()-1);
                String sLng = koordinat[i][3].substring(1,koordinat[i][3].length()-1);
                String[] lat = sLat.split(",");
                String[] lng = sLng.split(",");

                for (int j=0; j<lat.length; j++){
                    polygonOptions.add(new LatLng(Double.parseDouble(lat[j]), Double.parseDouble(lng[j])));
                }

                if (koordinat[i][1].equals("0")) {
                    polygonOptions.fillColor(Color.TRANSPARENT).strokeWidth(1).strokeColor(Color.TRANSPARENT);
                } else
                if (koordinat[i][1].equals("1")) {
                    polygonOptions.fillColor(Color.GREEN).strokeWidth(1).strokeColor(Color.GREEN);
                } else
                if (koordinat[i][1].equals("2")) {
                    polygonOptions.fillColor(Color.YELLOW).strokeWidth(1).strokeColor(Color.YELLOW);
                } else
                if (koordinat[i][1].equals("3")) {
                    polygonOptions.fillColor(Color.RED).strokeWidth(1).strokeColor(Color.RED);
                }
            }
            googleMap.addPolygon(polygonOptions);
            n++;
        }
    }


}