package com.fourreau.itwapp.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.ApplicantOneResponse;
import com.fourreau.itwapp.model.Contact;
import com.fourreau.itwapp.model.DeleteApplicantResponse;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.task.DeleteApplicantTask;
import com.fourreau.itwapp.task.OneApplicantTask;
import com.fourreau.itwapp.util.Utils;
import com.gc.materialdesign.widgets.Dialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.itwapp.models.Applicant;
import io.itwapp.models.ApplicantStatus;
import io.itwapp.models.Question;
import io.itwapp.models.Response;
import timber.log.Timber;

public class ApplicantDetailsActivity extends ActionBarActivity implements ApplicantOneResponse, DeleteApplicantResponse{

    @Inject
    ApplicantService applicantService;

    private Applicant applicant;

    private String idApplicant;

    private LinearLayout llTitleName;
    private TextView textViewMail, textViewFirstName, textViewLastName, textViewDeadline, textViewDeleted, textViewAnswerDate, textViewEmailView, textViewStatus, textViewResponsesNone;
    private ImageView language, gravatar;

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);

        ((ItwApplication) getApplication()).inject(this);

        idApplicant = ((ItwApplication) this.getApplication()).getApplicantId();

        if(idApplicant != null) {
            initView();

            //launch task which retrieve one interview
            OneApplicantTask mTask = new OneApplicantTask(ApplicantDetailsActivity.this, applicantService, idApplicant);
            mTask.delegate = this;
            mTask.execute();
        }
        else {
            Dialog dialog = new Dialog(getApplicationContext(), getString(R.string.dialog_title_generic_error), getString(R.string.activity_applicant_details_error));
            dialog.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.applicant_details, menu);
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
            case R.id.action_delete:
                deleteApplicant();
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
        setContentView(R.layout.activity_applicant_details);

        initView();
        updateUi();
    }

    public void initView() {
        //get fields
        llTitleName = (LinearLayout)findViewById(R.id.layout_title_name);
        textViewMail = (TextView) findViewById(R.id.activity_details_mail);
        textViewFirstName = (TextView) findViewById(R.id.activity_details_first_name);
        textViewLastName = (TextView) findViewById(R.id.activity_details_last_name);
        textViewDeadline = (TextView) findViewById(R.id.activity_details_deadline);
        textViewDeleted = (TextView) findViewById(R.id.activity_details_deleted);
        textViewAnswerDate = (TextView) findViewById(R.id.activity_details_answer_date);
        textViewEmailView = (TextView) findViewById(R.id.activity_details_email_view);
        textViewStatus = (TextView) findViewById(R.id.activity_details_status);
        textViewResponsesNone = (TextView) findViewById(R.id.text_view_responses_none);
        gravatar = (ImageView) findViewById(R.id.activity_details_gravatar);
        language = (ImageView) findViewById(R.id.activity_details_language);
        container = (LinearLayout)findViewById(R.id.containerResponses);
    }

    public void updateUi() {
        //set fields
        //language
        if(applicant.equals(Contact.Language.EN)){
            language.setBackgroundResource(R.drawable.flag_en);
        }
        else {
            language.setBackgroundResource(R.drawable.flag_fr);
        }
        //gravatar download image
        String hash = Utils.md5Hex(applicant.mail);
        if(Utils.is4InchScreen(ApplicantDetailsActivity.this)) {
            Picasso.with(ApplicantDetailsActivity.this).load(Utils.getUrlGravatar(hash)).error(R.drawable.ic_action_person).into(gravatar);
        }
        else {
            Picasso.with(ApplicantDetailsActivity.this).load(Utils.getUrlGravatar4Inch(hash)).error(R.drawable.ic_action_person).into(gravatar);
        }
        //mail
        textViewMail.setText(applicant.mail);
        //firstname
        if(!applicant.firstname.isEmpty()) {
            textViewFirstName.setText(applicant.firstname);
        }
        else {
            llTitleName.setVisibility(View.GONE);
        }
        //lastname
        if(!applicant.lastname.isEmpty()) {
            textViewLastName.setText(applicant.lastname);
        }
        else {
            llTitleName.setVisibility(View.GONE);
        }
        textViewDeadline.setText(Utils.sdf.format(applicant.dateEnd));
        if(applicant.deleted) {
            textViewDeleted.setText(R.string.yes);
        }
        else {
            textViewDeleted.setText(R.string.no);
        }

        textViewAnswerDate.setText(Utils.sdf.format(applicant.dateAnswer));
        //email view
        if(applicant.emailView) {
            textViewEmailView.setText(R.string.yes);
        }
        else {
            textViewEmailView.setText(R.string.no);
        }
        //status
        if(applicant.status.equals(ApplicantStatus.COMPLETED)) {
            textViewStatus.setText(R.string.activity_applicant_details_status_completed);
        }
        else if(applicant.status.equals(ApplicantStatus.OPENEMAIL)) {
            textViewStatus.setText(R.string.activity_applicant_details_status_open_email);
        }
        else if(applicant.status.equals(ApplicantStatus.INPROGRESS)) {
            textViewStatus.setText(R.string.activity_applicant_details_status_in_progress);
        }
        else if(applicant.status.equals(ApplicantStatus.EMAILSENT)) {
            textViewStatus.setText(R.string.activity_applicant_details_status_email_sent);
        }
        else {
            textViewStatus.setText(R.string.unknown);
        }
        //responses
        if(applicant.questions.length > 0) {
            if(applicant.responses.length > 0) {
                for (Response r : applicant.responses) {
                    for(Question q : applicant.questions) {
                        if(q.number == r.number) {
                            final String video = r.file;
                            //add row
                            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            final View addView = layoutInflater.inflate(R.layout.response_row, null);

                            //get fields
                            TextView textViewQuestionName = (TextView) addView.findViewById(R.id.textViewQuestion);
                            //set fields
                            textViewQuestionName.setText(q.content);
                            //button video
                            addView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(ApplicantDetailsActivity.this, ResponseVideoActivity.class);
                                    intent.putExtra("url", video);
                                    startActivity(intent);
                                }
                            });
                            addView.setBackgroundResource(R.drawable.frame);
                            addView.setPadding(10, 10, 20, 10);
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(2, 5, 2, 5);
                            addView.setLayoutParams(lp);
                            //add row to container
                            container.addView(addView);
                        }
                    }
                }
            }
            else {
                textViewResponsesNone.setVisibility(View.VISIBLE);
            }
        }
    }

    public void processFinish(Applicant a){
        applicant = a;
        Timber.d("ApplicantDetailsActivity:applicant retrieved : " + applicant.mail);
        updateUi();
    }

    public void processFinishDelete(Boolean output) {
        if(output) {
            showAlertDialog(R.string.dialog_title_generic_success, R.string.dialog_title_delete_success);
        }
        else {
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_title_delete_error);
        }
    }

    public void deleteApplicant() {
        //launch task which remove applicant
        DeleteApplicantTask mTask = new DeleteApplicantTask(ApplicantDetailsActivity.this, applicantService, idApplicant);
        mTask.delegate = this;
        mTask.execute();
    }

    private void showAlertDialog(int title, int content) {
        new AlertDialog.Builder(ApplicantDetailsActivity.this).setTitle(title).setMessage(content)
                .setIcon(R.drawable.ic_warning).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                finish();
                Intent intent = new Intent(ApplicantDetailsActivity.this, ApplicantsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).show();
    }
}
