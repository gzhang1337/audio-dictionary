package com.dict.audio.audio_dictionary;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 *  This activity is after the user sign in.
 *  The user may give a feedback or receive a feedback.
 *  This page shows the user ID and the number of tokens it has.
 *  This page also shows the list of submissions that can be clicked to view the feedback.
 */

public class ProfileActivity extends ListActivity {

    private List<String> listVals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profilescreen);
        Intent starter = getIntent();
        if (starter != null) {
            TextView userName = (TextView) findViewById(R.id.username);
            userName.setText(starter.getStringExtra(MainActivity.USER_ID).toString());


            //TODO query database for number of tokens current user has


            //button for giving a feedback and receiving a feedback
            Button giveFeedback = (Button) findViewById(R.id.giveFeedback);
            Button getFeedback = (Button) findViewById(R.id.useTokens);

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

            //TODO need to check if this works (reference Placebages)

            listVals = getDataFromDb();
            ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.row_layout,R.id.listOfMyPron,listVals);
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

    private List<String> getDataFromDb() {
        ArrayList<String> result = new ArrayList<String>();
        result.add("fork");
        result.add("you");
        result.add("guys");
        return result;
    }
}
