package com.fourreau.itwapp.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.ApplicantOneResponse;
import com.fourreau.itwapp.model.Contact;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.task.OneApplicantTask;
import com.fourreau.itwapp.util.Utils;
import com.gc.materialdesign.widgets.Dialog;

import javax.inject.Inject;

import io.itwapp.models.Applicant;
import io.itwapp.models.ApplicantStatus;
import timber.log.Timber;

public class ApplicantDetailsActivity extends ActionBarActivity implements ApplicantOneResponse{

    @Inject
    ApplicantService applicantService;

    private String idApplicant;

    private TextView textViewMail, textViewFirstName, textViewLastName, textViewDeadline, textViewDeleted, textViewAnswerDate, textViewEmailView, textViewStatus;
    private ImageView language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicant_details);

        ((ItwApplication) getApplication()).inject(this);


        Intent i = getIntent();
        idApplicant = i.getStringExtra("idApplicant");

        if(idApplicant != null) {
            //launch task which retrieve one interview
            OneApplicantTask mTask = new OneApplicantTask(ApplicantDetailsActivity.this, applicantService, idApplicant);
            mTask.delegate = this;
            mTask.execute();

            //get fields
            textViewMail = (TextView) findViewById(R.id.activity_details_mail);
            textViewFirstName = (TextView) findViewById(R.id.activity_details_first_name);
            textViewLastName = (TextView) findViewById(R.id.activity_details_last_name);
            textViewDeadline = (TextView) findViewById(R.id.activity_details_deadline);
            textViewDeleted = (TextView) findViewById(R.id.activity_details_deleted);
            textViewAnswerDate = (TextView) findViewById(R.id.activity_details_answer_date);
            textViewEmailView = (TextView) findViewById(R.id.activity_details_email_view);
            textViewStatus = (TextView) findViewById(R.id.activity_details_status);
            language = (ImageView) findViewById(R.id.activity_details_language);
        }
        else {
            Dialog dialog = new Dialog(getApplicationContext(), getString(R.string.dialog_title_generic_error), getString(R.string.activity_applicant_details_error));
            dialog.show();
        }
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

    public void processFinish(Applicant applicant){
        Timber.d("ApplicantDetailsActivity:applicant retrieved : " + applicant.mail);
        //set fields
        textViewMail.setText(applicant.mail);
        //firstname
        if(!applicant.firstname.isEmpty()) {
            textViewFirstName.setText(applicant.firstname);
        }
        else {
            textViewFirstName.setText(R.string.none);
        }
        //lastname
        if(!applicant.firstname.isEmpty()) {
            textViewLastName.setText(applicant.firstname);
        }
        else {
            textViewLastName.setText(R.string.none);
        }
        textViewDeadline.setText(Utils.sdf.format(applicant.dateEnd));
        textViewDeleted.setText(String.valueOf(applicant.deleted));
        textViewAnswerDate.setText(Utils.sdf.format(applicant.dateAnswer));
        textViewEmailView.setText(String.valueOf(applicant.emailView));
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
        //language
        if(applicant.equals(Contact.Language.EN)){
            language.setBackgroundResource(R.drawable.flag_en);
        }
        else {
            language.setBackgroundResource(R.drawable.flag_fr);
        }
    }

}
