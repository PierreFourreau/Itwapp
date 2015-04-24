package com.fourreau.itwapp.core;

import android.app.Application;

import com.fourreau.itwapp.core.module.AndroidModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
* Created by Pierre on 22/04/2015.
*/
public class ItwApplication extends Application {

    private ObjectGraph applicationGraph;

    @Override public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AndroidModule(this));
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }

    public void inject(Object target) {
        applicationGraph.inject(target);
    }
}
