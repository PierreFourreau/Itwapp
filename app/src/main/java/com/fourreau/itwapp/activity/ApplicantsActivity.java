package com.fourreau.itwapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.ApplicantAllResponse;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.task.AllApplicantsTask;

import javax.inject.Inject;

import io.itwapp.models.Applicant;
import timber.log.Timber;

public class ApplicantsActivity extends ActionBarActivity implements ApplicantAllResponse {

    @Inject
    ApplicantService applicantService;

    private String idInterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);

        ((ItwApplication) getApplication()).inject(this);

        idInterview = ((ItwApplication) this.getApplication()).getInterviewId();

        //launch task which retrieve one interview
        AllApplicantsTask mTask = new AllApplicantsTask(ApplicantsActivity.this, applicantService, idInterview);
        mTask.delegate = this;
        mTask.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void processFinish(Applicant[] applicants){
        for(int i = 0; i < applicants.length; i++) {
            Timber.d(applicants[i].firstname);
        }
//        Timber.d("ApplicantsActivity:applicants retrieved : " + applicants.toString());
    }

    @Override
    public void onBackPressed() {

            super.onBackPressed();
    }

}
