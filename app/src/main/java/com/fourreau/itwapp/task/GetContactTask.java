package com.fourreau.itwapp.task;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.model.ApplicantOneResponse;
import com.fourreau.itwapp.model.DeleteInterviewResponse;
import com.fourreau.itwapp.model.GetContactResponse;
import com.fourreau.itwapp.service.InterviewService;

import java.util.HashMap;
import java.util.Map;

import io.itwapp.exception.APIException;
import timber.log.Timber;

/**
 * Created by Pierre on 15/04/2015.
 */
public class GetContactTask extends AsyncTask<String, Void, String> {

    private Context mContext;
    private Activity mActivity;

    private Uri contactUri;

    private ProgressDialog mProgressDialog;

    public GetContactResponse delegate = null;

    public GetContactTask(Activity activity,  Uri contactUri){
        this.mContext = activity.getApplicationContext();
        this.mActivity = activity;
        this.contactUri = contactUri;

        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setTitle(mActivity.getString(R.string.dialog_title_add_applicant_contact_loading));
        mProgressDialog.setMessage(mActivity.getString(R.string.dialog_loading));
        mProgressDialog.setCancelable(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        Cursor cursor = null;
        String email = "";
        try {

            // get the contact id from the Uri
            String id = contactUri.getLastPathSegment();

            // query for everything email
            cursor = mActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[] { id },
                    null);

            int emailIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

            // get email
            if (cursor.moveToFirst()) {
                email = cursor.getString(emailIdx);
            }
        } catch (Exception e) {
            Timber.d("Failed to get email data");
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return email;
    }

    @Override
    protected void onPostExecute(String email) {
        delegate.processFinishGetContact(email);
        mProgressDialog.dismiss();
    }
}