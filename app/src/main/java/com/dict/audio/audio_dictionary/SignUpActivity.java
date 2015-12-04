package com.dict.audio.audio_dictionary;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SignUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupscreen);
        Button signUp = (Button) findViewById(R.id.finishSignUp);
        signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView userID = (TextView) findViewById(R.id.userID);
                        TextView passWord = (TextView) findViewById(R.id.password);
                        TextView confirmPw = (TextView) findViewById(R.id.cPassword);
                        TextView email = (TextView) findViewById(R.id.email);
                        if (passWord.getText().toString().equals(confirmPw.getText().toString())) {
                            // TODO connecto to database and validate the email has not been registered to another id
                            Toast.makeText(getApplicationContext(),"Sign up successful",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        Button clear = (Button) findViewById(R.id.clearbutton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView userID = (TextView) findViewById(R.id.userID);
                TextView passWord = (TextView) findViewById(R.id.password);
                TextView confirmPw = (TextView) findViewById(R.id.cPassword);
                TextView email = (TextView) findViewById(R.id.email);
                userID.setText("");
                passWord.setText("");
                confirmPw.setText("");
                email.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
