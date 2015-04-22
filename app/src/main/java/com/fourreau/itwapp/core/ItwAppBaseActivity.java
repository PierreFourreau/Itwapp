package com.fourreau.itwapp.core;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Pierre on 22/04/2015.
 */
public abstract class ItwAppBaseActivity extends Activity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Perform injection so that when this call returns all dependencies will be available for use.
        ((ItwApplication) getApplication()).inject(this);
    }
}