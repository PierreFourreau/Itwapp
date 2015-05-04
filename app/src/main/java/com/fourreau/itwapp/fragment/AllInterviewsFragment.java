package com.fourreau.itwapp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.fourreau.itwapp.activity.HomeActivity;
import com.fourreau.itwapp.adapter.ListViewAllInterviewsAdapter;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.InterviewResponse;
import com.fourreau.itwapp.model.ListViewInterviewItem;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.task.AllInterviewsTask;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.itwapp.models.Interview;

/**
 * Created by Pierre on 04/05/2015.
 */
public class AllInterviewsFragment extends ListFragment implements InterviewResponse {

    @Inject
    InterviewService interviewService;

    private List<ListViewInterviewItem> mItems = new ArrayList<ListViewInterviewItem>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ItwApplication)getActivity().getApplication()).inject(this);

        //launch task which retrieve all interviews
        AllInterviewsTask mTask = new AllInterviewsTask(getActivity(), interviewService);
        mTask.delegate = this;
        mTask.execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ListViewInterviewItem item = mItems.get(position);
        Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
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
            mItems.add(new ListViewInterviewItem(interviews[i].name, interviews[i].text));
        }
        setListAdapter(new ListViewAllInterviewsAdapter(getActivity(), mItems));
    }

}