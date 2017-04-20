package com.nu.micar;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.SignInButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MiCarSignIn extends AppCompatActivity {
    private static final String TAG = "SIGNINACTIVITY==";
    public static Dialog dialog;
    public static Animation ballAnim;
    RequestQueue requestQueue;
    EditText input_email, input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micar_sign_in);

        requestQueue = Volley.newRequestQueue(MiCarSignIn.this);


        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        Button button = (Button) findViewById(R.id.LoginViaFb);


        button.setOnClickListener(new View.OnClickListener()

                                  {
                                      public void onClick(View v) {
                                          Intent intent = new Intent(MiCarSignIn.this, MiCarLoginViaFB.class);
                                          startActivity(intent);
                                          // Perform action on click
                                      }
                                  }
        );

        SignInButton gbutton = (SignInButton) findViewById(R.id.sign_in_button);
        gbutton.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           Log.d("activity_micar_test", "suceswwwwss");
                                           Intent intent = new Intent(MiCarSignIn.this, MiCarSignInGoogle.class);
                                           startActivity(intent);
                                           // Perform action on click
                                       }
                                   }
        );


    }

    public void loginUser(View view) {
        // Google Volley Post Request


        hideSoftKeyboard(view);
        showLoading(MiCarSignIn.this);

        String url = getResources().getString(R.string.webservicesurl);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        hideLoading(MiCarSignIn.this);


                        try {
                            JSONObject parent = new JSONObject(response);
                            int successStatus = parent.getInt("success");
                            String successMessage = parent.getString("message");

                            if (successStatus == 0) {

                                showToast(successMessage);
                            } else {

                                JSONObject dataObject = new JSONObject(parent.getString("data"));
                                JSONObject profileObject = new JSONObject(dataObject.getString("profile"));

                               /* sharedPrefData("username", profileObject.getString("samia"));
                                sharedPrefData("email", profileObject.getString("email"));
                                sharedPrefData("password", profileObject.getString("password"));
                                sharedPrefData("account_type", profileObject.getString("account_type"));
                                sharedPrefData("session_token", profileObject.getString("session_token"));*/

                                showToast(successMessage);


                                Intent schoolActivityIntent = new Intent(MiCarSignIn.this, DemoMapsActivity.class);
                                startActivity(schoolActivityIntent);
                                finish();

                            }

                        } catch (Exception e) {

                            e.printStackTrace();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        hideLoading(MiCarSignIn.this);
                        // error
                        //    Log.d("Error.Response", response );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("method", "userLogin");
                params.put("email", input_email.getText().toString().trim());
                params.put("password", input_password.getText().toString().trim());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MiCarSignIn.this);
        requestQueue.add(postRequest);

    /* AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("method","userLogin");
        params.put("email","samia.nouman@yahoo.com");
        params.put("password","12345");


        RequestHandle requestHandle = client.get("http://micar.000webhostapp.com/micar/api/page.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("activity_micar_test", "sucesss" + responseBody);
                JSONObject JSON = new JSONObject();
                Log.e("koi b msg",responseBody.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("activity_micar_test", "fail");
            }

        });*/
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

    protected void sharedPrefData(String tag, String value) {
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(MiCarSignIn.this);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(tag, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showToast(String text) {
        try {
            Toast.makeText(MiCarSignIn.this, text,
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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



}
