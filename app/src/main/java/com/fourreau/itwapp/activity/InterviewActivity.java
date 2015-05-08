package com.fourreau.itwapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.InterviewOneResponse;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.task.OneInterviewTask;
import com.gc.materialdesign.views.ButtonRectangle;

import javax.inject.Inject;

import io.itwapp.models.Interview;
import timber.log.Timber;

public class InterviewActivity extends ActionBarActivity implements InterviewOneResponse {

    @Inject
    InterviewService interviewService;

    private String idInterview;

    private ButtonRectangle seeApplicantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ItwApplication) getApplication()).inject(this);

        setContentView(R.layout.activity_interview);

        idInterview = ((ItwApplication) this.getApplication()).getInterviewId();

        Timber.d("InterviewActivity: id interview " + idInterview);
        Toast.makeText(this, idInterview, Toast.LENGTH_SHORT).show();


        //launch task which retrieve one interview
        OneInterviewTask mTask = new OneInterviewTask(InterviewActivity.this, interviewService, idInterview);
        mTask.delegate = this;
        mTask.execute();



        //set interview informations
//        interview.name

        //see applicants button
        seeApplicantsButton = (ButtonRectangle) findViewById(R.id.see_applicants_button);
        seeApplicantsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InterviewActivity.this, ApplicantsActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void processFinish(Interview interview){
        Timber.d("InterviewActivity:interview retrieved : " + interview.name);
    }

}
