//package com.fourreau.itwapp.activity;
//
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
//
//import javax.inject.Inject;
//
///**
// * Created by Pierre on 23/04/2015.
// */
//public abstract class ActionBarBaseActivity extends ActionBarActivity {
//
//    @Inject
//    LocationManager locationManager;
//
//    @Override protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // After the super.onCreate call returns we are guaranteed our injections are available.
//
////        if (savedInstanceState == null) {
////            getSupportFragmentManager().beginTransaction()
////                    .add(android.R.id.content, HomeFragment.newInstance())
////                    .commit();
////        }
//
//        // TODO do something with the injected dependencies here!
//    }
//}
