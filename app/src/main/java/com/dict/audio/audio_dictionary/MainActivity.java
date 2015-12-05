package com.dict.audio.audio_dictionary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dict.audio.audio_dictionary.database.DatabaseHelper;

public class MainActivity extends Activity {
    public final static String USER_ID = "UserID";
    public final static String TAG = "Audio-dict";
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button login = (Button) findViewById(R.id.logIn);
        Button signup = (Button) findViewById(R.id.signUp);
        db = DatabaseHelper.getInstance(this);
        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView userId = (TextView) findViewById(R.id.userIDMain);
                        TextView pw = (TextView) findViewById(R.id.passwordMain);
                        //TODO authenticate user and start acitviity based on results of authetnitcation
                        if (userId.getText().toString() != null || pw.getText().toString() != null) {
                            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                            intent.putExtra(USER_ID, userId.getText().toString());
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Please provide username and password",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
