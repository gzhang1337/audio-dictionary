package com.dict.audio.audio_dictionary;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This activity is from ProfileActivity. User wants to receive a feedback.
 * The specific user has to be known.
 * This records an audio of the user's pronunciation.
 * It can also playback the user's pronunciation
 * By clicking the submit button, it will the data to the server
 *
 */
public class SubmitActivity extends Activity {
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private Button recordButton = null;
    private Button playButton = null;
    private String outputFile = null;
    private boolean recording = false;
    private boolean playing = false;
    private Timer timer;
    private SeekBar seekBar;
    private int duration;
    private int amtToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitscreen);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() +"/record.3gp";
        //TODO how to record an audio.
        recordButton = (Button) findViewById(R.id.startRecord);
        seekBar = (SeekBar) findViewById(R.id.seekBarRecord);


        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recording) {
                    recordButton.setText("Stop recording");
                    startRecording();
                    recording = true;
                }
                else {//stop recording
                    recordButton.setText("Start Recording");
                    stopRecording();
                    recording = false;
                    playButton.setEnabled(true);
                }
            }
        });
        playButton = (Button) findViewById(R.id.playButton);
        playButton.setEnabled(false);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playing) {
                    playButton.setText("Stop Playing");
                    startPlaying();
                    playing = true;
                }
                else {
                    playButton.setText("Playback");
                    stopPlaying();
                    playing = false;
                }
            }
        });

        //submit button
        Button submit = (Button) findViewById(R.id.submitPron);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO submit the data to the server
            }
        });
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
    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(outputFile);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "prepare failed");
        }
        mRecorder.start();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                int p = seekBar.getProgress();
                p += 1;
                seekBar.setProgress(p);
            }
        },null);
    }
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        duration = seekBar.getProgress();
        amtToUpdate = duration / 100;
        seekBar.setProgress(0);

    }
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(outputFile);
            mPlayer.prepare();
            mPlayer.start();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int p = seekBar.getProgress();
                    p += 1;
                    seekBar.setProgress(p);
                }
            },amtToUpdate);

        } catch (IOException e) {
            Log.e(MainActivity.TAG, "prepare() failed");
        }
    }
    private void stopPlaying() {
        if (!mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.release();
        mPlayer = null;
    }
}
