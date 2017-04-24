package com.nu.micar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * Created by Waqar Malik on 24-Apr-17.
 */


public class Splash extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (getSharedPrefData("IsLogin").equals("true")) {

            Intent i = new Intent(Splash.this, MainActivity.class);
            startActivity(i);
            finish();

        } else {

            Intent i = new Intent(Splash.this, MiCarMainActivity.class);
            startActivity(i);
            finish();

        }


    }


    /**
     * ******************************** getSharedPrefData ******************************************
     */
    public String getSharedPrefData(String tag) {
        String res = "";
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(Splash.this);
            res = prefs.getString(tag, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


}