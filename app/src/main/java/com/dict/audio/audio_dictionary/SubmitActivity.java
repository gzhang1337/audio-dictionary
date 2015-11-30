package com.dict.audio.audio_dictionary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * This activity is from ProfileActivity. User wants to receive a feedback.
 * The specific user has to be known.
 * This records an audio of the user's pronunciation.
 * It can also playback the user's pronunciation
 * By clicking the submit button, it will the data to the server
 *
 */
public class SubmitActivity extends AppCompatActivity{

    String whatYouAreSaying ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitscreen);

        whatYouAreSaying = findViewById(R.id.pronWord).toString();

        //TODO how to record an audio.

        //TODO playback an audio

        //TODO checkbox for ENG or SPN


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
}
