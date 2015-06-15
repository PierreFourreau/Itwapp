package com.fourreau.itwapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

import com.fourreau.itwapp.R;

public class LicensesActivity extends ActionBarActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.licenses_activity);

        ((WebView)findViewById(R.id.web_view)).loadUrl("file:///android_asset/licenses.html");
    }
}