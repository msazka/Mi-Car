package com.nu.micar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class MiCarMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micar_main);

        Button button = (Button) findViewById(R.id.SignIn);
        button.setOnClickListener(this);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(MiCarMainActivity.this, MiCarSignIn.class);
//                startActivity(intent);
//                // Perform action on click
//            }
//        });
        Button signup = (Button) findViewById(R.id.SignUp);
        signup.setOnClickListener(this);
//        signup.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intent = new Intent(MiCarMainActivity.this, MiCarSignUp.class);
//                startActivity(intent);
//                // Perform action on click
//            }
//        });

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.SignIn){
            Intent intent = new Intent(MiCarMainActivity.this, MiCarSignIn.class);
                startActivity(intent);
        }

        if(id == R.id.SignUp){
            Intent intent = new Intent(MiCarMainActivity.this, MiCarSignUp.class);
                startActivity(intent);
        }

    }
}
