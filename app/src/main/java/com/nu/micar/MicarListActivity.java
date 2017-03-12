package com.nu.micar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nu.micar.R;

public class MiCarListActivity extends AppCompatActivity {

    //protected void onCreate(Bundle savedInstanceState) {
      //  super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_micar_list);
    //}
    // Array of strings...
    String[] mobileArray = {"LEC 6755","LEC 6755","LEC 6755","LEC 6755",
            "LEC 6755","LEC 6755","LEC 6755","LEC 6755"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micar_track_car_list);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_micar_car_items, mobileArray);

        ListView listView = (ListView) findViewById(R.id.car_list);
        listView.setAdapter(adapter);
    }
}