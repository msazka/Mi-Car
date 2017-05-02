package com.nu.micar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Waqar Malik on 24-Apr-17.
 */


public class Splash extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.nu.micar",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/

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