package com.mozu_k.mobilecloset;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addButton = (Button)findViewById(R.id.add);
        addButton.setOnClickListener(this);
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
}