package com.fourreau.itwapp.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.model.ApplicantOneResponse;
import com.fourreau.itwapp.model.AsyncTaskResult;
import com.fourreau.itwapp.model.InterviewOneResponse;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.service.InterviewService;

import io.itwapp.exception.APIException;
import io.itwapp.models.Applicant;
import io.itwapp.models.Interview;
import timber.log.Timber;

/**
 * Created by Pierre on 15/04/2015.
 */
public class OneApplicantTask extends AsyncTask<String, Void, AsyncTaskResult<Applicant>> {

    private Context mContext;
    private Activity mActivity;
    private ApplicantService applicantService;
    private String applicantId;

    private ProgressDialog mProgressDialog;

    public ApplicantOneResponse delegate = null;

    public OneApplicantTask(Activity activity, ApplicantService applicantService, String applicantId){
        this.mContext = activity.getApplicationContext();
        this.mActivity = activity;
        this.applicantService = applicantService;
        this.applicantId = applicantId;

        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setTitle(mActivity.getString(R.string.title_activity_applicant_details));
        mProgressDialog.setMessage(mActivity.getString(R.string.dialog_loading));
        mProgressDialog.setCancelable(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected AsyncTaskResult<Applicant> doInBackground(String... params) {
        Applicant applicant= null;
        try {
            applicant = applicantService.findOne(applicantId);
            return new AsyncTaskResult<Applicant>(applicant);
        }
        catch (APIException e) {
            Timber.e("InterviewActivity:findOne:" + e.toString());
            return new AsyncTaskResult<Applicant>(e);
        }
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Applicant> result) {
        if(result.getError() != null ) {
            // error handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_generic_error_server);
        }  else if (isCancelled()) {
            // cancel handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_cancellation);
        } else {
            Applicant applicant = result.getResult();

            if(applicant != null) {
                delegate.processFinish(applicant);
            }
        }
        mProgressDialog.dismiss();
    }

    private void showAlertDialog(int title, int content) {
        new AlertDialog.Builder(mActivity).setTitle(title).setMessage(content)
                .setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //Nothing to do
            }
        }).show();
    }
}