package com.nu.micar.micar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class SignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

            Button button = (Button) findViewById(R.id.LoginViaFb);
            button.setOnClickListener(new View.OnClickListener()

            {
                public void onClick (View v){
                Intent intent = new Intent(SignIn.this, LoginViaFB.class);
                startActivity(intent);
                // Perform action on click
            }
            }

            );
        };

    public void loginUser(View view){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params= new RequestParams();

        RequestHandle requestHandle = client.get("http://http://localhost/micar/test.js", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("test", "sucesss");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("test", "fail");
            }

        });
    };
}
