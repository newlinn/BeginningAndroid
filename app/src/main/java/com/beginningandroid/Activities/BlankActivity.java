package com.beginningandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.beginningandroid.R;


public class BlankActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        Button btnFinish = (Button)findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToActivity();
            }
        });
    }



    @Override
    public void onBackPressed() {
        backToActivity();
    }

    private void backToActivity()
    {
        Intent intent = new Intent();
        intent.putExtra("ActivityResult", "Blank Activity finished!");
        setResult(RESULT_OK, intent);
        finish();
    }
}
