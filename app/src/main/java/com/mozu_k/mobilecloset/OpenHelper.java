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
    private static final String COLUMN_NAME1 = "_id";
    private static final String COLUMN_NAME2 = "title";
    private static final String COLUMN_NAME3 = "category";
    private static final String COLUMN_NAME4 = "color";
    private static final String COLUMN_NAME5 = "price";
    private static final String COLUMN_NAME6 = "brand";

    //テーブル新規作成
   private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME1 + "INTEGER PRIMARY KEY," +
            COLUMN_NAME2 + " TEXT," + COLUMN_NAME3 + " TEXT," + COLUMN_NAME4 + "TEXT," +
            COLUMN_NAME5 + " INTEGER," + COLUMN_NAME6 + " TEXT)";

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

    public void addData(SQLiteDatabase db,Item item){
        ContentValues values = new ContentValues();
        for(Field field: item.getClass().getDeclaredFields()){
            try{
                field.setAccessible(true);
                values.put(field.getName(), (byte[]) field.get(item));
            }catch(IllegalAccessException e){
                values.put(field.getName(),"access denied");
            }
        }
        db.insert(DB_NAME,null,values);
    }
}
