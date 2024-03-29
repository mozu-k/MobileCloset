package com.mozu_k.mobilecloset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static ViewPager pager;
    public static PagerAdapter pagerAdapter;
    private static final int REQUEST_CODE = 1;
    private int currentPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewPager
        this.pager = (ViewPager) findViewById(R.id.pager);
        this.pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        this.pager.setAdapter(this.pagerAdapter);

        //addボタンが押されたとき
        ImageButton addButton = (ImageButton)findViewById(R.id.add);
        addButton.setOnClickListener(this);
    }

    //アイテム追加画面へ
    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.add:
                //カテゴリ設定用にページ番号渡す
                intent = new Intent(this,AddActivity.class);
                intent.putExtra("page",this.pager.getCurrentItem());
                startActivityForResult(intent,REQUEST_CODE);
                break;
        }
    }

    //メイン画面に戻ってきたとき
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: //return from add
                if(RESULT_OK == resultCode){
                    //元のページ番号知る
                    Bundle extra = data.getExtras();
                    this.currentPageNumber = extra.getInt("page");

                    this.pagerAdapter.notifyDataSetChanged();
                    break;
                }
        }
    }

    //メニューボタン実装
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //インフレーターを使ってメニューを表示させる
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        return true;
    }
}