package com.mozu_k.mobilecloset;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OpenHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "ClosetDB.db";
    private static final String TABLE_NAME = "ClosetDB";
    private static final String _ID = "_id";
    private static final String COLUMN_NAME1 = "title";
    private static final String COLUMN_NAME2 = "category";
    private static final String COLUMN_NAME3 = "color";
    private static final String COLUMN_NAME4 = "price";
    private static final String COLUMN_NAME5 = "brand";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" + _ID + "INTEGER PRIMARY KEY," +
            COLUMN_NAME1 + " TEXT," + COLUMN_NAME2 + " TEXT," + COLUMN_NAME3 + "TEXT," +
            COLUMN_NAME4 + " INTEGER," + COLUMN_NAME5 + " TEXT)";

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

    }
}
