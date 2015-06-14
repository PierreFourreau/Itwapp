package com.fourreau.itwapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.HomeActivity;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

/**
 * Created by Pierre on 22/04/2015.
 */
public class AboutFragment extends Fragment {

    private FrameLayout frameLayout = null;
    private View view = null;

    private Button buttonOpenSourceProjects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        frameLayout = new FrameLayout(getActivity());
        view = inflater.inflate(R.layout.fragment_about, null);
        frameLayout .addView(view);

        buttonOpenSourceProjects = (Button) view.findViewById(R.id.see_projects_open_source_button);
        buttonOpenSourceProjects.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new LibsBuilder()
                        //Pass the fields of your application to the lib so it can find all external lib information
                        .withFields(R.string.class.getFields())
                                //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                        .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                //start the activity
                        .start(getActivity());
            }
        });

        return frameLayout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(3);
    }
}