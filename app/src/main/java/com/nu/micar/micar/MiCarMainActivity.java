package com.nu.micar.micar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.facebook.FacebookSdk;


public class MiCarMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_car_main);

        Button button = (Button) findViewById(R.id.SignIn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MiCarMainActivity.this, SignIn.class);
                startActivity(intent);
                // Perform action on click
            }
        });
    }
}
