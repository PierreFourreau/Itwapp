package com.fourreau.itwapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.ApplicantDetailsActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.Contact;
import com.fourreau.itwapp.util.Utils;
import com.squareup.picasso.Picasso;

import java.sql.Date;
import java.util.List;

/**
 * Created by Pierre on 08/05/2015.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private Activity activity;

    private List<Contact> contactList;

    //allows to remember the last item shown on screen
    private int lastPosition = -1;

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
        //gravatar download image
        String hash = Utils.md5Hex(c.getEmail());

        Picasso.with(activity).load(Utils.getSmallUrlGravatar(hash)).into(contactViewHolder.vGravatar);
        if(Utils.is4InchOrLessScreen(activity)) {
            Picasso.with(activity).load(Utils.getSmallUrlGravatar4Inch(hash)).into(contactViewHolder.vGravatar);
        }
        else {
            Picasso.with(activity).load(Utils.getSmallUrlGravatar(hash)).into(contactViewHolder.vGravatar);
        }

        // Here you apply the animation when the view is bound
        setAnimation(contactViewHolder.container, i);
    }

    /**
     * Here is the method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_item, viewGroup, false);

        ContactAdapter.ContactViewHolder vh = new ContactViewHolder(itemView, new ContactAdapter.ContactViewHolder.IContactViewHolderClicks() {
            public void triggerOnClickContact(View caller, String id) {
                //launch activity
                Intent intent = new Intent(activity, ApplicantDetailsActivity.class);
                ((ItwApplication) activity.getApplication()).setApplicantId(id);
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
        protected ImageView vGravatar;
        protected ImageView vLanguage;
        public IContactViewHolderClicks mListener;

        //need to retrieve the container (ie the root ViewGroup from your custom_item_layout), it's the view that will be animated
        FrameLayout container;

        public ContactViewHolder(View v, IContactViewHolderClicks listener) {
            super(v);
            container = (FrameLayout) itemView.findViewById(R.id.card_view_contact);
            mListener = listener;
            cardView = (CardView) v.findViewById(R.id.card_view_contact);
            vId = (TextView) v.findViewById(R.id.id);
            vMail =  (TextView) v.findViewById(R.id.mail);
            vDeadline = (TextView)  v.findViewById(R.id.deadline);
            vGravatar = (ImageView)  v.findViewById(R.id.contact_gravatar);
            vLanguage = (ImageView)  v.findViewById(R.id.language);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.triggerOnClickContact(v, vId.getText().toString());
        }

        public static interface IContactViewHolderClicks {
            public void triggerOnClickContact(View caller, String id);
        }
    }
}
