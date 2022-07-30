package com.mozu_k.mobilecloset;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener,AlertDialogFragment.AlertDialogListener{

    private OpenHelper helper;
    private SQLiteDatabase db;
    private TextView name, category, color, price, brand;
    private static final String TABLE_NAME = "ClosetDB";
    private int id, currentPageNumber;
    private static final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //開いていたカテゴリの番号,アイテムのＩＤを取得
        Intent intent = getIntent();
        Bundle extra = intent.getExtras();
        this.id = extra.getInt("id");
        this.currentPageNumber = extra.getInt("page");

        this.name = (TextView) findViewById(R.id.name);
        this.category = (TextView) findViewById(R.id.category);
        this.color = (TextView) findViewById(R.id.color);
        this.price = (TextView) findViewById(R.id.price);
        this.brand = (TextView) findViewById(R.id.brand);

        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(this);
        Button editButton = (Button) findViewById(R.id.edit);
        editButton.setOnClickListener(this);
        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(this);

        this.helper = new OpenHelper(this);
        this.db = helper.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{"_id", "title", "category", "color", "price", "brand"},
                "_id=?",
                new String[]{String.valueOf(this.id)},
                null,
                null,
                null
        );

        cursor.moveToNext();

        this.name.setText(cursor.getString(1));
        this.category.setText(cursor.getString(2));
        this.color.setText(cursor.getString(3));
        this.price.setText(String.valueOf(cursor.getInt(4)) + "円");
        this.brand.setText(cursor.getString(5));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit:
                Intent intent = new Intent(this, EditActivity.class);
                this.db = this.helper.getWritableDatabase();
                intent.putExtra("id", this.id);
                intent.putExtra("page", this.currentPageNumber);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.delete:
                //削除確認ウィンドウ出す
                DialogFragment dialog = new AlertDialogFragment();
                dialog.show(getSupportFragmentManager(), "AlertDialogFragment");
                break;
            case R.id.cancel:
                finish();
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (RESULT_OK == resultCode) {
                    MainActivity.pagerAdapter.notifyDataSetChanged();
                    finish();
                }
                break;
        }
    }

    public void deleteData() {
        this.db.delete(TABLE_NAME, "_id=?", new String[]{String.valueOf(this.id)});
    }

    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button
        deleteData();
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        intent.putExtra("page",this.currentPageNumber);  //同じページに戻るためページ番号渡す
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button
    }
}