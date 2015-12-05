package com.dict.audio.audio_dictionary;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dict.audio.audio_dictionary.database.DatabaseHelper;
import com.dict.audio.audio_dictionary.database.Feedback;
import com.dict.audio.audio_dictionary.database.Submission;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/*
* Created and implemented by Yinchen Zhang, Rae Kang
* */
/**
 * This activity is after the user clicks which item to be give back to.
 * Word/Phrase is the title and an audio is playable to hear.
 * The user writes the audio as a string.
 * The user may write a feedback
 */
public class FeedbackTwoActivity extends Activity {
    private MediaPlayer mPlayer;
    private SeekBar seekbar;
    private int uid;
    private int sid;
    private String submissionWord;
    private String whatYouHear;
    private String feedback;
    private DatabaseHelper db;
    private Date date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback2);
        Intent starter = getIntent();

        uid = starter.getIntExtra("UID",uid);
        sid = starter.getIntExtra("SID", sid);
        submissionWord = starter.getStringExtra("Word");

        db = DatabaseHelper.getInstance(this);

        if (starter!=null && starter.getStringExtra("Word")!=null &&
                starter.getStringExtra("UID")!=null &&
                starter.getStringExtra("SID")!=null) {
            TextView pron = (TextView) findViewById(R.id.pronName);
            pron.setText(submissionWord);
            seekbar = (SeekBar)findViewById(R.id.seekBarPlay);
            seekbar.setClickable(false);

            final int duration = mPlayer.getDuration();
            final int amtToUpdate = duration / 100;
            final Timer mTimer= new Timer();



            Button submit = (Button) findViewById(R.id.submitFeedback);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO save the feedback in a persistent state somehow

                    whatYouHear = findViewById(R.id.textYouHear).toString();
                    feedback = findViewById(R.id.giveFeedback).toString();

                    Submission submission = db.getSubmission(sid);

                    if(whatYouHear.equals(submissionWord)){
                        submission.upvote++;
                    }else{
                        submission.downvote++;
                    }

                    

                    date = new Date();

                    Feedback fb = new Feedback(0,sid,uid, whatYouHear, feedback, date.toString());

                    db.addFeedback(fb);

                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            });
            final ImageButton playButton = (ImageButton) findViewById(R.id.playImageButton);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPlayer.isPlaying()) {
                        playButton.setImageResource(R.drawable.btn_play_normal);
                        mPlayer.stop();
                    }
                    else {
                        playButton.setImageResource(R.drawable.btn_pause_normal);
                        mPlayer.start();
                        mTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if(!(amtToUpdate * seekbar.getProgress() >= duration)) {
                                    int p = seekbar.getProgress();
                                    p += 1;
                                    seekbar.setProgress(p);
                                }
                            }
                        },amtToUpdate);
                    }
                }
            });
        }
        else {
            Log.e(MainActivity.TAG, "FeedBackTwoActivity started invalidly");
            return;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
