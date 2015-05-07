package com.fourreau.itwapp.core.module;

import android.content.Context;
import android.location.LocationManager;

import com.fourreau.itwapp.activity.LoginActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.core.annotation.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.LOCATION_SERVICE;

/**
*
* A module for Android-specific dependencies which require a {@link Context} or
* {@link android.app.Application} to create.
 *
 * Created by Pierre on 22/04/2015.
 *
*/
@Module(
        injects = { LoginActivity.class},
        complete = false,
        library = true
)

public class AndroidModule {
    private final ItwApplication application;

    public AndroidModule(ItwApplication application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @Annotation} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton LocationManager provideLocationManager() {
        return (LocationManager) application.getSystemService(LOCATION_SERVICE);
    }
}
