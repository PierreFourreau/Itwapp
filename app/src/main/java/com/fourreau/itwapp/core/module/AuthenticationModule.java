package com.fourreau.itwapp.core.module;

import com.fourreau.itwapp.activity.LoginActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.service.AuthenticationService;
import com.fourreau.itwapp.service.impl.AuthenticationServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
* Created by Pierre on 22/04/2015.
*/
@Module(
        injects = {LoginActivity.class},
        complete = false,
        library = true
)
public class AuthenticationModule {
    private final ItwApplication application;

    public AuthenticationModule(ItwApplication application) {
        this.application = application;
    }


    @Provides @Singleton
    public AuthenticationService provideAuthenticationService(AuthenticationServiceImpl impl) {
        return impl;
    }
}
