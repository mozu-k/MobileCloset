package com.mozu_k.mobilecloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private OpenHelper helper;
    private SQLiteDatabase db;
    private ListView listView;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new OpenHelper(getApplicationContext());

        Button addButton = (Button)findViewById(R.id.add);
        addButton.setOnClickListener(this);

        this.listView = (ListView)findViewById(R.id.listView);
        readData();
    }

    //アイテム追加画面へ
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.add:
                Intent intent = new Intent(this,AddActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:  //AddActivity
                if (RESULT_OK == resultCode) {
                    readData();
                }
                break;
        }
    }

    public void readData(){
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                "ClosetDB",
                new String[] {"_id","title","category","color","price","brand"},
                null,
                null,
                null,
                null,
                null
        );

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {"title", "category"},
                new int[] {android.R.id.text1, android.R.id.text2},
                0);

        this.listView.setAdapter(adapter);
    }
}