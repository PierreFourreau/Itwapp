package com.fourreau.itwapp.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.service.InterviewService;

import javax.inject.Inject;

import io.itwapp.models.Interview;

/**
 * Created by Pierre on 15/04/2015.
 */
public class AllInterviewsTask extends AsyncTask<String, Void, Interview[]> {

    private InterviewService interviewService;

    public AllInterviewsTask(InterviewService interviewService){
        this.interviewService = interviewService;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Interview[] doInBackground(String... params) {
        Interview[] interviews = interviewService.getAllInterviews();
        for(int i = 0; i < interviews.length; i++) {
            Log.d("", "interview" + interviews[i].name);
        }
        return interviews;
    }

    @Override
    protected void onPostExecute(Interview[] interviews) {
        Log.d("","Number of interviews retrieved : " + interviews.length);
    }
}