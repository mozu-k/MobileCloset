package com.mozu_k.mobilecloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private OpenHelper helper;
    private SQLiteDatabase db;
    private EditText name,color,price,brand;
    private Spinner category;
    private static final String TABLE_NAME = "ClosetDB";
    private int id,currentPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button enterButton = (Button)findViewById(R.id.enter);
        enterButton.setOnClickListener(this);
        Button cancelButton = (Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        this.id = extra.getInt("id");
        this.currentPageNumber = extra.getInt("page");

        this.name = (EditText)findViewById(R.id.name);
        this.category = (Spinner)findViewById(R.id.category);
        this.color = (EditText)findViewById(R.id.color);
        this.price = (EditText)findViewById(R.id.price);
        this.brand = (EditText)findViewById(R.id.brand);

        helper = new OpenHelper(this);
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[] {"_id","title","category","color","price","brand"},
                "_id=?",
                new String[]{String.valueOf(this.id)},
                null,
                null,
                null
        );

        cursor.moveToNext();

        this.name.setText(cursor.getString(1));
        this.color.setText(cursor.getString(3));
        this.price.setText(String.valueOf(cursor.getInt(4)));
        this.brand.setText(cursor.getString(5));

        //カテゴリー用ドロップダウン
        String[] categories = {"トップス","ボトムス","ワンピース","ジャケット","インナー","靴","バッグ","小物・アクセサリー"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        for(int i = 0; i < categories.length; i++) {
            adapter.add(categories[i]);
        }
        this.category.setAdapter(adapter);
        this.category.setSelection(currentPageNumber);  //初期値を開いていたカテゴリに自動で設定
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.enter:
                Intent intent = new Intent();
                this.db = this.helper.getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put("title", this.name.getText().toString());
                values.put("category", this.category.getSelectedItem().toString());
                values.put("color", this.color.getText().toString());
                if (this.price.getText().toString().equals("") == false) {
                    values.put("price", Integer.parseInt(this.price.getText().toString()));
                } else {
                    values.put("price", 0);
                }
                values.put("brand", this.brand.getText().toString());

                this.db.update("ClosetDB", values,"_id=?",new String[]{String.valueOf(this.id)});

                setResult(RESULT_OK,intent);
                intent.putExtra("id",this.id);
                finish();

            case R.id.cancel:
                finish();
        }
    }
}