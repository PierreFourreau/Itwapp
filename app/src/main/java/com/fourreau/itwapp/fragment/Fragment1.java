package com.fourreau.itwapp.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.HomeActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.task.AllInterviewsTask;

import javax.inject.Inject;

/**
 * Created by Pierre on 22/04/2015.
 */
public class Fragment1 extends Fragment {

//    @Inject
//    InterviewService interviewService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        ((ItwApplication)getActivity().getApplication()).inject(this);

//        setRetainInstance(true);

        //launch task which retrieve all interviews
//        AllInterviewsTask mTask = new AllInterviewsTask(getActivity(), interviewService);
//        mTask.execute();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(1);
    }

    @Override public void onResume() {
        super.onResume();

        // Fragments should not modify things outside of their own view. Use an external controller to
        // ask the activity to change its title.
    }
}