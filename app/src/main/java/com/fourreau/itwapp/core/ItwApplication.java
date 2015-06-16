package com.fourreau.itwapp.core;

import android.app.Application;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.fourreau.itwapp.BuildConfig;
import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.module.AndroidModule;
import com.fourreau.itwapp.core.module.ApplicantModule;
import com.fourreau.itwapp.core.module.AuthenticationModule;
import com.fourreau.itwapp.core.module.InterviewModule;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.Arrays;
import java.util.HashMap;
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

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public synchronized Tracker getTracker(TrackerName trackerId) {

        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(R.xml.app_tracker);
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }


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
