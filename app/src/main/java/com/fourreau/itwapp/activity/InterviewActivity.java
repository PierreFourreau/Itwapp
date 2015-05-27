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
import android.widget.ImageView;
import android.widget.TextView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.DeleteInterviewResponse;
import com.fourreau.itwapp.model.InterviewOneResponse;
import com.fourreau.itwapp.model.UpdateInterviewResponse;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.task.DeleteInterviewTask;
import com.fourreau.itwapp.task.OneInterviewTask;
import com.fourreau.itwapp.util.Utils;
import com.gc.materialdesign.views.ButtonFloat;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.itwapp.models.Interview;
import io.itwapp.models.Question;
import timber.log.Timber;

public  class InterviewActivity extends ActionBarActivity implements InterviewOneResponse, DeleteInterviewResponse, UpdateInterviewResponse {

    @Inject
    InterviewService interviewService;

    private Interview interview;

    private String idInterview;

    private TextView textViewName, textViewDescription, textViewCallback, textViewQuestions;
    private ImageView imageThumbnailYoutube, videoPlayButton;
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

        imageThumbnailYoutube = (ImageView) findViewById(R.id.image_thumbnail_youtube);
        videoPlayButton = (ImageView) findViewById(R.id.video_play_button);
        textViewName = (TextView) findViewById(R.id.activity_interview_name);
        textViewDescription = (TextView) findViewById(R.id.activity_interview_description);
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
        //video
        if(interview.video != null) {
            Picasso.with(InterviewActivity.this).load(Utils.URL_THUMBNAIL_YOUTUBE_BEGIN + Utils.extractYoutubeId(interview.video) + Utils.URL_THUMBNAIL_YOUTUBE_FULL_SIZE_END).fit().centerCrop().error(R.drawable.ic_itw).into(imageThumbnailYoutube);
            imageThumbnailYoutube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(interview.video));
                    startActivity(i);
                }
            });
            videoPlayButton.setVisibility(View.VISIBLE);
        }
        else {
            Picasso.with(InterviewActivity.this).load(R.drawable.ic_itw).resize(50, 50).into(imageThumbnailYoutube);
            imageThumbnailYoutube.setVisibility(View.GONE);
            videoPlayButton.setVisibility(View.GONE);
        }

        //name
        textViewName.setText(interview.name);
        //description
        if(!interview.text.isEmpty()) {
            textViewDescription.setText(interview.text);
        }
        else {
            textViewDescription.setText(R.string.none);
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
                questions += "- " + q.content + "\n\n";
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
                .setIcon(R.drawable.ic_warning).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        }).show();
    }
}
