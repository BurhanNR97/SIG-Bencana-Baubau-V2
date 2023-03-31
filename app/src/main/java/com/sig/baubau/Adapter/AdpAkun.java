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
import com.sig.baubau.DB.UserDB;
import com.sig.baubau.R;

public class AdpAkun extends CursorAdapter {
    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AdpAkun(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_akun, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listIDakun);
        holder.ListKode = (TextView)v.findViewById(R.id.listKodeuser);
        holder.ListUsername = (TextView)v.findViewById(R.id.listUsername);
        holder.ListPassword = (TextView)v.findViewById(R.id.listPassword);

        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();
        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(UserDB.row_id)));
        holder.ListKode.setText(cursor.getString(cursor.getColumnIndex(UserDB.row_nik)));
        holder.ListUsername.setText(cursor.getString(cursor.getColumnIndex(UserDB.row_email)));
        holder.ListPassword.setText(cursor.getString(cursor.getColumnIndex(UserDB.row_password)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListKode;
        TextView ListUsername;
        TextView ListPassword;
    }
}
