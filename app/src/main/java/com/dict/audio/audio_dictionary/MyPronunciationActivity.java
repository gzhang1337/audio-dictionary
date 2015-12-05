package com.dict.audio.audio_dictionary;

import android.content.Intent;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dict.audio.audio_dictionary.database.DatabaseHelper;
import com.dict.audio.audio_dictionary.database.Feedback;
import com.dict.audio.audio_dictionary.database.Submission;

import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*
* Created and implemented by Yinchen Zhang, Rae Kang
* */
/**
 * This activity is after the user clicks an item from the profile screen.
 * This activty shows the submission
 * This activity shows the upvote and downvote.
 * This activity shows the list of feedbacks from other people
 */
public class MyPronunciationActivity extends ListActivity {
    private List<String> listVals;
    private DatabaseHelper db;
    private Submission mSubmission;
    private ArrayList<Feedback> mFeedBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypronscreen);
        Intent starter = getIntent();
        if (starter!=null) {
            TextView theWord = (TextView) findViewById(R.id.pronWord);
            theWord.setText(starter.getStringExtra("Word").toString());
            db = DatabaseHelper.getInstance(this);
            mSubmission = db.getSubmission(starter.getIntExtra("SubmissionId",-1));
            mFeedBack = db.getFeedbackForSub(mSubmission.sid);
            listVals = convertFeedBackList(mFeedBack);
            ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.row_layout,R.id.pronounWord,listVals);
            setListAdapter(mAdapter);
        }
        else {
            throw new IllegalStateException("Pronunciation Activity not started with intent");
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
    private ArrayList<String> convertFeedBackList(ArrayList<Feedback> in) {
        ArrayList<String> result = new ArrayList<String>();
        for (Feedback ele : in) {
            result.add(ele.text);
        }
        return result;
    }
}
