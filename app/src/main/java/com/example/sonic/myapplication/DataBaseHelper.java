package com.example.sonic.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="db_pesan.db";
    public static final String TABLE_NAME ="tb_outbox";

    public static final String COL_1="ID";
    public static final String COL_2="HP";
    public static final String COL_3="NAMA";
    public static final String COL_4="ISI";
    public static final String COL_5="TIME";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, HP TEXT, NAMA TEXT, ISI TEXT,TIME DATETIME  default current_timestamp)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public boolean insertData(String hp, String nama, String isi){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues konten=new ContentValues();
        konten.put(COL_2, hp);
        konten.put(COL_3,nama);
        konten.put(COL_4,isi);
        long result=db.insert(TABLE_NAME,null,konten);
        db.close();
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
}
