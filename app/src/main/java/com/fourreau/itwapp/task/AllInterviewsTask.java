package com.fourreau.itwapp.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fourreau.itwapp.service.InterviewService;

import io.itwapp.models.Interview;
import timber.log.Timber;

/**
 * Created by Pierre on 15/04/2015.
 */
public class AllInterviewsTask extends AsyncTask<String, Void, Interview[]> {

    private Context mContext;
    private InterviewService interviewService;

    public AllInterviewsTask(Context context, InterviewService interviewService){
        this.mContext = context;
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
            Timber.d("Interview : " + interviews[i].name);
        }
        return interviews;
    }

    @Override
    protected void onPostExecute(Interview[] interviews) {
        Timber.d("Number of interviews retrieved : " + interviews.length);
        Toast.makeText(mContext, "Number of interviews retrieved : " + interviews.length, Toast.LENGTH_LONG).show();
    }
}