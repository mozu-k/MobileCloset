package com.mozu_k.mobilecloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText name,price,brand;
    private Spinner category;
    private RadioGroup color;
    private String selectedColor = "";
    private OpenHelper helper;
    private SQLiteDatabase db;
    private int currentPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //開いていたカテゴリの番号を取得
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        this.currentPageNumber = extra.getInt("page");

        //カテゴリー用ドロップダウン
        String[] categories = {"トップス","ボトムス","ワンピース・セットアップ","ジャケット","インナー","靴","バッグ","小物・アクセサリー"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        for(int i = 0; i < categories.length; i++) {
            adapter.add(categories[i]);
        }
        this.category = (Spinner)findViewById(R.id.category);
        this.category.setAdapter(adapter);
        this.category.setSelection(currentPageNumber);  //初期値を開いていたカテゴリに自動で設定

        this.name = (EditText)findViewById(R.id.name);
        this.color = (RadioGroup) findViewById(R.id.color);
        this.price = (EditText)findViewById(R.id.price);
        this.brand = (EditText)findViewById(R.id.brand);

        Button enterButton = (Button)findViewById(R.id.enter);
        enterButton.setOnClickListener(this);
        Button cancelButton = (Button)findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);

        this.helper = new OpenHelper(getApplicationContext());
    }

    //アイテム追加
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.enter:
                Intent intent = new Intent();
                this.db = this.helper.getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put("title",this.name.getText().toString());
                values.put("category",this.category.getSelectedItem().toString());
                values.put("color",this.selectedColor);
                if(this.price.getText().toString().equals("") == false){
                    values.put("price",Integer.parseInt(this.price.getText().toString()));
                }else{
                    values.put("price",0);
                }
                values.put("brand",this.brand.getText().toString());

                db.insert("ClosetDB",null,values);

                setResult(RESULT_OK,intent);
                intent.putExtra("page",this.currentPageNumber);  //同じページに戻るためページ番号渡す
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