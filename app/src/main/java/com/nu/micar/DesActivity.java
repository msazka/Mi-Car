package com.nu.micar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesActivity extends ParentActivity {

    public static Dialog dialog;
    public static Animation ballAnim;
    RequestQueue requestQueue;
    List<RegisterCarModel> registerCarModelsList = new ArrayList<RegisterCarModel>();
    EditText input_regno, input_model, input_model_year, input_manufacturer, input_color, input_registration_city, input_engine_capacity, input_engine_type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_des);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) findViewById(R.id.text_view);

        setSupportActionBar(toolbar);
        if (getIntent() != null) {
            textView.setText(getIntent().getStringExtra("string"));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(DesActivity.this);

        input_regno = (EditText) findViewById(R.id.input_regno);
        input_model = (EditText) findViewById(R.id.input_model);
        input_model_year = (EditText) findViewById(R.id.input_model_year);
        input_manufacturer = (EditText) findViewById(R.id.input_manufacturer);
        input_color = (EditText) findViewById(R.id.input_color);
        input_registration_city = (EditText) findViewById(R.id.input_registration_city);
        input_engine_capacity = (EditText) findViewById(R.id.input_engine_capacity);
        input_engine_type = (EditText) findViewById(R.id.input_engine_type);

        Button button = (Button) findViewById(R.id.btn_car_reg);


        button.setOnClickListener(new View.OnClickListener()

                                  {
                                      public void onClick(View v) {

                                          hideSoftKeyboard(v);
                                          showLoading(DesActivity.this);

                                          String url = getResources().getString(R.string.webservicesurl);
                                          StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                                  new Response.Listener<String>() {
                                                      @Override
                                                      public void onResponse(String response) {
                                                          // response

                                                          hideLoading(DesActivity.this);


                                                          try {
                                                              JSONObject parent = new JSONObject(response);
                                                              int successStatus = parent.getInt("success");
                                                              String successMessage = parent.getString("message");

                                                              if (successStatus == 0) {

                                                                  showToast(successMessage);
                                                              } else {

                                                                  JSONObject dataObject = new JSONObject(parent.getString("data"));
                                                                  JSONObject profileObject = new JSONObject(dataObject.getString("profile"));

                                                                  RegisterCarModel carregmodel = new RegisterCarModel();


                                                                  carregmodel.setDevice_id(profileObject.getString("device_id"));
                                                                  carregmodel.setUser_id(profileObject.getString("user_id"));
                                                                  carregmodel.setRegistration_no(profileObject.getString("registration_no"));
                                                                  carregmodel.setRegistration_city(profileObject.getString("registration_city"));
                                                                  carregmodel.setModel(profileObject.getString("model"));
                                                                  carregmodel.setModel_year(profileObject.getString("model_year"));
                                                                  carregmodel.setManufacturer(profileObject.getString("manufacturer"));
                                                                  carregmodel.setEngine_capacity(profileObject.getString("engine_capacity"));
                                                                  carregmodel.setEngine_type(profileObject.getString("engine_type"));
                                                                  carregmodel.setColor(profileObject.getString("color"));
                                                                  carregmodel.setDate_created(profileObject.getString("date_created"));
                                                                  carregmodel.setDate_modified(profileObject.getString("date_modified"));


                                                                     registerCarModelsList.add(carregmodel);
                                                                  //    mAdapter.notifyDataSetChanged();


                                                                  showToast(successMessage);

                                                                  sharedPrefData("IsLogin", "true");

                                                                  Intent schoolActivityIntent = new Intent(DesActivity.this, MainActivity.class);
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

                                                          hideLoading(DesActivity.this);
                                                          // error
                                                          //    Log.d("Error.Response", response );
                                                      }
                                                  }
                                          ) {


                                              @Override
                                              protected Map<String, String> getParams() {
                                                  Map<String, String> params = new HashMap<String, String>();
                                                  params.put("method", "carRegistration");
                                                  params.put("session_token", getSharedPrefData("session_token"));
                                                  params.put("registration_no", input_regno.getText().toString().trim());

                                                  params.put("model", input_model.getText().toString().trim());
                                                  params.put("model_year", input_model_year.getText().toString().trim());
                                                  params.put("manufacturer", input_manufacturer.getText().toString().trim());
                                                  params.put("color", input_color.getText().toString().trim());
                                                  params.put("registration_city", input_registration_city.getText().toString().trim());
                                                  params.put("engine_capacity", input_engine_capacity.getText().toString().trim());
                                                  params.put("device_id", getSharedPrefData("session_token"));

                                                  return params;
                                              }
                                          };

                                          RequestQueue requestQueue = Volley.newRequestQueue(DesActivity.this);
                                          requestQueue.add(postRequest);


                                      }
                                  }
        );


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
