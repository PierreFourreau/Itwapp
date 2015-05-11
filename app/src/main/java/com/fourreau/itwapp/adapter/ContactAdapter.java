package com.fourreau.itwapp.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.model.Contact;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Pierre on 08/05/2015.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contactList;

    public ContactAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Contact c = contactList.get(i);
        contactViewHolder.vMail.setText(c.getEmail());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

        contactViewHolder.vDeadline.setText( sdf.format(new Date(c.getDeadline())));
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);

        ContactAdapter.ContactViewHolder vh = new ContactViewHolder(itemView, new ContactAdapter.ContactViewHolder.IContactViewHolderClicks() {
            public void onPotato(View caller) {
                Timber.d("aaaaaaaaaaaa");
                Log.d("aaaaaa","aaaaaa");
            };
        });
        return vh;
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected CardView cardView;
        protected TextView vMail;
        protected TextView vDeadline;
        public IContactViewHolderClicks mListener;

        public ContactViewHolder(View v, IContactViewHolderClicks listener) {
            super(v);
            mListener = listener;
            cardView = (CardView) v.findViewById(R.id.card_view);
            vMail =  (TextView) v.findViewById(R.id.mail);
            vDeadline = (TextView)  v.findViewById(R.id.deadline);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onPotato(v);
        }

        public static interface IContactViewHolderClicks {
            public void onPotato(View caller);
        }
    }
}
