package com.fourreau.itwapp.core.module;

import com.fourreau.itwapp.activity.AddInterviewActivity;
import com.fourreau.itwapp.activity.EditInterviewActivity;
import com.fourreau.itwapp.activity.InterviewActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.fragment.AllInterviewsFragment;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.service.impl.InterviewServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
* Created by Pierre on 22/04/2015.
*/
@Module(
        injects = {AllInterviewsFragment.class, InterviewActivity.class, AddInterviewActivity.class, EditInterviewActivity.class},
        complete = false,
        library = true
)
public class InterviewModule {
    private final ItwApplication application;

    public InterviewModule(ItwApplication application) {
        this.application = application;
    }

    @Provides @Singleton public InterviewService provideInterviewService(InterviewServiceImpl impl) {
        return impl;
    }
}
