package com.nu.micar;

import android.*;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Marker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by waqar on 27-Apr-17.
 */


public class MiCarLocateCarActivity extends ParentActivity implements

        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    TextView tv_dis;
    String deslatstring, deslonstring;
    double destlat, destlon;
    String deviceid, regno, address;
    ProgressBar progressBar;
    Handler h = new Handler();
    //int delay = 120000; //2 mins
    int delay = 30000;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_car);

        deviceid = getIntent().getStringExtra("deviceid");
        regno = getIntent().getStringExtra("regno");

        /*if (servicesOK()) {
            setContentView(R.layout.activity_micar_tracking);
            SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFrag.getMapAsync(this);
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        tv_dis = (TextView) findViewById(R.id.tv_dis);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        carListbyUserId();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
              //  mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
          //  mMap.setMyLocationEnabled(true);
        }

    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();





    }

    @Override
    public void onBackPressed() {
        h.removeCallbacks(runnable);
        h.removeCallbacks(null);
        finish();
    }

    @Override
    protected void onDestroy() {
        h.removeCallbacks(runnable);
        h.removeCallbacks(null);//stop handler when activity not visible
        super.onDestroy();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        h.removeCallbacks(runnable);
        h.removeCallbacks(null);


        h.postDelayed(new Runnable() {
            public void run() {
                showdistance(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                runnable=this;

                h.postDelayed(runnable, delay);
            }
        }, delay);


        showdistance(location.getLatitude(), location.getLongitude());


        //Place current location marker
       /* LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));*/
        //  mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));

        //optionally, stop location updates if only current location is needed
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MiCarLocateCarActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        //mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void carListbyUserId() {

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

                                Toast.makeText(MiCarLocateCarActivity.this, "Something went wrong", Toast.LENGTH_LONG);
                            } else {

                                JSONArray trackrecordArray = new JSONArray(parent.getString("track_record"));

                                JSONObject trackcarobj = trackrecordArray.getJSONObject(0);

                                deslatstring = trackcarobj.getString("latitude");
                                deslonstring = trackcarobj.getString("longitude");
                                address = trackcarobj.getString("address");


                                destlat = Double.parseDouble(deslatstring);
                                destlon = Double.parseDouble(deslonstring);
                                showdistance(mLastLocation.getLatitude(),mLastLocation.getLongitude());


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
                params.put("method", "getCarLocation");
                params.put("session_token", getSharedPrefData("session_token"));
                params.put("device_id", deviceid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);

    }


    private void showdistance(double lat, double lon) {


        Double dis = calculateDistance(mLastLocation.getLatitude(), mLastLocation.getLongitude(), destlat, destlon);

        dis = dis * 1000;


        //tv_dis.setText("" +  Math.round(dis) * 1000 + " m");
        tv_dis.setText("" +  String.format ("%.2f", dis)  + " m");

    }

    public boolean servicesOK() {
        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to mapping service", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    /*private double meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
        double pk = (double) (180.f/Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = FloatMath.cos(a1)*FloatMath.cos(a2)*FloatMath.cos(b1)*FloatMath.cos(b2);
        double t2 = FloatMath.cos(a1)*FloatMath.sin(a2)*FloatMath.cos(b1)*FloatMath.sin(b2);
        double t3 = FloatMath.sin(a1)*FloatMath.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }*/

}