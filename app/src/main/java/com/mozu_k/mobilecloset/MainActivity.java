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

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private static final int REQUEST_CODE = 1;
    private int currentPageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //viewPager
        pager = (ViewPager) findViewById(R.id.pager);
        this.pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pager.setAdapter(this.pagerAdapter);

        //ボタンが押されたとき
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
            case REQUEST_CODE:  //AddActivityから決定で戻るとき
                if (RESULT_OK == resultCode) {
                    //元のページ番号知る
                    Bundle extra = data.getExtras();
                    this.currentPageNumber = extra.getInt("page");

                    PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
                    pager.setAdapter(pagerAdapter);
                    pager.setCurrentItem(this.currentPageNumber); //最初と同じページを初期表示にする
                }
                break;
        }
    }
}