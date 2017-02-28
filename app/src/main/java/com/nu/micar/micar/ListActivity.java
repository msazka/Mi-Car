package com.nu.micar.micar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
public class ListActivity extends AppCompatActivity {

    //protected void onCreate(Bundle savedInstanceState) {
      //  super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_list);
    //}
    // Array of strings...
    String[] mobileArray = {"LEC 6755","LEC 6755","LEC 6755","LEC 6755",
            "LEC 6755","LEC 6755","LEC 6755","LEC 6755"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_car_list);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.car_items, mobileArray);

        ListView listView = (ListView) findViewById(R.id.car_list);
        listView.setAdapter(adapter);
    }
}