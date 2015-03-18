package com.beginningandroid.Data;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.beginningandroid.Activities.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import com.beginningandroid.R;

public class SQLiteActivity extends BaseActivity {

    private MySQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase writeDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        sqLiteOpenHelper = new MySQLiteOpenHelper(this, "bookStore.db", null, 3);

        findViewById(R.id.btnGetWritableDB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行此方法的时候才调用sqLiteOpenHelper的onCreate
                writeDb = sqLiteOpenHelper.getWritableDatabase();
            }
        });
        bookLstView = (ListView) findViewById(R.id.lvBook);
        bookLstView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                final int pos = info.position;
                new AlertDialog.Builder(SQLiteActivity.this)
                        .setTitle("信息" )
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setMessage("位置" + pos)
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bookData.remove(pos);
                                bookAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
    }

    ListView bookLstView;
    //SimpleAdapter bookAdapter;
    //ArrayList<HashMap<String, String>> bookData;

    BookAdapter bookAdapter;
    ArrayList<Book> bookData;

    private void fillLstView() {

        if (bookData.isEmpty())
            return;
        /*
        bookAdapter = new SimpleAdapter(SQLiteActivity.this, bookData,
                R.layout.sqlite_lst_item,
                new String[]{"author", "price", "pages", "name"},
                new int[]{R.id.bookAuthor, R.id.bookPrice, R.id.bookPages, R.id.bookName}
        );
        */
        bookAdapter = new BookAdapter(SQLiteActivity.this, R.layout.sqlite_lst_item, bookData);
        bookLstView.setAdapter(bookAdapter);
    }

    public void addData(View v) {
        if (writeDb == null)
            writeDb = sqLiteOpenHelper.getWritableDatabase();

        writeDb.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            // 开始组装第一条数据
            values.put("name", "The Da Vinci Code" );
            values.put("author", "Dan Brown" );
            values.put("pages", 454);
            values.put("price", 16.96);
            writeDb.insert("Book", null, values); // 插入第一条数据
            values.clear();
            // 开始组装第二条数据
            values.put("name", "The Lost Symbol" );
            values.put("author", "Dan Brown" );
            values.put("pages", 510);
            values.put("price", 19.95);
            writeDb.insert("Book", null, values); // 插入第二条数据
            values.clear();
            writeDb.setTransactionSuccessful();
        }
        catch (Exception ex){

        }
        finally {
            writeDb.endTransaction();
        }
    }

    public void updateData(View v) {
        ContentValues values = new ContentValues();
        values.put("price", 19.95);
        writeDb.update("Book", values, "name=?", new String[]{"The DaVinci Code" });
    }

    public void delData(View v) {
        writeDb.delete("Book", "pages > ?", new String[]{"500" });
    }

    public void getData(View v) {
        if (bookData == null) {
            //bookData = new ArrayList<HashMap<String, String>>();
            bookData = new ArrayList<Book>();
        }
        else {
            //bookData.clear();
        }

        SQLiteDatabase readDB = sqLiteOpenHelper.getReadableDatabase();
        //  ( table, columns,  selection, selectionArgs,  groupBy,  having,  orderBy)
        Cursor cursor = readDB.query("Book", new String[]{"id, name, price, author, pages"}, "price > ?", new String[]{"0"}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                /*
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(cursor.getColumnIndex("id" )));
                map.put("author",cursor.getString(cursor.getColumnIndex("author")));
                map.put("name", cursor.getString(cursor.getColumnIndex("name" )));
                map.put("price", cursor.getString(cursor.getColumnIndex("price" )));
                map.put("pages", cursor.getString(cursor.getColumnIndex("pages" )));
                bookData.add(map);
                */
                Book book = new Book();
                book.id = cursor.getInt(cursor.getColumnIndex("id"));
                book.author = cursor.getString(cursor.getColumnIndex("author"));
                book.price = cursor.getDouble(cursor.getColumnIndex("price"));
                book.pages = cursor.getInt(cursor.getColumnIndex("pages"));
                book.name = cursor.getString(cursor.getColumnIndex("name"));
                bookData.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();

        fillLstView();
    }

    public class Book {
        public int id;
        public String author;
        public String name;
        public Double price;
        public int pages;
    }

    public class BookAdapter extends ArrayAdapter<Book> {

        private int resource;
        private Context context;

        public BookAdapter(Context context, int resource, List<Book> objects) {
            super(context, resource, objects);
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = getItem(position);
            View view;
            BookView bookView;
            if (convertView != null){
                view = convertView;
                bookView = (BookView)view.getTag();
            }
            else{
                view = LayoutInflater.from(getContext()).inflate(this.resource, null);
                bookView = new BookView();
                bookView.tvName = (TextView)view.findViewById(R.id.bookName);
                bookView.tvAuthor = (TextView)view.findViewById(R.id.bookAuthor);
                bookView.tvPages = (TextView)view.findViewById(R.id.bookPages);
                bookView.tvPrice =(TextView)view.findViewById(R.id.bookPrice);
                view.setTag(bookView);
            }

            bookView.tvName.setText(book.name);
            bookView.tvPrice.setText(book.price.toString());
            bookView.tvPages.setText(String.valueOf(book.pages));
            bookView.tvAuthor.setText(book.author);

            return view;
        }

        public class BookView{
            TextView tvName;
            TextView tvPrice;
            TextView tvPages;
            TextView tvAuthor;
        }
    }
}
