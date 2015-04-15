package com.fourreau.itwapp.sync;

import android.os.AsyncTask;

import com.fourreau.itwapp.service.impl.InterviewServiceImpl;

import io.itwapp.models.Interview;

/**
 * Created by Pierre on 15/04/2015.
 */
public class AllInterviews extends AsyncTask<String, Void, Interview[]> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Interview[] doInBackground(String... params) {
        //TODO : inject service
        InterviewServiceImpl interviewService = new InterviewServiceImpl();

        return interviewService.getAllInterviews();
    }

    @Override
    protected void onPostExecute(Interview[] interviews) {

    }
}