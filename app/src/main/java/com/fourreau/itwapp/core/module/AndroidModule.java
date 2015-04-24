package com.fourreau.itwapp.core.module;

import android.content.Context;

import com.fourreau.itwapp.activity.HomeActivity;
import com.fourreau.itwapp.activity.LoginActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.fragment.Fragment1;
import com.fourreau.itwapp.service.AuthenticationService;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.service.impl.AuthenticationServiceImpl;
import com.fourreau.itwapp.service.impl.InterviewServiceImpl;
import com.fourreau.itwapp.task.AllInterviewsTask;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
*
* A module for Android-specific dependencies which require a {@link Context} or
* {@link android.app.Application} to create.
 *
 * Created by Pierre on 22/04/2015.
 *
*/
@Module(
        injects = { LoginActivity.class, Fragment1.class},
        complete = false,
        library = true
)

public class AndroidModule {
    private final ItwApplication application;

    public AndroidModule(ItwApplication application) {
        this.application = application;
    }

    @Provides @Singleton public AuthenticationService provideAuthenticationService(AuthenticationServiceImpl impl) {
        return impl;
    }

    @Provides @Singleton public InterviewService provideInterviewService(InterviewServiceImpl impl) {
        return impl;
    }
}
