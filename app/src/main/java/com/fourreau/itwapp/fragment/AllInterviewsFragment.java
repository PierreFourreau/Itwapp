package com.fourreau.itwapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.AddInterviewActivity;
import com.fourreau.itwapp.activity.HomeActivity;
import com.fourreau.itwapp.activity.InterviewActivity;
import com.fourreau.itwapp.adapter.ListViewAllInterviewsAdapter;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.InterviewAllResponse;
import com.fourreau.itwapp.model.ListViewInterviewItem;
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
public class AllInterviewsFragment extends ListFragment implements InterviewAllResponse {

    @Inject
    InterviewService interviewService;

    private List<ListViewInterviewItem> mItems = new ArrayList<ListViewInterviewItem>();
    private ButtonFloat addInterviewButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.listview_interviews, container, false);
        addInterviewButton = (ButtonFloat) view.findViewById(R.id.addInterviewButton);
        addInterviewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getActivity(), AddInterviewActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ItwApplication)getActivity().getApplication()).inject(this);

        //launch task which retrieve all interviews
        AllInterviewsTask mTask = new AllInterviewsTask(getActivity(), interviewService);
        mTask.delegate = this;
        mTask.execute();

        //fetchContacts();

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ListViewInterviewItem item = mItems.get(position);
        Intent intent = new Intent(getActivity(), InterviewActivity.class);
        ((ItwApplication) getActivity().getApplication()).setInterviewId(item.id);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((HomeActivity) activity).onSectionAttached(1);
    }

    public void processFinish(Interview[] interviews){
        for(int i = 0; i < interviews.length; i++) {
            mItems.add(new ListViewInterviewItem(interviews[i].id, interviews[i].name, interviews[i].text));
        }
        setListAdapter(new ListViewAllInterviewsAdapter(getActivity(), mItems));
    }

}