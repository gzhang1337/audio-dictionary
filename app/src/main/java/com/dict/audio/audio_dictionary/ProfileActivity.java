package com.dict.audio.audio_dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *  This activity is after the user sign in.
 *  The user may give a feedback or receive a feedback.
 *  This page shows the user ID and the number of tokens it has.
 *  This page also shows the list of submissions that can be clicked to view the feedback.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilescreen);
        Intent starter = getIntent();
        if (starter != null) {
            TextView userName = (TextView) findViewById(R.id.username);
            userName.setText(starter.getStringExtra(MainActivity.USER_ID));


            //TODO query database for number of tokens current user has


            //button for giving a feedback and receiving a feedback
            Button giveFeedback = (Button) findViewById(R.id.giveFeedback);
            Button getFeedback = (Button) findViewById(R.id.submitFeedback);

            giveFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), FeedbackOneActivity.class);
                    startActivity(intent);
                }
            });

            getFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), SubmitActivity.class);
                    startActivity(intent);
                }
            });

            //TODO need a view adapter to show all of user's pronunciation
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
