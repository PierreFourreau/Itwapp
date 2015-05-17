package com.fourreau.itwapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.fragment.DatePickerFragment;
import com.fourreau.itwapp.fragment.TimePickerFragment;
import com.fourreau.itwapp.model.Contact;
import com.fourreau.itwapp.model.CreateApplicantsResponse;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.task.CreateApplicantsTask;
import com.gc.materialdesign.views.ButtonFloat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class AddApplicantActivity extends ActionBarActivity implements CreateApplicantsResponse, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Inject
    ApplicantService applicantService;

    private String idInterview;

    private EditText editTextMails, editTextMessage;
    private TextView addApplicanTimeChoosen, addApplicanDateChoosen;
    private RadioGroup radioGroupLanguage;
    private ButtonFloat buttonValidateAddApplicant;

    private  List<Map<String, Object>> applicants = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ItwApplication) getApplication()).inject(this);
        setContentView(R.layout.activity_add_applicant);

        idInterview = ((ItwApplication) this.getApplication()).getInterviewId();

        editTextMails = (EditText) findViewById(R.id.add_applicant_mails);
        editTextMessage = (EditText) findViewById(R.id.add_applicant_message);
        addApplicanDateChoosen = (TextView) findViewById(R.id.add_applicant_date_choosen);
        addApplicanTimeChoosen = (TextView) findViewById(R.id.add_applicant_time_choosen);
        radioGroupLanguage = (RadioGroup) findViewById(R.id.add_applicant_radio_group_language);
        buttonValidateAddApplicant = (ButtonFloat) findViewById(R.id.add_applicant_button_validate);

        //validate add
        buttonValidateAddApplicant.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
            if(editTextMails.getText().length() > 0) {

                //default language en
                String language = "en";

                //get language
                if(radioGroupLanguage.getCheckedRadioButtonId() == R.id.add_applicant_radio_fr)
                {
                    language = "fr";
                }

                //get mails with split
                String[] mails = editTextMails.getText().toString().split(";");

                for(int i = 0; i < mails.length; i++) {
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("mail", mails[i]);
                    param.put("alert", true);
                    param.put("interview", idInterview);
                    param.put("lang", language);
                    param.put("deadline", 1409045626568L);
                    applicants.add(param);
                }


                Timber.d("AddApplicantsActivity:applicants sent : " + applicants.toString());

                //launch task which add applicants
                CreateApplicantsTask mTask = new CreateApplicantsTask(AddApplicantActivity.this, applicantService, applicants);
                mTask.delegate = AddApplicantActivity.this;
                mTask.execute();
            }
            else {
                showAlertDialog(R.string.dialog_title_generic_error, R.string.activity_add_applicant_mails_error);
            }
        }});
    }

    /**
     * Show date picker dialog.
     *
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    /**
     * Show time picker dialog.
     * @param v
     */
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment(this);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    /**
     * When date is set from dialog.
     *
     * @param view
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     */
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
        addApplicanDateChoosen.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
    }

    /**
     * When time is set from dialog.
     *
     * @param view
     * @param hourOfDay
     * @param minute
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        addApplicanTimeChoosen.setText(hourOfDay + ":" + minute);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Retrieve result from task.
     *
     * @param output
     */
    public void processFinishCreate(Boolean output) {
        if(output) {
            showAlertDialog(R.string.dialog_title_generic_success, R.string.dialog_title_add_applicant_success);
        }
        else {
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_title_add_applicant_error);
        }
    }

    private void showAlertDialog(int title, int content) {
        new AlertDialog.Builder(AddApplicantActivity.this).setTitle(title).setMessage(content)
                .setIcon(android.R.drawable.ic_dialog_info).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //do nothing
            }
        }).show();
    }

    public void fetchContacts() {

        List<Contact> contacts = new ArrayList<Contact>();


        String email = null;

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output = new StringBuffer();

        ContentResolver contentResolver = AddApplicantActivity.this.getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
                // Query and loop for every email of the contact
                Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
                while (emailCursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                    output.append("\n First Name:" + name);
                    email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                    output.append(" - Email:" + email);
                    //create and add contact
                    if(name != null && email != null) {
                        Contact contact = new Contact(contact_id, name, email, null, null, null);
                        contacts.add(contact);
                    }
                }
                emailCursor.close();
            }
            Timber.d(output.toString());
        }
    }
}
