package com.example.carstore;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBmain extends SQLiteOpenHelper {

    public static final String DBNAME="car.db";
    public static final String TABLENAME="cars";
    public static final int ver=1;

    public DBmain(@Nullable Context context) {
        super(context, DBNAME, null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String qry= "create table "+TABLENAME+"(id integer primary key, avatar blob, brand text, model text,year text,price text)";

        sqLiteDatabase.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String qry= "drop table if exists "+TABLENAME+"";
    sqLiteDatabase.execSQL(qry);
    }
}
