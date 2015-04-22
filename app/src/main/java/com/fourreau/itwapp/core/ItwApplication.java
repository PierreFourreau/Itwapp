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
public class ItwApplication extends Application implements Injector {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidAppModule sharedAppModule = new AndroidAppModule();

        // bootstrap. So that it allows no-arg constructor in AndroidAppModule
        sharedAppModule.sApplicationContext = this.getApplicationContext();

        List<Object> modules = new ArrayList<Object>();
        modules.add(sharedAppModule);
        //modules.add(new UserAccountModule());
        //modules.add(new ThreadingModule());
        modules.addAll(getAppModules());

        mObjectGraph = ObjectGraph.create(modules.toArray());

        mObjectGraph.inject(this);
    }

    protected abstract List<Object> getAppModules();

    @Override
    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

    @Override
    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }
}
