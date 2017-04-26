package com.nu.micar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiCarTrackFragment extends Fragment {

    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar progressBar;
    List<RegisterCarModel> registerCarModelsList = new ArrayList<RegisterCarModel>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cars_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        registerCarModelsList.clear();

        carListbyUserId(view);

    }

    private void carListbyUserId(View view){

        //hideSoftKeyboard(view);
        //showLoading(DesActivity.this);
        progressBar.setVisibility(View.VISIBLE);

        String url = getResources().getString(R.string.webservicesurl);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        progressBar.setVisibility(View.GONE);


                        try {
                            JSONObject parent = new JSONObject(response);
                            int successStatus = parent.getInt("success");
                            //String successMessage = parent.getString("message");

                            if (successStatus == 0) {

                                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG);
                            } else {

                                JSONArray carsArray = new JSONArray(parent.getString("cars_found"));


                                for(int i=0; i<=carsArray.length()-1; i++){

                                    JSONObject carsOnject = carsArray.getJSONObject(i);
                                    RegisterCarModel carregmodel = new RegisterCarModel();


                                    carregmodel.setDevice_id(carsOnject.getString("device_id"));
                                    carregmodel.setUser_id(carsOnject.getString("user_id"));
                                    carregmodel.setRegistration_no(carsOnject.getString("registration_no"));
                                    carregmodel.setRegistration_city(carsOnject.getString("registration_city"));
                                    carregmodel.setModel(carsOnject.getString("model"));
                                    carregmodel.setModel_year(carsOnject.getString("model_year"));
                                    carregmodel.setManufacturer(carsOnject.getString("manufacturer"));
                                    carregmodel.setEngine_capacity(carsOnject.getString("engine_capacity"));
                                    carregmodel.setEngine_type(carsOnject.getString("engine_type"));
                                    carregmodel.setColor(carsOnject.getString("color"));
                                    carregmodel.setDate_created(carsOnject.getString("date_created"));
                                    carregmodel.setDate_modified(carsOnject.getString("date_modified"));


                                    registerCarModelsList.add(carregmodel);

                                }

                                mAdapter.notifyDataSetChanged();

                                /*JSONObject dataObject = new JSONObject(parent.getString("data"));
                                JSONObject profileObject = new JSONObject(dataObject.getString("profile"));*/


                                //    mAdapter.notifyDataSetChanged();


                                // Toast.makeText(getActivity(),successMessage,Toast.LENGTH_LONG);

                                //sharedPrefData("IsLogin", "true");

                                                                  /*Intent schoolActivityIntent = new Intent(DesActivity.this, MainActivity.class);
                                                                  startActivity(schoolActivityIntent);
                                                                  finish();*/

                            }

                        } catch (Exception e) {

                            e.printStackTrace();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.GONE);
                        // error
                        //    Log.d("Error.Response", response );
                    }
                }
        ) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("method", "getCarsByUserId");
                params.put("session_token", getSharedPrefData("session_token"));
                params.put("page_no", "1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(postRequest);

        mAdapter = new MiCarTrackAdapter(getActivity(), registerCarModelsList);
        mRecyclerView.setAdapter(mAdapter);

    }


    /**
     * ******************************** getSharedPrefData ******************************************
     */
    public String getSharedPrefData(String tag) {
        String res = "";
        try {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(getActivity());
            res = prefs.getString(tag, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

}
