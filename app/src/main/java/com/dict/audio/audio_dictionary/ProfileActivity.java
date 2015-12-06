package com.dict.audio.audio_dictionary;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.dict.audio.audio_dictionary.database.DatabaseHelper;
import com.dict.audio.audio_dictionary.database.Submission;
import com.dict.audio.audio_dictionary.database.User;

import java.util.ArrayList;
import java.util.List;

/*
* Created and implemented by Yinchen Zhang, Rae Kang
* */
/**
 *  This activity is after the user sign in.
 *  The user may give a feedback or receive a feedback.
 *  This page shows the user ID and the number of tokens it has.
 *  This page also shows the list of submissions that can be clicked to view the feedback.
 */

public class ProfileActivity extends ListActivity {

    public final int GET_FEEDBACK = 1;
    public final int GIVE_FEEDBACK = 2;
    private ArrayAdapter<String> mAdapter;
    private List<String> listVals;
    private DatabaseHelper db;
    private ArrayList<Submission> currSubs;
    private TextView tokens;
    private User currUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilescreen);
        final Intent starter = getIntent();
        if (starter != null) {
            TextView userName = (TextView) findViewById(R.id.username);
            userName.setText(starter.getStringExtra(MainActivity.USER_ID).toString());
            listVals = new ArrayList<String>();
            //TODO query database for number of tokens current user has
            tokens = (TextView) findViewById(R.id.numTokens);
            db = DatabaseHelper.getInstance(this);
            currUser = db.getUserByName(starter.getStringExtra(MainActivity.USER_ID).toString());
            updateTokens();
            //button for giving a feedback and receiving a feedback
            Button giveFeedback = (Button) findViewById(R.id.giveFeedback);
            Button getFeedback = (Button) findViewById(R.id.useTokens);

            giveFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), FeedbackOneActivity.class);

                    User user = db.getUserByName(starter.getStringExtra(MainActivity.USER_ID).toString());
                    int uid = user.uid;
                    intent.putExtra("UID", uid);
                    startActivity(intent);
                }
            });

            getFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), SubmitActivity.class);
                    intent.putExtra("UserId",currUser.uid);
                    startActivityForResult(intent,GET_FEEDBACK);
                }
            });
            updatePronouns();
            mAdapter = new ArrayAdapter<String>(this, R.layout.row_layout,R.id.pronounWord,listVals);
            setListAdapter(mAdapter);
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
    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        String selectedItem = (String) getListView().getItemAtPosition(position);
        Submission currentSubmission = currSubs.get(position);
        Intent intent = new Intent(this, MyPronunciationActivity.class);
        intent.putExtra("SubmissionId", currentSubmission.sid);
        intent.putExtra("Word",selectedItem);
        startActivityForResult(intent, 1);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == GET_FEEDBACK ) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK) {
//                updateTokens();
//                updatePronouns();
//            }
//        }
//        else if (requestCode == GIVE_FEEDBACK) {
//            if (requestCode == RESULT_OK){
//               updateTokens();
//            }
//        }
//    }
    private void updateTokens() {
        currUser = db.getUserByUid(currUser.uid);
        tokens.setText(Integer.toString(currUser.tokens));
    }
    private void updatePronouns() {
        currSubs = db.getUserSubmissions(currUser);
        convertSubList(currSubs);
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    protected  void convertSubList(ArrayList<Submission> in) {
        listVals.clear();
        for (Submission ele: in ) {
            listVals.add(ele.word);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        updateTokens();
        updatePronouns();
        Log.e(MainActivity.TAG,"ProfileActivity onResume called");
    }
}
