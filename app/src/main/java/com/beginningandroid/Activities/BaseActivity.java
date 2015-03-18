package com.beginningandroid.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.beginningandroid.R;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends Activity {

    protected static List<Activity> activities = new ArrayList<Activity>();

    public static void AddActivity(Activity activity) {
        activities.add(activity);
    }

    public static void RemoveActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void FinishAllActivity() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) activity.finish();
        }
    }

    public static void Start(Context context, Bundle bundle) {
        Intent intent = new Intent(context, BaseActivity.class);
        intent.putExtra("Bundle", bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
        activities.add(this);
        setContentView(R.layout.activity_base);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activities.remove(this);
    }

}
