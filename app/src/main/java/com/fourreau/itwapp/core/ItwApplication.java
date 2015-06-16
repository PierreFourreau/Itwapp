package com.fourreau.itwapp.core;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.fourreau.itwapp.BuildConfig;
import com.fourreau.itwapp.core.module.AndroidModule;
import com.fourreau.itwapp.core.module.ApplicantModule;
import com.fourreau.itwapp.core.module.AuthenticationModule;
import com.fourreau.itwapp.core.module.InterviewModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;
import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 *
 * Application class.
 *
* Created by Pierre on 22/04/2015.
*/
public class ItwApplication extends Application {

    private ObjectGraph applicationGraph;

    public String interviewId;
    public String applicantId;

    @Override public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        //init logger
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        applicationGraph = ObjectGraph.create(getModules().toArray());
    }

    public String getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(String interviewId) {
        this.interviewId = interviewId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
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
    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            // TODO e.g., Crashlytics.log(String.format(message, args));
            Crashlytics.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    Crashlytics.logException(t);
                } else if (priority == Log.WARN) {
                    Crashlytics.log(t.toString());
                }
            }
        }
    }
}
