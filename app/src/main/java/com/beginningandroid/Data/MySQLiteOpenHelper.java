package com.beginningandroid.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by LingChen on 15/3/13.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_BOOK = "create table Book ("
            + "id integer primary key autoincrement, "
            + "author text, "
            + "price real, "
            + "pages integer, "
            + "name text)";

    public static final String CREATE_CATEGORY = "create table Category ("
            + "id integer primary key autoincrement, "
            + "category_name text, "
            + "category_code integer)";

    private Context context;

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库创建的时候执行
        db.execSQL(CREATE_BOOK);
        Toast.makeText(this.context, "CREATE_BOOK", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            db.execSQL(CREATE_CATEGORY);
            Toast.makeText(this.context, "CREATE_CATEGORY", Toast.LENGTH_SHORT).show();
        }
        if (newVersion == 3){
            db.execSQL("drop table if exists book");
            db.execSQL(CREATE_BOOK);
        }
    }
}
