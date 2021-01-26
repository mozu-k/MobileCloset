package com.mozu_k.mobilecloset;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;

public class OpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "ClosetDB.db";
    private static final String TABLE_NAME = "ClosetDB";

    //テーブル新規作成
   private static final String SQL_CREATE_TABLE =
            "CREATE TABLE ClosetDB(_id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,category TEXT,color TEXT,price integer,brand TEXT)";

   //テーブル削除
    private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    //コンストラクタ
    OpenHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE);
        onCreate(db);
    }
}
