package com.fourreau.itwapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class LicensesActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.licenses_activity);

        // Get tracker.
        Tracker t = ((ItwApplication) getApplication()).getTracker(ItwApplication.TrackerName.APP_TRACKER);
        t.setScreenName("LicensesActivity");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        ((WebView)findViewById(R.id.web_view)).loadUrl("file:///android_asset/licenses.html");
    }
}