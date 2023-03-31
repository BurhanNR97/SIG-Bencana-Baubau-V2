package com.sig.baubau.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.sig.baubau.Adapter.AdpBanjir;
import com.sig.baubau.Adapter.AdpLongsor;
import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.DBlistBanjir;
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.DB.LongsorDB;
import com.sig.baubau.R;
import com.sig.baubau.UI.TampilMAppp;

public class ListLongsor extends Fragment {
    GoogleMap googleMap;
    KecDB kecDB;
    PolygonOptions polygonOptions;
    long id;
    Polygon polygon;
    LongsorDB db;
    ListView listView;

    public ListLongsor() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list_longsor, container, false);

        kecDB = new KecDB(v.getContext());
        db = new LongsorDB(v.getContext());
        listView = v.findViewById(R.id.listLayoutLongsor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View vie, int i, long l) {
                TextView getId = (TextView)vie.findViewById(R.id.tvIDLongsor);
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
        AdpLongsor customCursorAdapter = new AdpLongsor(getContext(), cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setListView();
    }
}