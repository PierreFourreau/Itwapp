package com.fourreau.itwapp.activity;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.fourreau.itwapp.R;

import io.itwapp.models.Response;
import timber.log.Timber;

public class ResponseVideoActivity extends ActionBarActivity {

    private String urlVideo;

    private VideoView videoViewResponse;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_video);

        //get url video from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            urlVideo = extras.getString("url");
        }

        if(urlVideo != null) {
            //set the media controller buttons
            if (mediaControls == null) {
                mediaControls = new MediaController(ResponseVideoActivity.this);
            }

            videoViewResponse = (VideoView) findViewById(R.id.video_view_response);

            progressDialog = new ProgressDialog(ResponseVideoActivity.this);
            progressDialog.setTitle(R.string.title_activity_response_video);
            progressDialog.setMessage(ResponseVideoActivity.this.getString(R.string.dialog_loading));
            progressDialog.setCancelable(true);
            progressDialog.show();

            try {
                //set the media controller in the VideoView
                videoViewResponse.setMediaController(mediaControls);
                //set the uri of the video to be played
                videoViewResponse.setVideoURI(Uri.parse(urlVideo));

            } catch (Exception e) {
                Timber.e("ResponseVideoActivity:" + e.toString());
            }

            videoViewResponse.requestFocus();
            //we also set an setOnPreparedListener in order to know when the video file is ready for playback
            videoViewResponse.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mediaPlayer) {
                    // close the progress bar and play the video
                    progressDialog.dismiss();
                    //if we have a position on savedInstanceState, the video playback should start from here
                    videoViewResponse.seekTo(position);
                    if (position == 0) {
                        videoViewResponse.start();
                    } else {
                        //if we come from a resumed activity, video playback will be paused
                        videoViewResponse.pause();
                    }
                }
            });
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
