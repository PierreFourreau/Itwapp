package com.fourreau.itwapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.ApplicantDetailsActivity;
import com.fourreau.itwapp.model.Contact;
import com.fourreau.itwapp.util.Utils;

import java.sql.Date;
import java.util.List;

/**
 * Created by Pierre on 08/05/2015.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Activity activity;

    private List<Contact> contactList;

    public ContactAdapter(Activity activity, List<Contact> contactList) {
        this.activity = activity;
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Contact c = contactList.get(i);
        contactViewHolder.vId.setText(c.getId());
        //set mail
        contactViewHolder.vMail.setText(c.getEmail());
        //set deadline
        contactViewHolder.vDeadline.setText(Utils.sdf.format(new Date(c.getDeadline())));
        //set language
        if(c.getLanguage().equals(Contact.Language.EN)) {
            contactViewHolder.vLanguage.setBackgroundResource(R.drawable.flag_en);
        }
        else {
            contactViewHolder.vLanguage.setBackgroundResource(R.drawable.flag_fr);
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);

        ContactAdapter.ContactViewHolder vh = new ContactViewHolder(itemView, new ContactAdapter.ContactViewHolder.IContactViewHolderClicks() {
            public void onPotato(View caller, String id) {
                //launch activity
                Intent intent = new Intent(activity, ApplicantDetailsActivity.class);
                intent.putExtra("idApplicant", id);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            };
        });
        return vh;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected CardView cardView;
        protected TextView vId;
        protected TextView vMail;
        protected TextView vDeadline;
        protected ImageView vLanguage;
        public IContactViewHolderClicks mListener;

        public ContactViewHolder(View v, IContactViewHolderClicks listener) {
            super(v);
            mListener = listener;
            cardView = (CardView) v.findViewById(R.id.card_view);
            vId = (TextView) v.findViewById(R.id.id);
            vMail =  (TextView) v.findViewById(R.id.mail);
            vDeadline = (TextView)  v.findViewById(R.id.deadline);
            vLanguage = (ImageView)  v.findViewById(R.id.language);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onPotato(v, vId.getText().toString());
        }

        public static interface IContactViewHolderClicks {
            public void onPotato(View caller, String id);
        }
    }
}
