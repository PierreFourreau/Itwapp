package com.fourreau.itwapp.core;

import android.app.Application;

import com.fourreau.itwapp.BuildConfig;
import com.fourreau.itwapp.core.module.AndroidModule;
import com.fourreau.itwapp.core.module.ApplicantModule;
import com.fourreau.itwapp.core.module.AuthenticationModule;
import com.fourreau.itwapp.core.module.InterviewModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 *
 * Application class.
 *
* Created by Pierre on 22/04/2015.
*/
public class ItwApplication extends Application {

    private ObjectGraph applicationGraph;

    public final static String EXTRA_ID_INTERVIEW = "com.fourreau.itwapp.idInterview";

    @Override public void onCreate() {
        super.onCreate();

        //init logger
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new AndroidModule(this), new InterviewModule(this), new ApplicantModule(this), new AuthenticationModule(this));
    }

    public void inject(Object target) {
        applicationGraph.inject(target);
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends Timber.HollowTree {
        @Override public void i(String message, Object... args) {
//            Crashlytics.log(message);
        }

        @Override public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override public void e(Throwable t, String message, Object... args) {
            e(message, args);

            // TODO e.g., Crashlytics.logException(t);
//            Crashlytics.logException(t);
        }
    }
}
