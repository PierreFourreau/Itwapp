package com.fourreau.itwapp.core.module;

import com.fourreau.itwapp.fragment.Fragment1;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.service.impl.InterviewServiceImpl;

import dagger.Module;
import dagger.Provides;

/**
* Created by Pierre on 22/04/2015.
*/
@Module(injects = Fragment1.class)
public class InterviewModule {
    @Provides
    public InterviewService providesAuthenticationService() {
        return new InterviewServiceImpl();
    }
}