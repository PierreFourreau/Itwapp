package com.fourreau.itwapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.sync.AllInterviews;

import io.itwapp.Itwapp;

public class LoginActivity extends ActionBarActivity {

    private static final String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Itwapp.apiKey = "3bf8bce4b8b0ac18a0a4669ec58ed03f";
        Itwapp.secretKey = "450425436db428e7d04288d592c1e771c82f9747";

        //get all interviews
        new AllInterviews().execute();

        Log.d(TAG, "foo");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
}
