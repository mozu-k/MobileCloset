package com.mozu_k.mobilecloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private OpenHelper helper;
    private SQLiteDatabase db;
    private TextView first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new OpenHelper(getApplicationContext());

        Button addButton = (Button)findViewById(R.id.add);
        addButton.setOnClickListener(this);

        this.first = (TextView)findViewById(R.id.first);
        readData();
    }

    //アイテム追加画面へ
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.add:
                Intent intent = new Intent(this,AddActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void readData(){
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                "ClosetDB",
                new String[] {"title","category","color","price","brand"},
                null,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();

        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < cursor.getCount(); i++){
            sb.append(cursor.getString(1));
            sb.append("\n");
            sb.append(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();

        this.first.setText(sb.toString());
    }
}