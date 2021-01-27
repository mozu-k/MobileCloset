package com.mozu_k.mobilecloset;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class TopsFragment extends Fragment {

    private ViewPager viewPager;
    private ListView listView;
    private OpenHelper helper;
    private SQLiteDatabase db;
    private int fragmentNumber = 0;
    private String[] categories = {"トップス","ボトムス","ワンピース","ジャケット","インナー","靴","バッグ","小物・アクセサリー"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_tops, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //上部のカテゴリー名を設定
        TextView categoryTitle = (TextView)view.findViewById(R.id.category_title);
        categoryTitle.setText((CharSequence) categories[fragmentNumber]);
        //fragmentNumber = viewPager.getCurrentItem();
        listView = (ListView)view.findViewById(R.id.listView);
        readData(view);
    }

    public void readData(View view){
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
}
