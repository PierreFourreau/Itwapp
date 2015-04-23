package com.fourreau.itwapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.view.Gravity.CENTER;

/**
 * Created by Pierre on 22/04/2015.
 */
public class Fragment1 extends Fragment {
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment1, container, false);
//    }

    public static Fragment1 newInstance() {
        return new Fragment1();
    }


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        TextView tv = new TextView(getActivity());
        tv.setGravity(CENTER);
        tv.setText("Hello, World");
        return tv;
    }

    @Override public void onResume() {
        super.onResume();

        // Fragments should not modify things outside of their own view. Use an external controller to
        // ask the activity to change its title.
    }
}