package com.fourreau.itwapp.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.adapter.ContactAdapter;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.ApplicantAllResponse;
import com.fourreau.itwapp.model.Contact;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.task.AllApplicantsTask;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.itwapp.models.Applicant;
import timber.log.Timber;

public class ApplicantsActivity extends ActionBarActivity implements ApplicantAllResponse {

    @Inject
    ApplicantService applicantService;

    private RecyclerView recList;
    private LinearLayoutManager llm;
    private ContactAdapter ca;

    private String idInterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);

        ((ItwApplication) getApplication()).inject(this);

        //recycler view
        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        //get interview id
        idInterview = ((ItwApplication) this.getApplication()).getInterviewId();

        //launch task which retrieve one interview
        AllApplicantsTask mTask = new AllApplicantsTask(ApplicantsActivity.this, applicantService, idInterview);
        mTask.delegate = this;
        mTask.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void processFinish(Applicant[] applicants){
        List<Contact> contactList = new ArrayList<Contact>();
        for(int i = 0; i < applicants.length; i++) {
            Timber.d(applicants[i].firstname);
            contactList.add(new Contact(applicants[i].id, applicants[i].firstname, applicants[i].mail));
        }
        ca = new ContactAdapter(contactList);
        recList.setAdapter(ca);

//        Timber.d("ApplicantsActivity:applicants retrieved : " + applicants.toString());
    }
}
