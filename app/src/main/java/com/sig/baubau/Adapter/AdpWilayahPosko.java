package com.sig.baubau.Adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sig.baubau.DB.BanjirDB;
import com.sig.baubau.DB.PoskoDB;
import com.sig.baubau.R;
import com.sig.baubau.UI.Posko;

public class AdpWilayahPosko extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdpWilayahPosko(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_wilposko, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDwilposko);
        holder.ListNama = (TextView)v.findViewById(R.id.listnamaposko);
        holder.ListAlamat = (TextView)v.findViewById(R.id.listalamatposko);
        holder.ListKoordinat = (TextView)v.findViewById(R.id.listkoordinatposko);

        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();
        String lat = cursor.getString(cursor.getColumnIndex(PoskoDB.row_lat)).trim();
        String lng = cursor.getString(cursor.getColumnIndex(PoskoDB.row_lng)).trim();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(PoskoDB.row_id)));
        holder.ListNama.setText(cursor.getString(cursor.getColumnIndex(PoskoDB.row_nama)));
        holder.ListAlamat.setText(cursor.getString(cursor.getColumnIndex(PoskoDB.row_alamat)));
        holder.ListKoordinat.setText(lat + " - " +lng);
    }

    class MyHolder{
        TextView ListID;
        TextView ListNama;
        TextView ListAlamat;
        TextView ListKoordinat;
    }
}
