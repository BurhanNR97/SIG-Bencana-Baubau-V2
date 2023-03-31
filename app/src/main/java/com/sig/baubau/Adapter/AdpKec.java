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
import com.sig.baubau.DB.KecDB;
import com.sig.baubau.R;

import org.w3c.dom.Text;

public class AdpKec extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdpKec(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_kec, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDkec);
        holder.ListKode = (TextView)v.findViewById(R.id.listKodekec);
        holder.ListNama = (TextView)v.findViewById(R.id.listNamaKec);
        holder.ListLat = (TextView)v.findViewById(R.id.listLat);
        holder.ListLng = (TextView)v.findViewById(R.id.listLong);

        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(KecDB.row_id)));
        holder.ListKode.setText(cursor.getString(cursor.getColumnIndex(KecDB.row_kdKec)));
        holder.ListNama.setText(cursor.getString(cursor.getColumnIndex(KecDB.row_nmKec)));
        holder.ListLat.setText(cursor.getString(cursor.getColumnIndex(KecDB.row_lat)));
        holder.ListLng.setText(cursor.getString(cursor.getColumnIndex(KecDB.row_long)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListKode;
        TextView ListNama;
        TextView ListLat;
        TextView ListLng;
    }
}
