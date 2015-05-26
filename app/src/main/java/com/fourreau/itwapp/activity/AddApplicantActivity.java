package com.fourreau.itwapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.fragment.DatePickerFragment;
import com.fourreau.itwapp.fragment.TimePickerFragment;
import com.fourreau.itwapp.model.Contact;
import com.fourreau.itwapp.model.CreateApplicantsResponse;
import com.fourreau.itwapp.model.GetContactResponse;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.task.AllApplicantsTask;
import com.fourreau.itwapp.task.CreateApplicantsTask;
import com.fourreau.itwapp.task.GetContactTask;
import com.fourreau.itwapp.util.Utils;
import com.gc.materialdesign.views.ButtonFloat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class AddApplicantActivity extends ActionBarActivity implements CreateApplicantsResponse, GetContactResponse, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    static final int PICK_CONTACT_REQUEST = 1;

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

        Calendar c = Calendar.getInstance();
        addApplicanDateChoosen.setText(Utils.sdfDateSimple.format(c.getTime()));
        addApplicanTimeChoosen.setText(Utils.sdfTimeSimple.format(c.getTime()));

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
     * Show contacts dialog.
     */
    public void showContactsDialog(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    /**
     * Get contact email.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            //make sure the request was successful
            if (resultCode == RESULT_OK) {
                //get the URI that points to the selected contact
                Uri contactUri = data.getData();

                //launch task
                GetContactTask mTask = new GetContactTask(AddApplicantActivity.this, contactUri);
                mTask.delegate = AddApplicantActivity.this;
                mTask.execute();
            }
        }
    }

    /**
     * Get result from contact dialog.
     *
     * @param email
     */
    public void processFinishGetContact(String email) {
        if(email != null && email != "") {
            String textViewContent = editTextMails.getText().toString();
            if(textViewContent.isEmpty()) {
                editTextMails.setText(email, TextView.BufferType.EDITABLE);
            }
            else {
                editTextMails.setText(textViewContent + ";" + email, TextView.BufferType.EDITABLE);
            }

            Toast.makeText(AddApplicantActivity.this, R.string.dialog_title_add_applicant_contact_success, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(AddApplicantActivity.this, R.string.dialog_title_add_applicant_contact_no_result, Toast.LENGTH_LONG).show();
        }
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

    /**
     * Retrieve result from task.
     *
     * @param output
     */
    public void processFinishCreate(Boolean output) {
        if(output) {
            Toast.makeText(AddApplicantActivity.this, R.string.dialog_title_add_applicant_success, Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(AddApplicantActivity.this, ApplicantsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else {
            showAlertDialog(R.string.dialog_title_generic_error, R.string.dialog_title_add_applicant_error);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog(int title, int content) {
        new AlertDialog.Builder(AddApplicantActivity.this).setTitle(title).setMessage(content)
                .setIcon(R.drawable.ic_warning).setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                //do nothing
            }
        }).show();
    }
}
