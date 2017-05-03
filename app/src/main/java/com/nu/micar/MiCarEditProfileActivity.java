package com.nu.micar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Waqar Malik on 02-May-17.
 */

public class MiCarEditProfileActivity extends ParentActivity {

    public static Dialog dialog;
    public static Animation ballAnim;
    RequestQueue requestQueue;
    EditText input_email, input_password, input_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micar_sign_up);


        requestQueue = Volley.newRequestQueue(MiCarEditProfileActivity.this);


        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        input_username = (EditText) findViewById(R.id.input_username);
        Button btn_signup = (Button) findViewById(R.id.btn_signup);

        try{
            input_email.setText(getSharedPrefData("email"));
            input_username.setText(getSharedPrefData("username"));
            btn_signup.setText("Update Profile");
        }
        catch (Exception e){

        }



        btn_signup.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              signupUser();
                                          }
                                      }
        );

    }

    public void signupUser() {
        // Google Volley Post Request


        //  hideSoftKeyboard(MiCarSignUp.this);
        showLoading(MiCarEditProfileActivity.this);

        String url = getResources().getString(R.string.webservicesurl);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        hideLoading(MiCarEditProfileActivity.this);


                        try {
                            JSONObject parent = new JSONObject(response);
                            int successStatus = parent.getInt("success");
                          //  String successMessage = parent.getString("message");

                            if (successStatus == 0) {

                                showToast("In Correct fields");
                            } else {

                               // JSONObject dataObject = new JSONObject(parent.getString("data"));
                                JSONObject profileObject = new JSONObject(parent.getString("profile"));

                                sharedPrefData("username", profileObject.getString("username"));
                                sharedPrefData("email", profileObject.getString("email"));
                                sharedPrefData("password", profileObject.getString("password"));
                                sharedPrefData("account_type", profileObject.getString("account_type"));
                                sharedPrefData("session_token", profileObject.getString("session_token"));

                                showToast("Profile updated successfully");

                               // sharedPrefData("IsLogin", "true");

                                Intent schoolActivityIntent = new Intent(MiCarEditProfileActivity.this, MainActivity.class);
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

                        hideLoading(MiCarEditProfileActivity.this);
                        // error
                        //    Log.d("Error.Response", response );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("method", "updateProfile");
                params.put("email", input_email.getText().toString().trim());
                params.put("password", input_password.getText().toString().trim());
                params.put("username", input_username.getText().toString().trim());
             //   params.put("account_type", "CUSTOM");
                params.put("session_token", getSharedPrefData("session_token"));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MiCarEditProfileActivity.this);
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


    @Override
    public void onBackPressed() {
        Intent schoolActivityIntent = new Intent(MiCarEditProfileActivity.this, MainActivity.class);
        startActivity(schoolActivityIntent);
        finish();
    }
}