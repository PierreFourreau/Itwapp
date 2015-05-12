package com.fourreau.itwapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
    private TextView textViewNoData;
    private MenuItem menuItem;

    private String idInterview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants);

        ((ItwApplication) getApplication()).inject(this);

        textViewNoData = (TextView) findViewById(R.id.textViewNoData);

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
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        menuItem = item;
        switch(id) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                return true;
            case  R.id.action_refresh:
                menuItem.setActionView(R.layout.progressbar);
                menuItem.expandActionView();
                //launch task
                AllApplicantsTask mTask = new AllApplicantsTask(ApplicantsActivity.this, applicantService, idInterview);
                mTask.delegate = this;
                mTask.execute();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // finish() is called in super: we only override this method to be able to override the transition
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
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

        //show textview no data
        if(contactList.size() == 0) {
            textViewNoData.setVisibility(View.VISIBLE);
        }

        //restore action bar refresh
        if(menuItem != null) {
            menuItem.collapseActionView();
            menuItem.setActionView(null);
        }
    }
}
