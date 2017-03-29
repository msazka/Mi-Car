package com.nu.micar;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.SignInButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.logging.Logger;

import cz.msebera.android.httpclient.Header;

public class MiCarSignIn extends AppCompatActivity{
    private static final String TAG = "SIGNINACTIVITY==";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micar_sign_in);



        Button button = (Button) findViewById(R.id.LoginViaFb);


            button.setOnClickListener(new View.OnClickListener()

            {
                public void onClick (View v){
                Intent intent = new Intent(MiCarSignIn.this, MiCarLoginViaFB.class);
                startActivity(intent);
                // Perform action on click
            }
            }
            );

        SignInButton gbutton = (SignInButton) findViewById(R.id.sign_in_button);
        gbutton.setOnClickListener(new View.OnClickListener() {
              public void onClick (View v){
                  Log.d("activity_micar_test", "suceswwwwss");
                  Intent intent = new Intent(MiCarSignIn.this, MiCarSignInGoogle.class);
                  startActivity(intent);
                  // Perform action on click
              }}
        );


        }

    public void loginUser(View view) {
        AsyncHttpClient client = new AsyncHttpClient();
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

        });
    }



}
