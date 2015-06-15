package com.fourreau.itwapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.HomeActivity;
import com.fourreau.itwapp.activity.LicensesActivity;
import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by Pierre on 22/04/2015.
 */
public class AboutFragment extends Fragment {

    private FrameLayout frameLayout = null;
    private View view = null;

    private ButtonRectangle buttonOpenSourceProjects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        frameLayout = new FrameLayout(getActivity());
        view = inflater.inflate(R.layout.fragment_about, null);
        frameLayout .addView(view);

        buttonOpenSourceProjects = (ButtonRectangle) view.findViewById(R.id.see_projects_open_source_button);
        buttonOpenSourceProjects.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LicensesActivity.class);
                startActivity(intent);
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