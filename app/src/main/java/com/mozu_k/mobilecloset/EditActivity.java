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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private OpenHelper helper;
    private SQLiteDatabase db;
    private EditText name,price,brand;
    private RadioGroup color;
    private String selectedColor = "";
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
        this.color = (RadioGroup) findViewById(R.id.color);
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
        this.price.setText(String.valueOf(cursor.getInt(4)));
        this.brand.setText(cursor.getString(5));
        this.selectedColor = cursor.getString(3);

        switch(cursor.getString(3)){
            case "red":
                this.color.check(R.id.red);
                break;
            case "pink":
                this.color.check(R.id.pink);
                break;
            case "orange":
                this.color.check(R.id.orange);
                break;
            case "yellow":
                this.color.check(R.id.yellow);
                break;
            case "green":
                this.color.check(R.id.green);
                break;
            case "blue":
                this.color.check(R.id.blue);
                break;
            case "purple":
                this.color.check(R.id.purple);
                break;
            case "brown":
                this.color.check(R.id.brown);
                break;
            case "beige":
                this.color.check(R.id.beige);
                break;
            case "gray":
                this.color.check(R.id.gray);
                break;
            case "white":
                this.color.check(R.id.white);
                break;
            case "black":
                this.color.check(R.id.black);
                break;
            case "colorful":
                this.color.check(R.id.colorful);
                break;
        }

        //カテゴリー用ドロップダウン
        String[] categories = {"トップス","ボトムス","ワンピース・セットアップ","ジャケット","インナー","靴","バッグ","小物・アクセサリー"};
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
                values.put("color", this.selectedColor);
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

    public void onRadioButtonClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.red:
                if (checked){
                    this.selectedColor = "red";
                }
                break;
            case R.id.pink:
                if (checked){
                    this.selectedColor = "pink";
                }
                break;
            case R.id.orange:
                if(checked){
                    this.selectedColor = "orange";
                }
                break;
            case R.id.yellow:
                if(checked){
                    this.selectedColor = "yellow";
                }
                break;
            case R.id.green:
                if(checked){
                    this.selectedColor = "green";
                }
                break;
            case R.id.blue:
                if (checked){
                    this.selectedColor = "blue";
                }
                break;
            case R.id.purple:
                if (checked){
                    this.selectedColor = "purple";
                }
                break;
            case R.id.brown:
                if(checked){
                    this.selectedColor = "brown";
                }
                break;
            case R.id.beige:
                if(checked){
                    this.selectedColor = "beige";
                }
                break;
            case R.id.gray:
                if(checked){
                    this.selectedColor = "gray";
                }
                break;
            case R.id.white:
                if(checked){
                    this.selectedColor = "white";
                }
                break;
            case R.id.black:
                if(checked){
                    this.selectedColor = "black";
                }
                break;
            case R.id.colorful:
                if(checked){
                    this.selectedColor = "colorful";
                }
                break;
        }
    }
}