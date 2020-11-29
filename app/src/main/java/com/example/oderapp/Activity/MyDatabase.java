package com.example.oderapp.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public void creadteTable(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }
    public Cursor selectData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        return cursor;
    }
    public Cursor selectDataOne(String selectSQL, String[] selectionArgs){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(selectSQL, selectionArgs);
        return cursor;

    }

    public long insertData(String table, String nullColumHack, ContentValues values){
        SQLiteDatabase database = getWritableDatabase();
        return database.insert(table, nullColumHack, values);
    }
    public int update(String table, ContentValues  values, String whereClause, String[] whereArgs){
        SQLiteDatabase database = getWritableDatabase();
        return  database.update(table, values, whereClause, whereArgs);
    }
    public int delete(String table, String whereClause, String[] whereArgs){
        SQLiteDatabase database = getWritableDatabase();
        return database.delete(table, whereClause, whereArgs);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

