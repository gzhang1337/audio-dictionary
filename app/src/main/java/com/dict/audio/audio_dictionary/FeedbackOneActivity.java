package com.dict.audio.audio_dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by raekang on 11/29/15.
 */
public class FeedbackOneActivity extends AppCompatActivity {

    /* This activity is is from the profile screen. The user wants to give a feedback.
     * This screen shows the list of all the submissions by other users.
     * All the items are clickable. Clicking an item will lead to FeedbackTwoActivity
     */


    //TODO needs a layout adapter to show the list. We need to get the list from the server.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbackscreennumone);

        Intent starter = getIntent();
    // hi
        if (starter != null) {


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
