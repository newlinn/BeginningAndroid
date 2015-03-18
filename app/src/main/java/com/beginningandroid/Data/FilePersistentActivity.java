package com.beginningandroid.Data;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.beginningandroid.Activities.BaseActivity;

import com.beginningandroid.R;

public class FilePersistentActivity extends BaseActivity {

    EditText etPersistent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_persistent);
        etPersistent = (EditText)findViewById(R.id.etPersistent);
        String txt = ToFile.load(FilePersistentActivity.this);
        if (!TextUtils.isEmpty(txt)) {
            etPersistent.setText(ToFile.load(FilePersistentActivity.this));
            etPersistent.setSelection(txt.length());
            Toast.makeText(FilePersistentActivity.this, "restored text", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.btnSetSharedPreferences).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(FilePersistentActivity.class.getSimpleName(), MODE_PRIVATE).edit();
                editor.putString("name", "Tom");
                editor.putInt("age", 28);
                editor.putBoolean("married", false);
                editor.putString("comments", etPersistent.getText().toString());
                if (editor.commit()) {
                    Toast.makeText(FilePersistentActivity.this, "save to " + FilePersistentActivity.class.getSimpleName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnGetSharedPreferences).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(FilePersistentActivity.class.getSimpleName().toString(), MODE_PRIVATE);
                String txt = "name is " + sharedPreferences.getString("name", "");
                txt = txt + ", age is " + sharedPreferences.getInt("age", 0);
                txt = txt + ", is married " + sharedPreferences.getBoolean("married", true);
                txt = txt + ", comments is " + sharedPreferences.getString("comments", "");
                Toast.makeText(FilePersistentActivity.this, txt, Toast.LENGTH_SHORT).show();

            }
        });
        //this.getSharedPreferences();
        //this.getPreferences();
        //PreferenceManager.getDefaultSharedPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToFile.save(FilePersistentActivity.this, etPersistent.getText().toString());
    }


}
