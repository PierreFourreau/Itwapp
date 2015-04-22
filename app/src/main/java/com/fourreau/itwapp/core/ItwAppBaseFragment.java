//package com.fourreau.itwapp.core;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//
///**
// * Base fragment which performs injection using the activity object graph of its parent.
// *
// * Created by Pierre on 22/04/2015.
// */
//public class ItwAppBaseFragment extends Fragment {
//    @Override public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        ((ItwAppBaseActivity) getActivity()).inject(this);
//    }
//}