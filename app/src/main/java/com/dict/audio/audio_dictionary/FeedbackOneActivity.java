package com.dict.audio.audio_dictionary;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class FeedbackOneActivity extends ListActivity {

    /* This activity is from the profile screen. The user wants to give a feedback.
     * This screen shows the list of all the submissions by other users.
     * All the items are clickable. Clicking an item will lead to FeedbackTwoActivity
     */
    private List<String> listVals;

    //TODO needs a layout adapter to show the list. We need to get the list from the server.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedbackscreennumone);

        listVals = getDataFromDb();
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, R.layout.row_layout,R.id.pronounWord,listVals);
        setListAdapter(mAdapter);
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
        Intent intent = new Intent(this,FeedbackTwoActivity.class);
        intent.putExtra("Word",selectedItem);
        startActivity(intent);
    }
    private List<String> getDataFromDb() {
        ArrayList<String> result = new ArrayList<String>();
        result.add("fork");
        result.add("you");
        result.add("guys");
        return result;
    }

}
