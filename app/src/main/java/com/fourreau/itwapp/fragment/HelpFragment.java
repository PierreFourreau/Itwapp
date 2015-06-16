package com.fourreau.itwapp.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.HomeActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.util.Utils;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import static com.fourreau.itwapp.R.id.web_view_help;

/**
 * Created by Pierre on 22/04/2015.
 */
public class HelpFragment extends Fragment {

    private FrameLayout frameLayout = null;
    private View view = null;
    private WebView webViewHelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get tracker.
        Tracker t = ((ItwApplication) getActivity().getApplication()).getTracker(ItwApplication.TrackerName.APP_TRACKER);
        t.setScreenName("HelpFragment");
        t.send(new HitBuilders.ScreenViewBuilder().build());

        frameLayout = new FrameLayout(getActivity());
        view = inflater.inflate(R.layout.fragment_help, null);
        frameLayout .addView(view);

        webViewHelp = (WebView) view.findViewById(web_view_help);
        webViewHelp.getSettings().setJavaScriptEnabled(true);
        webViewHelp.loadUrl(Utils.URL_HELP);

        return frameLayout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(2);
    }
}