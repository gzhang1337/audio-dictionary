package com.dict.audio.audio_dictionary;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dict.audio.audio_dictionary.database.DatabaseHelper;
import com.dict.audio.audio_dictionary.database.User;


/*
* Created and implemented by Yinchen Zhang, Rae Kang
* */
public class SignUpActivity extends AppCompatActivity {

    private EditText userEdit;
    private EditText passEdit;
    private EditText passConfirmEdit;
    private EditText emailEdit;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupscreen);
        Button signUp = (Button) findViewById(R.id.finishSignUp);
        db = DatabaseHelper.getInstance(this);

        userEdit = (EditText) findViewById(R.id.userID);
        passEdit = (EditText) findViewById(R.id.password);
        passConfirmEdit = (EditText) findViewById(R.id.cPassword);
        emailEdit = (EditText) findViewById(R.id.email);

        signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String user = userEdit.getText().toString();
                        String pass = userEdit.getText().toString();
                        String passConfirm = userEdit.getText().toString();
                        String email = userEdit.getText().toString();

                        if (user.length() > 0 && pass.length() > 0 && passConfirm.length() > 0 && email.length() > 0) {

                            if (pass.equals(passConfirm)) {
                                // TODO connecto to database and validate the email has not been registered to another id
                                User newUser = new User(0, user, pass, 3);
                                db.addUser(newUser);

                                Toast.makeText(getApplicationContext(),
                                        "Sign up successful",
                                        Toast.LENGTH_LONG).show();

                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Passwords do not match",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Please fill out all fields",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        Button clear = (Button) findViewById(R.id.clearbutton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdit.setText("");
                passEdit.setText("");
                passConfirmEdit.setText("");
                emailEdit.setText("");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }
}
