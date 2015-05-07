package com.fourreau.itwapp.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.HomeActivity;
import com.fourreau.itwapp.adapter.ListViewAllInterviewsAdapter;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.Contact;
import com.fourreau.itwapp.model.InterviewResponse;
import com.fourreau.itwapp.model.ListViewInterviewItem;
import com.fourreau.itwapp.service.InterviewService;
import com.fourreau.itwapp.task.AllInterviewsTask;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.itwapp.models.Interview;
import timber.log.Timber;

/**
 * Created by Pierre on 04/05/2015.
 */
public class AllInterviewsFragment extends ListFragment implements InterviewResponse {

    @Inject
    InterviewService interviewService;

    private List<ListViewInterviewItem> mItems = new ArrayList<ListViewInterviewItem>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.listview_interviews, container, false);
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

        ContentResolver contentResolver = getActivity().getContentResolver();

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
                        Contact contact = new Contact(Integer.parseInt(contact_id), name, email);
                        contacts.add(contact);
                    }
                }
                emailCursor.close();
            }
            Timber.d(output.toString());
        }
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