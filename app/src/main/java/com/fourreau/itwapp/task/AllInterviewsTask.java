package com.fourreau.itwapp.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.model.InterviewAllResponse;
import com.fourreau.itwapp.model.InterviewOneResponse;
import com.fourreau.itwapp.model.AsyncTaskResult;
import com.fourreau.itwapp.service.InterviewService;

import io.itwapp.exception.APIException;
import io.itwapp.models.Interview;
import timber.log.Timber;

/**
 * Created by Pierre on 15/04/2015.
 */
public class AllInterviewsTask extends AsyncTask<String, Void, AsyncTaskResult<Interview[]>> {

    private Context mContext;
    private Activity mActivity;
    private InterviewService interviewService;

    private ProgressDialog mProgressDialog;

    public InterviewAllResponse delegate = null;

    public AllInterviewsTask(Activity activity, InterviewService interviewService){
        this.mContext = activity.getApplicationContext();
        this.mActivity = activity;
        this.interviewService = interviewService;

        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setTitle(mActivity.getString(R.string.title_section1));
        mProgressDialog.setMessage(mActivity.getString(R.string.dialog_loading));
        mProgressDialog.setCancelable(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected AsyncTaskResult<Interview[]> doInBackground(String... params) {
        Interview[] interviews = null;
        try {
            interviews = interviewService.getAllInterviews();
            return new AsyncTaskResult<Interview[]>(interviews);

        }
        catch (APIException e) {
            Timber.e("AllInterviewsTask:getAllInterviews:" + e.toString());
            return new AsyncTaskResult<Interview[]>(e);
        }
    }

    @Override
    protected void onPostExecute(AsyncTaskResult<Interview[]> result) {
        if(result.getError() != null ) {
            // error handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_generic_error_server);
        }  else if (isCancelled()) {
            // cancel handling here
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_content_cancellation);
        } else {
            Interview[] interviews = result.getResult();

            if(interviews != null) {
                delegate.processFinish(interviews);
                Timber.d("Number of interviews retrieved : " + interviews.length);
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