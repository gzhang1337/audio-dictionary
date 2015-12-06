package com.dict.audio.audio_dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dict.audio.audio_dictionary.database.DatabaseHelper;
import com.dict.audio.audio_dictionary.database.User;

/*
* Created and implemented by Yinchen Zhang, Rae Kang
* */

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
                        //TODO authenticate user and start acitviity based on results of authentication
                        if (userId.getText().toString() != null || pw.getText().toString() != null) {
                            User user = db.getUserByNamePass(userId.getText().toString(),pw.getText().toString());
                            if(user != null) {
                                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                                intent.putExtra(USER_ID, userId.getText().toString());

                                userId.setText("");
                                pw.setText("");
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Invalid User, please signup",Toast.LENGTH_SHORT).show();
                            }
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
}
