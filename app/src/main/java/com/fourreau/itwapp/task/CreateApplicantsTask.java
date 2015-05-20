package com.fourreau.itwapp.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.model.CreateApplicantsResponse;
import com.fourreau.itwapp.service.ApplicantService;

import java.util.List;
import java.util.Map;

import io.itwapp.exception.APIException;
import io.itwapp.exception.InvalidRequestError;
import io.itwapp.exception.ResourceNotFoundException;
import io.itwapp.exception.ServiceException;
import io.itwapp.exception.UnauthorizedException;
import timber.log.Timber;

/**
 * Created by Pierre on 15/04/2015.
 */
public class CreateApplicantsTask extends AsyncTask<String, Void, Boolean> {

    private Context mContext;
    private Activity mActivity;
    private ApplicantService applicantService;
    private List<Map<String, Object>> applicants;

    private ProgressDialog mProgressDialog;

    public CreateApplicantsResponse delegate = null;

    public CreateApplicantsTask(Activity activity, ApplicantService applicantService, List<Map<String, Object>> applicants){
        this.mContext = activity.getApplicationContext();
        this.mActivity = activity;
        this.applicantService = applicantService;
        this.applicants = applicants;

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
    protected Boolean doInBackground(String... params) {
        try {
            for(int i = 0; i < applicants.size(); i++){
                applicantService.create(applicants.get(i));
            }
            return true;
        }
        catch (APIException | UnauthorizedException |InvalidRequestError | ResourceNotFoundException | ServiceException e) {
            Timber.e("AddInterviewActivity:create:" + e.toString());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(!result) {
            // error handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_generic_error_server);
        }  else if (isCancelled()) {
            // cancel handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_cancellation);
        } else {
            delegate.processFinishCreate(true);
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