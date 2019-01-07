package com.example.sonic.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class listFragment extends Fragment {

    private SQLiteDatabase newDB;
    private String tableName=DataBaseHelper.TABLE_NAME;
    ListView lv;
    ArrayList<String> adapter= new ArrayList<String>();;

    public listFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(this.getContext());
            newDB = dbHelper.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT ID, HP, NAMA,ISI,TIME FROM " +
                    tableName + " ORDER BY ID Desc", null);
            Log.d("sql:","buka db");

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String id = c.getString(c.getColumnIndex("ID"));
                        String hp = c.getString(c.getColumnIndex("HP"));
                        String nama = c.getString(c.getColumnIndex("NAMA"));
                        String pesan = c.getString(c.getColumnIndex("ISI"));
                        adapter.add(id+"|"+hp+"|"+nama+"|"+pesan);

                    } while (c.moveToNext());
                }
            }
        }catch(SQLiteException s){
            Log.d("sql:","database tdk ada");
        }finally {
            if(newDB !=null){
                newDB.close();
            }
        }

        View view= inflater.inflate(R.layout.fragment_list, container, false);
        lv = (ListView) view.findViewById(R.id.list1);
        ArrayAdapter  <String> tampil = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, adapter);
        lv.setAdapter(tampil);
        return view;


    }



}
