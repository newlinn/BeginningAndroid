package com.beginningandroid.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beginningandroid.R;


public class LayoutActivity extends Activity {

    public static final String MAIN_ACTIVITY = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(MAIN_ACTIVITY, "onCreate");

        setContentView(R.layout.activity_layout);

        Button btnImplicit = (Button) findViewById(R.id.btnImplicit);
        btnImplicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐式的调用
                Intent intent = new Intent("TestInOne.action.TestLayout");
                intent.addCategory("TestInOne.category.TestLayout");
                startActivity(intent);
            }
        });

        Button btnWebBrowser = (Button) findViewById(R.id.btnWebBrowser);
        btnWebBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));

                startActivity(intent);
            }
        });

        Button btnBlank = (Button)findViewById(R.id.btnBlank);
        btnBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LayoutActivity.this, BlankActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        Button btnNormalActivity = (Button)findViewById(R.id.btnNormalActivity);
        btnNormalActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LayoutActivity.this, NormalActivity.class);
                startActivity(intent);
            }
        });

        Button btnDialogActivity = (Button)findViewById(R.id.btnDialogActivity);
        btnDialogActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LayoutActivity.this, DialogActivity.class);
                startActivity(intent);
            }
        });

        Button btnListView = (Button)findViewById(R.id.btnListView);
        btnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LayoutActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
        {
            String result = data.getStringExtra("ActivityResult");
            Toast.makeText(LayoutActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MAIN_ACTIVITY, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MAIN_ACTIVITY, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MAIN_ACTIVITY, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MAIN_ACTIVITY, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MAIN_ACTIVITY, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MAIN_ACTIVITY, "onRestart");
    }
}
