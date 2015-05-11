package com.fourreau.itwapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.adapter.ContactAdapter;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.ApplicantAllResponse;
import com.fourreau.itwapp.model.Contact;
import com.fourreau.itwapp.service.ApplicantService;
import com.fourreau.itwapp.task.AllApplicantsTask;
import com.gc.materialdesign.views.ButtonFloat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.itwapp.models.Applicant;

public class ApplicantsActivity extends ActionBarActivity implements ApplicantAllResponse {

    @Inject
    ApplicantService applicantService;

    private RecyclerView recList;
    private LinearLayoutManager llm;
    private ContactAdapter ca;

    private ButtonFloat addApplicationButton;

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

        //add applicant button
        addApplicationButton = (ButtonFloat) findViewById(R.id.addApplicantButton);
        addApplicationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ApplicantsActivity.this, AddApplicantActivity.class);
                startActivity(intent);
            }
        });

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
        //fill contact list
        List<Contact> contactList = new ArrayList<Contact>();
        for(int i = 0; i < applicants.length; i++) {
            contactList.add(new Contact(applicants[i].id, applicants[i].firstname, applicants[i].mail, applicants[i].dateEnd, applicants[i].lang, applicants[i].status.getCode()));
        }
        //set adapter
        ca = new ContactAdapter(ApplicantsActivity.this, contactList);
        recList.setAdapter(ca);
    }
}
