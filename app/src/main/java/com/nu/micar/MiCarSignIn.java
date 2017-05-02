package com.nu.micar;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.nu.micar.R.id.SignIn;

public class MiCarSignIn extends ParentActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "SIGNINACTIVITY==";
    public static Dialog dialog;
    public static Animation ballAnim;
    RequestQueue requestQueue;
    EditText input_email, input_password;
    private static final int RC_SIGN_IN = 9001;

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListner;
    private GoogleApiClient mGoogleApiClient;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micar_sign_in);

        requestQueue = Volley.newRequestQueue(MiCarSignIn.this);

        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        //Button button = (Button) findViewById(R.id.LoginViaFb);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mFirebaseAuth = FirebaseAuth.getInstance();


        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.LoginViaFb);
        TextView link_forgotpassword = (TextView) findViewById(R.id.link_forgotpassword);

        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //goMainScreen();
                }
            }
        };
    /*button.setOnClickListener(new View.OnClickListener()

                                  {
                                      public void onClick(View v) {
                                          Intent intent = new Intent(MiCarSignIn.this, MiCarLoginViaFB.class);
                                          startActivity(intent);
                                          // Perform action on click
                                      }
                                  }
        );*/

        SignInButton gbutton = (SignInButton) findViewById(R.id.sign_in_button);
        gbutton.setOnClickListener(new View.OnClickListener() {
                                       public void onClick(View v) {
                                           /*Log.d("activity_micar_test", "suceswwwwss");
                                           Intent intent = new Intent(MiCarSignIn.this, MiCarSignInGoogle.class);
                                           startActivity(intent);*/

                                           Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                                           startActivityForResult(signInIntent, RC_SIGN_IN);
                                           // Perform action on click
                                       }
                                   }
        );


        link_forgotpassword.setOnClickListener(new View.OnClickListener()

                                               {
                                                   public void onClick(View v) {
                                                       forgetpassdialog();
                                                   }
                                               }
        );


    }

    private void handleFirebaseAuthResult(AuthResult authResult) {
        if (authResult != null) {
            // Welcome the user
            FirebaseUser user = authResult.getUser();
          //  Toast.makeText(this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
            // Go back to the main activity
          //  startActivity(new Intent(this, MainActivity.class));
        }
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                Log.e(TAG, "Google Sign In failed.");
            }
        }
    }*/

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MiCarSignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            String uid = task.getResult().getUser().getUid();
                            String name = task.getResult().getUser().getDisplayName();
                            String email = task.getResult().getUser().getEmail();

                            loginFbGoogleApicall(email, uid, name , "GOOGLE_PLUS");
                        }
                    }
                });
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }



    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.firebase_error_login, Toast.LENGTH_LONG).show();
                } else {

                    String uid = task.getResult().getUser().getUid();
                    String name = task.getResult().getUser().getDisplayName();
                    String email = task.getResult().getUser().getEmail();
                    // String image=task.getResult().getUser().getPhotoUrl().toString();


                    loginFbGoogleApicall(email, uid, name , "FACEBOOK");


                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed
                Log.e(TAG, "Google Sign In failed.");
            }
        }

        else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListner);
    }

    /*public void goMainScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }*/


    private void loginFbGoogleApicall(final String email, final String password, final String username , final String source) {

        // Google Volley Post Request


        //  hideSoftKeyboard(MiCarSignUp.this);
        showLoading(MiCarSignIn.this);

        String url = getResources().getString(R.string.webservicesurl);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        hideLoading(MiCarSignIn.this);


                        try {
                            JSONObject parent = new JSONObject(response);
                            int successStatus = parent.getInt("success");
                            String successMessage = parent.getString("message");

                            if (successStatus == 0) {

                                showToast(successMessage);
                            } else {

                                JSONObject dataObject = new JSONObject(parent.getString("data"));
                                JSONObject profileObject = new JSONObject(dataObject.getString("profile"));

                                sharedPrefData("username", profileObject.getString("username"));
                                sharedPrefData("email", profileObject.getString("email"));
                                sharedPrefData("password", profileObject.getString("password"));
                                sharedPrefData("account_type", profileObject.getString("account_type"));
                                sharedPrefData("session_token", profileObject.getString("session_token"));

                                showToast(successMessage);

                                sharedPrefData("IsLogin", "true");

                                Intent schoolActivityIntent = new Intent(MiCarSignIn.this, MainActivity.class);
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

                        hideLoading(MiCarSignIn.this);
                        // error
                        //    Log.d("Error.Response", response );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("method", "userSignUp");
                params.put("email", email);
                params.put("password", password);
                params.put("username", username);
                params.put("account_type", source);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MiCarSignIn.this);
        requestQueue.add(postRequest);

    }

    public void loginUser(View view) {
        // Google Volley Post Request


        hideSoftKeyboard(view);
        showLoading(MiCarSignIn.this);

        String url = getResources().getString(R.string.webservicesurl);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response

                        hideLoading(MiCarSignIn.this);


                        try {
                            JSONObject parent = new JSONObject(response);
                            int successStatus = parent.getInt("success");
                            String successMessage = parent.getString("message");

                            if (successStatus == 0) {

                                showToast(successMessage);
                            } else {

                                JSONObject dataObject = new JSONObject(parent.getString("data"));
                                JSONObject profileObject = new JSONObject(dataObject.getString("profile"));

                                sharedPrefData("username", profileObject.getString("username"));
                                sharedPrefData("email", profileObject.getString("email"));
                                sharedPrefData("password", profileObject.getString("password"));
                                sharedPrefData("account_type", profileObject.getString("account_type"));
                                sharedPrefData("session_token", profileObject.getString("session_token"));

                                showToast(successMessage);


                                /*Intent schoolActivityIntent = new Intent(MiCarSignIn.this, DemoMapsActivity.class);
                                startActivity(schoolActivityIntent);
                                finish();*/

                                sharedPrefData("IsLogin", "true");

                                Intent schoolActivityIntent = new Intent(MiCarSignIn.this, MainActivity.class);
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

                        hideLoading(MiCarSignIn.this);
                        // error
                        //    Log.d("Error.Response", response );
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("method", "userLogin");
                params.put("email", input_email.getText().toString().trim());
                params.put("password", input_password.getText().toString().trim());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MiCarSignIn.this);
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

    public void forgetpassdialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_forgetpass, null);
        dialogBuilder.setView(dialogView);

        final EditText et_tracker_id = (EditText) dialogView.findViewById(R.id.et_tracker_id);

        dialogBuilder.setTitle("Forget Password");
        dialogBuilder.setMessage("Enter your email address");

        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                hideSoftKeyboard(dialogView);
                showLoading(MiCarSignIn.this);

                String url = getResources().getString(R.string.webservicesurl);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // response

                                hideLoading(MiCarSignIn.this);


                                try {
                                    JSONObject parent = new JSONObject(response);
                                    int successStatus = parent.getInt("success");
                                    String successMessage = parent.getString("message");

                                    if (successStatus == 0) {

                                        showToast(successMessage);
                                    } else {

                                        showToast(successMessage);


                                    }

                                } catch (Exception e) {

                                    e.printStackTrace();

                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                hideLoading(MiCarSignIn.this);
                                // error
                                //    Log.d("Error.Response", response );
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("method", "forgotPassword");
                        params.put("email", et_tracker_id.getText().toString().trim());


                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(MiCarSignIn.this);
                requestQueue.add(postRequest);

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        final AlertDialog b = dialogBuilder.create();
        b.show();
    }

}
