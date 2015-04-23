package com.fourreau.itwapp.core.module;

import android.content.Context;

import com.fourreau.itwapp.activity.LoginActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.service.AuthenticationService;
import com.fourreau.itwapp.service.impl.AuthenticationServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
*
* Created by Pierre on 22/04/2015.
*
* A module for Android-specific dependencies which require a {@link Context} or
* {@link android.app.Application} to create.
*/
/**
 * A module for Android-specific dependencies which require a {@link Context} or
 * {@link android.app.Application} to create.
 */
@Module(
        injects = { LoginActivity.class },
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
     * {@link ForApplication @ForApplication} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton public AuthenticationService provideSampleHelper(AuthenticationServiceImpl impl) {
        return impl;
    }
}
