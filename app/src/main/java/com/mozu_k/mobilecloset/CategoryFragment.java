package com.mozu_k.mobilecloset;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class CategoryFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ViewPager viewPager;
    private ListView listView;
    private OpenHelper helper;
    private SQLiteDatabase db;
    private int fragmentNumber = 0;
    private String[] categories = {"トップス","ボトムス","ワンピース","ジャケット","インナー","靴","バッグ","小物・アクセサリー"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //上部のカテゴリー名を設定
        TextView categoryTitle = (TextView)view.findViewById(R.id.category_title);
        categoryTitle.setText((CharSequence) categories[fragmentNumber]);

        listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        readData();
    }

    public void setFragmentNumber(int fragmentNumber){
        this.fragmentNumber = fragmentNumber;
    }

    public void readData(){
        helper = new OpenHelper(getContext());
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                "ClosetDB",
                new String[] {"_id","title","category","color","price","brand"},
                "category=?",
                new String[]{categories[fragmentNumber]},
                null,
                null,
                null
        );

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getContext(),
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {"title", "brand"},
                new int[] {android.R.id.text1, android.R.id.text2},
                0);

        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        intent.putExtra("id",(int)id);
        startActivity(intent);
    }
}
