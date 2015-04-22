package com.fourreau.itwapp.core;

import android.app.Application;

import com.fourreau.itwapp.core.module.AndroidModule;
import com.fourreau.itwapp.core.module.AuthenticationModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
* Created by Pierre on 22/04/2015.
*/
public class ItwApplication extends Application {

    private ObjectGraph graph;

    @Override public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(getModules().toArray());
    }

    protected List<Object> getModules() {
        return Arrays.asList(
                new AndroidModule(this),
                new AuthenticationModule()
        );
    }

    public void inject(Object object) {
        graph.inject(object);
    }
}
