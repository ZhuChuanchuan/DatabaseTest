package com.zhcc.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);

        Button btnCreateDatabase = (Button) findViewById(R.id.create_database);
        btnCreateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        Button btnAddData = (Button) findViewById(R.id.add_data);
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("name", "haha");
                values.put("author", "tom");
                values.put("pages", 454);
                values.put("price", 13.33);
                db.insert("Book", null, values);
                values.clear();

                //插入第二条
                values.put("name", "gost");
                values.put("author", "jerry");
                values.put("pages", 222);
                values.put("price", 24.22);
                db.insert("Book", null, values);
            }
        });

        Button btnUpdateData = (Button) findViewById(R.id.update_data);
        btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("price", 10.99);
                db.update("Book",values,"name=?",new String[]{"haha"});
            }
        });

        Button btnDeleteData = (Button) findViewById(R.id.delete_data);
        btnDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                db.delete("Book", "pages>?", new String[]{"400"});
            }
        });

        Button btnQueryData = (Button) findViewById(R.id.query_data);
        btnQueryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    do {
                        //遍历cursor
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("mainactivity", name + " " + author + " " + pages + " " + price);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
    }
}
