package com.dict.audio.audio_dictionary;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

/**
 * Created by raekang on 12/6/15.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timerThread = new Thread(){
            public void run() {
                try {
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
