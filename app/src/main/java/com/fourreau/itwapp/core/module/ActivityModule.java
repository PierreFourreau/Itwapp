//package com.fourreau.itwapp.core.module;
//
//import android.content.Context;
//
//import com.fourreau.itwapp.activity.HomeActivity;
//import com.fourreau.itwapp.core.ActivityTitleController;
//import com.fourreau.itwapp.core.ItwAppBaseActivity;
//import com.fourreau.itwapp.core.annotation.ForActivity;
//import com.fourreau.itwapp.fragment.Fragment1;
//
//import javax.inject.Singleton;
//
//import dagger.Module;
//import dagger.Provides;
//
///**
// * This module represents objects which exist only for the scope of a single activity. We can
// * safely create singletons using the activity instance because the entire object graph will only
// * ever exist inside of that activity.
// *
// * Created by Pierre on 22/04/2015.
// */
//@Module(
//        injects = {
//                HomeActivity.class,
//                Fragment1.class
//        },
//        addsTo = AndroidModule.class,
//        library = true
//)
//public class ActivityModule {
//    private final ItwAppBaseActivity activity;
//
//    public ActivityModule(ItwAppBaseActivity activity) {
//        this.activity = activity;
//    }
//
//    /**
//     * Allow the activity context to be injected but require that it be annotated with
//     * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
//     */
//    @Provides @Singleton @ForActivity
//    Context provideActivityContext() {
//        return activity;
//    }
//
//    @Provides @Singleton
//    ActivityTitleController provideTitleController() {
//        return new ActivityTitleController(activity);
//    }
//}
