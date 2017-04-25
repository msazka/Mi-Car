package com.nu.micar;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Waqar Malik on 26-Apr-17.
 */

public class ParentActivity extends AppCompatActivity {


    public static Animation ballAnim;
    public static Dialog dialog;

    public static String errorMsgAPI = "";

    /**
     * *********************************** showLoading *********************************************
     */
    public static void showLoading(final Context context) {

        Handler h = new Handler();
        h.post(new Runnable() {
            public void run() {
                ballAnim = AnimationUtils.loadAnimation(context,
                        R.anim.ball_rotatation);
                dialog = new Dialog(context, android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.ball_progress_dialog);
                dialog.setCancelable(false);
                final ImageView ivBall = (ImageView) dialog
                        .findViewById(R.id.ivBall);
                ivBall.startAnimation(ballAnim);
                dialog.show();
            }
        });
    }

    /**
     * ********************************** hideLoading **********************************************
     */
    public static void hideLoading(final Context context) {
        try {
            dialog.dismiss();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //mVolleyQueue = Volley.newRequestQueue(FleetApplication.getContext(), new com.ssl.SslHttpStack(true));
    }

    /**
     * ********************************************** distance *************************************
     */
    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        int earthRadius = 6371; // Earth Radius in km
        double dLat = (double) Math.toRadians(lat2 - lat1);
        double dLon = (double) Math.toRadians(lon2 - lon1);
        double a =
                (double) (Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
                        * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2));
        double c = (double) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        double d = earthRadius * c;
        return d;
    }

    /**
     * *********************************** haveNetworkConnection ***********************************
     */

    public boolean haveNetworkConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            Log.i("Value", "Yes");
            return true;
        }
        Log.i("Value", "No");
        return false;
    }


    protected void sharedPrefData(String tag, String value) {
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(ParentActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(tag, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ******************************** getSharedPrefData ******************************************
     */
    public String getSharedPrefData(String tag) {
        String res = "";
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(ParentActivity.this);
            res = prefs.getString(tag, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * ****************************************** getBooleanFromPrefs ******************************
     */
    protected boolean getBooleanFromPrefs(String tag) {
        boolean res = false;
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(ParentActivity.this);
            res = prefs.getBoolean(tag, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    /**
     * ****************************************** putBooleanInPrefs ********************************
     */
    protected void putBooleanInPrefs(String tag, boolean value) {
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(ParentActivity.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(tag, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ******************************** clearAllPrefrences *****************************************
     */
    protected void clearAllPrefrences() {
        try {
           /* SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(AuthenticateParentActivity.this);
            prefs.edit().clear().commit();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * *************************************** showToast *******************************************
     */
    protected void showToast(String text) {
        try {
            Toast.makeText(ParentActivity.this, text,
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ****************************************** hideSoftKeyboard *********************************
     */
    protected void hideSoftKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void SharedPrefData(String tag, String value) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(ParentActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(tag, value);
        editor.commit();
    }


}
