package com.fourreau.itwapp.core.module;

import com.fourreau.itwapp.activity.AddApplicantActivity;
import com.fourreau.itwapp.activity.ApplicantDetailsActivity;
import com.fourreau.itwapp.activity.ApplicantsActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.fragment.AllInterviewsFragment;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.service.impl.ApplicantServiceImpl;
import com.fourreau.itwapp.service.impl.InterviewServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
* Created by Pierre on 22/04/2015.
*/
@Module(
        injects = {ApplicantsActivity.class, ApplicantDetailsActivity.class, AddApplicantActivity.class},
        complete = false,
        library = true
)
public class ApplicantModule {
    private final ItwApplication application;

    public ApplicantModule(ItwApplication application) {
        this.application = application;
    }

    @Provides @Singleton public ApplicantService provideApplicantService(ApplicantServiceImpl impl) {
        return impl;
    }
}
