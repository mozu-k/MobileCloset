package com.mozu_k.mobilecloset;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //カテゴリー用ドロップダウン
        String[] categories = {"トップス","ボトムス","ワンピース","ジャケット","インナー","靴","バッグ","小物"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        for(int i = 0; i < categories.length; i++){
            adapter.add(categories[i]);
        }
        Spinner spinner = (Spinner)findViewById(R.id.category);
        spinner.setAdapter(adapter);
    }
}