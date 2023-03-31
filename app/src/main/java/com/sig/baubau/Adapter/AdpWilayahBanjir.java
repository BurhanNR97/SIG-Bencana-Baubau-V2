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
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;

public class AdpWilayahBanjir extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdpWilayahBanjir(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_wilbanjie, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDwilbanjir);
        holder.ListKode = (TextView)v.findViewById(R.id.listKodekecwilbanjir);
        holder.ListAman = (TextView)v.findViewById(R.id.listindeksamanwilbanjir);
        holder.ListRendah = (TextView)v.findViewById(R.id.listindeksrendahwilbanjir);
        holder.ListSedang = (TextView)v.findViewById(R.id.listindekssedangwilbanjir);
        holder.ListTinggi = (TextView)v.findViewById(R.id.listindekstinggiwilbanjir);

        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(BanjirDB.row_id)));
        holder.ListKode.setText(cursor.getString(cursor.getColumnIndex(BanjirDB.row_kdKec)));
        holder.ListAman.setText(cursor.getString(cursor.getColumnIndex(BanjirDB.row_tidak)));
        holder.ListRendah.setText(cursor.getString(cursor.getColumnIndex(BanjirDB.row_rendah)));
        holder.ListSedang.setText(cursor.getString(cursor.getColumnIndex(BanjirDB.row_sedang)));
        holder.ListTinggi.setText(cursor.getString(cursor.getColumnIndex(BanjirDB.row_tinggi)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListKode;
        TextView ListAman;
        TextView ListRendah;
        TextView ListSedang;
        TextView ListTinggi;
    }
}
