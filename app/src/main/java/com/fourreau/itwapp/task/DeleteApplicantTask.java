package com.fourreau.itwapp.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.model.DeleteApplicantResponse;
import com.fourreau.itwapp.service.ApplicantService;

import io.itwapp.exception.APIException;
import timber.log.Timber;

/**
 * Created by Pierre on 15/04/2015.
 */
public class DeleteApplicantTask extends AsyncTask<String, Void, Boolean> {

    private Context mContext;
    private Activity mActivity;
    private ApplicantService applicantService;
    private String applicantId;

    private ProgressDialog mProgressDialog;

    public DeleteApplicantResponse delegate = null;

    public DeleteApplicantTask(Activity activity, ApplicantService applicantService, String applicantId){
        this.mContext = activity.getApplicationContext();
        this.mActivity = activity;
        this.applicantService = applicantService;
        this.applicantId = applicantId;

        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setTitle(mActivity.getString(R.string.title_activity_applicant_deletion));
        mProgressDialog.setMessage(mActivity.getString(R.string.dialog_loading));
        mProgressDialog.setCancelable(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            applicantService.delete(applicantId);
            return true;
        }
        catch (APIException e) {
            Timber.e("ApplicantDetailsActivity:delete:" + e.toString());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(!result) {
            delegate.processFinishDelete(false);
            // error handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_generic_error_server);
        }  else if (isCancelled()) {
            delegate.processFinishDelete(false);
            // cancel handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_cancellation);
        } else {
                delegate.processFinishDelete(true);
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