package com.dict.audio.audio_dictionary;

import android.app.Activity;
import android.content.Intent;
import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dict.audio.audio_dictionary.database.UserDatabaseHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity is after the user clicks an item from the profile screen.
 * This activty shows the word/phrase on the top.
 * This activity shows the upvote and downvote.
 * This activity shows the list of feedbacks from other people
 */
public class MyPronunciationActivity extends ListActivity {
    private List<String> listVals;
    UserDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypronscreen);
        Intent starter = getIntent();
        if (starter!=null) {
            TextView theWord = (TextView) findViewById(R.id.pronWord);
            theWord.setText(starter.getStringExtra("Word").toString());
            db = UserDatabaseHelper.getInstance(this);
            //TODO populate the display feedback and vote up and vote down
            listVals = db.getPronouns("DELETEME");
            ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.row_layout,R.id.feedbackView,listVals);
            setListAdapter(mAdapter);
        }
        else {
            throw new IllegalStateException("Pronunciation Activity not started with intent");
        }
        //TODO need to check if this works (reference Placebages)

        //TODO specific word or phrase needs to be passed as the title

        //TODO upvote and downvote needs to obtained from the server

        //TODO feedbacks need to be obtained from the server to be shown.

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
