package com.fourreau.itwapp.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.model.ApplicantAllResponse;
import com.fourreau.itwapp.model.AsyncTaskResult;
import com.fourreau.itwapp.model.InterviewAllResponse;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.service.InterviewService;

import java.util.HashMap;
import java.util.Map;

import io.itwapp.exception.APIException;
import io.itwapp.exception.InvalidRequestError;
import io.itwapp.exception.ResourceNotFoundException;
import io.itwapp.exception.ServiceException;
import io.itwapp.exception.UnauthorizedException;
import io.itwapp.models.Applicant;
import io.itwapp.models.Interview;
import timber.log.Timber;

/**
 * Created by Pierre on 15/04/2015.
 */
public class AllApplicantsTask extends AsyncTask<String, Void, AsyncTaskResult<Applicant[]>> {

    private Context mContext;
    private Activity mActivity;
    private ApplicantService applicantService;
    private String interviewId;

    private ProgressDialog mProgressDialog;

    public ApplicantAllResponse delegate = null;

    public AllApplicantsTask(Activity activity, ApplicantService applicantService, String interviewId){
        this.mContext = activity.getApplicationContext();
        this.mActivity = activity;
        this.applicantService = applicantService;
        this.interviewId = interviewId;

        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setTitle(mActivity.getString(R.string.title_activity_applicants));
        mProgressDialog.setMessage(mActivity.getString(R.string.dialog_loading));
        mProgressDialog.setCancelable(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected AsyncTaskResult<Applicant[]> doInBackground(String... params) {
        Applicant[] applicants = null;
        try {
            applicants = applicantService.getAllApplicantsByInterview(interviewId, new HashMap<String, Object>());
            return new AsyncTaskResult<Applicant[]>(applicants);

        }
        catch (APIException | UnauthorizedException |InvalidRequestError | ResourceNotFoundException | ServiceException e) {
            Timber.e("AllApplicantsTask:getAllApplicants:" + e.toString());
            return new AsyncTaskResult<Applicant[]>(e);
        }
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Applicant[]> result) {
        if(result.getError() != null ) {
            // error handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_generic_error_server);
        }  else if (isCancelled()) {
            // cancel handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_cancellation);
        } else {
            Applicant[] applicants = result.getResult();

            if(applicants != null) {
                delegate.processFinishApplicantAll(applicants);
                Timber.d("Number of applicants retrieved : " + applicants.length);
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