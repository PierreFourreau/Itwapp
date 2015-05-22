package com.fourreau.itwapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.AddInterviewActivity;
import com.fourreau.itwapp.activity.HomeActivity;
import com.fourreau.itwapp.adapter.InterviewAdapter;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.InterviewAllResponse;
import com.fourreau.itwapp.model.InterviewDto;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.task.AllInterviewsTask;
import com.gc.materialdesign.views.ButtonFloat;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.itwapp.models.Interview;

/**
 * Created by Pierre on 04/05/2015.
 */
public class AllInterviewsFragment extends Fragment implements InterviewAllResponse {

    @Inject
    InterviewService interviewService;

    private  List<InterviewDto> interviewList;

    private ButtonFloat addInterviewButton;
    private MenuItem menuItem;

    private RecyclerView recList;
    private LinearLayoutManager llm;
    private InterviewAdapter itwAdapter;
    private TextView textViewNoData;

    private FrameLayout frameLayout = null;
    private View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        frameLayout = new FrameLayout(getActivity());
        view = inflater.inflate(R.layout.fragment_interviews, null);
        frameLayout .addView(view);

        initView();

        return frameLayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ItwApplication)getActivity().getApplication()).inject(this);

        //launch task which retrieve all interviews
        launchTask();
    }

    /**
     * When configuration change (orientation for example).
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        frameLayout. removeAllViews();
        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.fragment_interviews, null);

        initView();
        updateUi();

        frameLayout .addView(view);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(1);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.global, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        menuItem = item;
        if (id == R.id.action_refresh) {
            menuItem.setActionView(R.layout.progressbar);
            menuItem.expandActionView();
            launchTask();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Init view.
     */
    public void initView() {
        //textview
        textViewNoData = (TextView) view.findViewById(R.id.textViewNoData);

        //recycler view
        recList = (RecyclerView) view.findViewById(R.id.cardListInterviews);
        recList.setHasFixedSize(true);
        llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        //add button
        addInterviewButton = (ButtonFloat) view.findViewById(R.id.addInterviewButton);
        addInterviewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), AddInterviewActivity.class);
                startActivity(intent);
            }
        });

        setHasOptionsMenu(true);
    }

    public void updateUi() {
        //set adapter
        itwAdapter = new InterviewAdapter(getActivity(), interviewList);
        recList.setAdapter(itwAdapter);

        //show textview no data
        if(interviewList.size() == 0) {
            textViewNoData.setVisibility(View.VISIBLE);
        }
        else {
            textViewNoData.setVisibility(View.GONE);
        }

        //restore action bar refresh
        if(menuItem != null) {
            menuItem.collapseActionView();
            menuItem.setActionView(null);
        }
    }

    public void launchTask() {
        AllInterviewsTask mTask = new AllInterviewsTask(getActivity(), interviewService);
        mTask.delegate = this;
        mTask.execute();
    }

    public void processFinish(Interview[] interviews){
        interviewList = new ArrayList<InterviewDto>();
        for(int i = 0; i < interviews.length; i++) {
            interviewList.add(new InterviewDto(interviews[i].id, interviews[i].name, interviews[i].text, interviews[i].sent, interviews[i].answers, interviews[i].news));
        }
        updateUi();
    }
}