package com.beginningandroid.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.beginningandroid.R;

public class ListViewActivity extends BaseActivity {
    private String[] fruitNames = {"Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};

    private ArrayList<Fruit> fruits;
    private ListView fruitLstV;

    static int flagLoadData = 1001;
    private Handler uiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (flagLoadData == msg.what){
                FruitAdapter fruitAdapter = new FruitAdapter(ListViewActivity.this,
                        R.layout.activity_fruit_lst_item, fruits);
                fruitLstV.setAdapter(fruitAdapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListViewActivity.this, android.R.layout.simple_list_item_1, fruitNames);
        fruitLstV = (ListView) findViewById(R.id.LstView);
        fruitLstV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = fruits.get(position);
                Toast.makeText(ListViewActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        //lstView.setAdapter(adapter);
        new Thread(){
            @Override
            public void run() {
                fruits = new ArrayList<Fruit>();
                fruits.add(new Fruit("Apple", R.drawable.apple_pic));
                fruits.add(new Fruit("Banana", R.drawable.banana_pic));
                fruits.add(new Fruit("Orange", R.drawable.orange_pic));
                fruits.add(new Fruit("Watermelon", R.drawable.watermelon_pic));
                fruits.add(new Fruit("Pear", R.drawable.pear_pic));
                fruits.add(new Fruit("Grape", R.drawable.grape_pic));
                fruits.add(new Fruit("Pineapple", R.drawable.pineapple_pic));
                fruits.add(new Fruit("Strawberry", R.drawable.strawberry_pic));
                fruits.add(new Fruit("Cherry", R.drawable.cherry_pic));
                fruits.add(new Fruit("Mango", R.drawable.mango_pic));
                Message msg = new Message();
                msg.what = flagLoadData;
                uiHandler.sendMessageDelayed(msg, 1000);
            }
        }.start();



    }

}

