package com.dict.audio.audio_dictionary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.dict.audio.audio_dictionary.database.DatabaseHelper;
import com.dict.audio.audio_dictionary.database.Submission;
import com.dict.audio.audio_dictionary.database.User;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/*
* Created and implemented by Yinchen Zhang, Rae Kang
* */
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
    private final int MAX_RECORD_TIME = 60000;
    private DatabaseHelper db;
    private boolean recorded = false;
    private Long time;
    private CheckBox checkEng, checkSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitscreen);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        time = System.currentTimeMillis() / 1000;

        // ensure AudioRecording folder is created
        File audioFolder = new File(outputFile + "/AudioRecording/");
        if (!audioFolder.mkdirs() && !audioFolder.isDirectory()) {
            outputFile = outputFile + "/" + time.toString() + ".3gp";
        } else {
            //unique audio file to save in the directory using the timestamp
            outputFile = outputFile + "/AudioRecording/" + time.toString() + ".3gp";
        }

        //TODO how to record an audio.
        recordButton = (Button) findViewById(R.id.startRecord);
        seekBar = (SeekBar) findViewById(R.id.seekBarRecord);
        seekBar.setMax(MAX_RECORD_TIME);
        seekBar.setClickable(false);
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recording) {
                    recordButton.setText("Stop recording");
                    startRecording();
                    recording = true;
                } else {//stop recording
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
                } else {
                    playButton.setText("Playback");
                    stopPlaying();
                }
            }
        });
        db = DatabaseHelper.getInstance(this);
        //submit button
        Button submit = (Button) findViewById(R.id.submitPron);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText wordPronounced = (EditText) findViewById(R.id.pronWord);
                String word = wordPronounced.getText().toString();
                if (word!=null && !word.isEmpty()) {
                    if (checkEng.isChecked() ^ checkSpan.isChecked()) {
                        if (recorded) {

                            int uid = getIntent().getIntExtra("UserId", -1);

                            Submission toAdd = new Submission(-1, uid, word, outputFile, "", time.toString(), 0, 0);
                            db.addSubmission(toAdd);
                            Toast.makeText(SubmitActivity.this, "Submission success", Toast.LENGTH_SHORT).show();

                            //TODO update the user token

                            User user = db.getUserByUid(uid);

                            user.tokens--;

                            //TODO update the user table
                            db.updateUserTokens(user.uid,user.tokens);
                            Intent returnIntent = new Intent();
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

                        } else {
                            Toast.makeText(SubmitActivity.this, "Please Record an audio.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SubmitActivity.this, "Please choose a language.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(SubmitActivity.this,"Please type the word you are pronouncing",Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkEng = (CheckBox) findViewById(R.id.checkEng);
        checkSpan = (CheckBox) findViewById(R.id.checkSpan);
        checkEng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkSpan.setChecked(false);
            }
        });
        checkSpan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkEng.setChecked(false);
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
        seekBar.setMax(MAX_RECORD_TIME);
        seekBar.setProgress(0);
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
                p += 1000;
                seekBar.setProgress(p);
            }
        }, 1, 1000);
    }
    private void stopRecording() {
        mRecorder.stop();
        timer.cancel();
        mRecorder.release();
        mRecorder = null;
        recorded = true;
    }
    private void startPlaying() {
        playing = true;
        seekBar.setProgress(0);
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playButton.setText("Playback");
                stopPlaying();
            }
        });
        try {
            mPlayer.setDataSource(outputFile);
            mPlayer.prepare();
            duration = mPlayer.getDuration();
            seekBar.setMax(duration);
            mPlayer.start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int currentPosition = 0;
                    while(currentPosition<duration && mPlayer != null) {
                        try {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException e) {
                            return;
                        }
                        if(mPlayer!=null) {
                            currentPosition = mPlayer.getCurrentPosition();
                            seekBar.setProgress(currentPosition);
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            Log.e(MainActivity.TAG, "prepare() failed");
        }
    }
    private void stopPlaying() {
        if (!mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        seekBar.setProgress(0);
        mPlayer.release();
        mPlayer = null;
        playing = false;
    }
}
