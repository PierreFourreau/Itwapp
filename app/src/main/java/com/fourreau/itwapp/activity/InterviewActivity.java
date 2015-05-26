package com.fourreau.itwapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.ApplicantAllResponse;
import com.fourreau.itwapp.model.DeleteInterviewResponse;
import com.fourreau.itwapp.model.InterviewOneResponse;
import com.fourreau.itwapp.model.UpdateInterviewResponse;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.task.AllApplicantsTask;
import com.fourreau.itwapp.task.DeleteInterviewTask;
import com.fourreau.itwapp.task.OneInterviewTask;
import com.gc.materialdesign.views.ButtonFloat;

import javax.inject.Inject;

import io.itwapp.models.Applicant;
import io.itwapp.models.Interview;
import io.itwapp.models.Question;
import timber.log.Timber;

public class InterviewActivity extends ActionBarActivity implements InterviewOneResponse, DeleteInterviewResponse, UpdateInterviewResponse {

    @Inject
    InterviewService interviewService;

    private Interview interview;

    private String idInterview;

    private TextView textViewName, textViewDescription, textViewVideoNone, textViewCallback, textViewQuestions;
    private ImageButton imageButtonYoutube;
    private ButtonFloat seeApplicantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ItwApplication) getApplication()).inject(this);

        setContentView(R.layout.activity_interview);

        initView();

        //launch task which retrieve one interview
        launchTask();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.interview, menu);
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
            case R.id.action_edit:
                if(interview != null) {
                    if (interview.sent > 0) {
                        showAlertDialogInfo(R.string.dialog_title_generic_error, R.string.dialog_title_update_forbidden);
                    } else {
                        Intent intent = new Intent(InterviewActivity.this, EditInterviewActivity.class);
                        startActivity(intent);
                    }
                }
                return true;
            case R.id.action_delete:
                deleteInterview();
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_interview);

        initView();
        updateUi();
    }

    public void initView(){
        idInterview = ((ItwApplication) this.getApplication()).getInterviewId();

        textViewName = (TextView) findViewById(R.id.activity_interview_name);
        textViewDescription = (TextView) findViewById(R.id.activity_interview_description);
        imageButtonYoutube = (ImageButton) findViewById(R.id.activity_interview_video);
        textViewVideoNone = (TextView) findViewById(R.id.activity_interview_video_none);
        textViewCallback = (TextView) findViewById(R.id.activity_interview_callback);
        textViewQuestions = (TextView) findViewById(R.id.activity_interview_questions);

        Timber.d("InterviewActivity: id interview " + idInterview);

        //see applicants button
        seeApplicantsButton = (ButtonFloat) findViewById(R.id.see_applicants_button);
        seeApplicantsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(InterviewActivity.this, ApplicantsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            }
        });
    }

    public void updateUi() {
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
            imageButtonYoutube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(interview.video));
                    startActivity(i);
                }
            });
        }
        else {
            textViewVideoNone.setVisibility(View.VISIBLE);
            imageButtonYoutube.setVisibility(View.GONE);
        }
        //callback
        if(!interview.callback.isEmpty()) {
            textViewCallback.setText(interview.callback);
        }
        else {
            textViewCallback.setText(R.string.none);
        }
        if(interview.questions.length > 0) {
            String questions = "";
            for(Question q : interview.questions) {
                questions += "- " + q.content + "\n";
            }
            textViewQuestions.setText(questions);
        }
        else {
            textViewQuestions.setText(R.string.none);
        }
    }

    public void launchTask() {
        OneInterviewTask mTask = new OneInterviewTask(InterviewActivity.this, interviewService, idInterview);
        mTask.delegate = this;
        mTask.execute();
    }

    public void deleteInterview() {
        //launch task which remove interview
        DeleteInterviewTask mTask = new DeleteInterviewTask(InterviewActivity.this, interviewService, idInterview);
        mTask.delegate = this;
        mTask.execute();
    }

    /**
     * Callback method of asyncs task.
     */
    public void processFinish(Interview itw){
        interview = itw;
        Timber.d("InterviewActivity:interview retrieved : " + interview.name);

        updateUi();
    }

    /**
     * Callback method of asyncs task.
     */
    public void processFinishDelete(Boolean output) {
        if(output) {
            showAlertDialog(R.string.dialog_title_generic_success, R.string.dialog_title_delete_success);
        }
        else {
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_title_delete_error);
        }
    }

    /**
     * Callback method of asyncs task.
     */
    public void processFinishUpdate(Interview output) {
        showAlertDialog(R.string.dialog_title_generic_success, R.string.activity_add_interview_success);
        launchTask();
    }

    private void showAlertDialog(int title, int content) {
        new AlertDialog.Builder(InterviewActivity.this).setTitle(title).setMessage(content)
                .setIcon(R.drawable.ic_warning).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
                Intent intent = new Intent(InterviewActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).show();
    }

    private void showAlertDialogInfo(int title, int content) {
        new AlertDialog.Builder(InterviewActivity.this).setTitle(title).setMessage(content)
                .setIcon(R.drawable.ic_warning).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //do nothing
            }
        }).show();
    }
}
