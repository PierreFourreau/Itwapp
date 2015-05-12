package com.fourreau.itwapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.InterviewOneResponse;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.task.OneInterviewTask;
import com.gc.materialdesign.views.ButtonFloat;
import com.gc.materialdesign.views.ButtonRectangle;

import javax.inject.Inject;

import io.itwapp.models.Interview;
import timber.log.Timber;

public class InterviewActivity extends ActionBarActivity implements InterviewOneResponse {

    @Inject
    InterviewService interviewService;

    private String idInterview;

    private TextView textViewName, textViewDescription, textViewVideo;
    private ButtonRectangle seeApplicantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ItwApplication) getApplication()).inject(this);

        setContentView(R.layout.activity_interview);

        idInterview = ((ItwApplication) this.getApplication()).getInterviewId();

        textViewName = (TextView) findViewById(R.id.activity_interview_name);
        textViewDescription = (TextView) findViewById(R.id.activity_interview_description);
        textViewVideo = (TextView) findViewById(R.id.activity_interview_video);

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
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    public void processFinish(Interview interview){
        Timber.d("InterviewActivity:interview retrieved : " + interview.name);
        textViewName.setText(interview.name);
        //description
        if(!interview.text.isEmpty()) {
            textViewDescription.setText(interview.text);
        }
        else {
            textViewDescription.setText(R.string.none);
        }
        //video
        if(!interview.video.isEmpty()) {
            textViewVideo.setText(interview.video);
        }
        else {
            textViewVideo.setText(R.string.none);
        }
    }
}
