package com.mozu_k.mobilecloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener{

    private OpenHelper helper;
    private SQLiteDatabase db;
    private TextView name,category,color,price,brand;
    private static final String TABLE_NAME = "ClosetDB";
    private static final String DB_NAME = "ClosetDB.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        int id = extra.getInt("id");

        this.name = (TextView)findViewById(R.id.name);
        this.category = (TextView)findViewById(R.id.category);
        this.color = (TextView)findViewById(R.id.color);
        this.price = (TextView)findViewById(R.id.price);
        this.brand = (TextView)findViewById(R.id.brand);

        Button cancelButton = (Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);

        helper = new OpenHelper(this);
        db = helper.getReadableDatabase();

        //String sql = "select * from " + TABLE_NAME + " where _id = ?;";

        //Cursor cursor = (Cursor)db.rawQuery(sql,new String[]{String.valueOf(id)});

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[] {"_id","title","category","color","price","brand"},
                "_id=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        cursor.moveToNext();

        this.name.setText(cursor.getString(1));
        this.category.setText(cursor.getString(2));
        this.color.setText(cursor.getString(3));
        this.price.setText(String.valueOf(cursor.getInt(4)));
        this.brand.setText(cursor.getString(5));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.edit:
            case R.id.delete:
            case R.id.cancel:
                finish();
        }
    }
}