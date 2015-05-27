package com.fourreau.itwapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourreau.itwapp.R;
import com.fourreau.itwapp.activity.InterviewActivity;
import com.fourreau.itwapp.core.ItwApplication;
import com.fourreau.itwapp.model.InterviewDto;
import com.fourreau.itwapp.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Pierre on 08/05/2015.
 */
public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder> {

    private Activity activity;

    private List<InterviewDto> interviewList;

    //allows to remember the last item shown on screen
    private int lastPosition = -1;

    public InterviewAdapter(Activity activity, List<InterviewDto> interviewList) {
        this.activity = activity;
        this.interviewList = interviewList;
    }

    @Override
    public int getItemCount() {
        return interviewList.size();
    }

    @Override
    public void onBindViewHolder(InterviewViewHolder interviewViewHolder, int i) {
        InterviewDto itw = interviewList.get(i);
        interviewViewHolder.vId.setText(itw.getId());
        Picasso.with(activity).load(Utils.URL_THUMBNAIL_YOUTUBE_BEGIN + itw.getVideoId() + Utils.URL_THUMBNAIL_YOUTUBE_END).fit().centerCrop().placeholder(R.drawable.youtube_logo).error(R.drawable.youtube_logo).into(interviewViewHolder.vThumbnailYoutube);
        interviewViewHolder.vTitle.setText(itw.getTitle());
        interviewViewHolder.vSent.setText(Integer.toString(itw.getSent()));
        interviewViewHolder.vAnswers.setText(Integer.toString(itw.getAnswers()));
        interviewViewHolder.vNew.setText(Integer.toString(itw.getNews()));
        if(itw.getNews() > 0) {
            Picasso.with(activity).load(R.drawable.ic_warning).into(interviewViewHolder.vImageNews);
        }
        //apply the animation when the view is bound
        setAnimation(interviewViewHolder.container, i);
    }

    /**
     * Here is the method to apply the animation.
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
    public InterviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.interview_item, viewGroup, false);

        InterviewAdapter.InterviewViewHolder vh = new InterviewViewHolder(itemView, new InterviewAdapter.InterviewViewHolder.IInterviewViewHolderClicks() {
            public void triggerOnClickInterview(View caller, String id) {
                //launch activity
                Intent intent = new Intent(activity, InterviewActivity.class);
                ((ItwApplication) activity.getApplication()).setInterviewId(id);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
            };
        });
        return vh;
    }

    public static class InterviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected CardView cardView;
        protected TextView vId;
        protected ImageView vThumbnailYoutube;
        protected TextView vTitle;
        protected TextView vSent;
        protected TextView vAnswers;
        protected TextView vNew;
        protected ImageView vImageNews;
        public IInterviewViewHolderClicks mListener;

        //need to retrieve the container (ie the root ViewGroup from your custom_item_layout), it's the view that will be animated
        FrameLayout container;

        public InterviewViewHolder(View v, IInterviewViewHolderClicks listener) {
            super(v);
            container = (FrameLayout) itemView.findViewById(R.id.card_view_interview);
            mListener = listener;
            cardView = (CardView) v.findViewById(R.id.card_view_interview);
            vId = (TextView) v.findViewById(R.id.id);
            vThumbnailYoutube = (ImageView)  v.findViewById(R.id.thumbnail_youtube);
            vTitle =  (TextView) v.findViewById(R.id.itw_title);
            vSent = (TextView)  v.findViewById(R.id.itw_sent);
            vAnswers = (TextView)  v.findViewById(R.id.itw_answers);
            vNew = (TextView)  v.findViewById(R.id.itw_new);
            vImageNews = (ImageView)  v.findViewById(R.id.ic_news);
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.triggerOnClickInterview(v, vId.getText().toString());
        }

        public static interface IInterviewViewHolderClicks {
            public void triggerOnClickInterview(View caller, String id);
        }
    }
}
