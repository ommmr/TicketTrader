package com.example.tickettrader;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {

    private Button Back;
    private Button Register;
    private Button Requirements;
    private EditText FirstName;
    private EditText LastName;
    private EditText netID;
    private EditText password;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        //Initializes all of the buttons
        Requirements = (Button) findViewById(R.id.btn_PasswordReqs);
        Back = (Button) findViewById(R.id.btnBack);
        Register = (Button) findViewById(R.id.btnRegisterLogin);
        FirstName = (EditText) findViewById(R.id.first_name);
        LastName = (EditText) findViewById(R.id.last_name);
        netID = (EditText) findViewById(R.id.net_id);
        password = (EditText) findViewById(R.id.password);


        //Setups the Requests for Volley
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        Requirements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(Registration.this).create();
                alertDialog.setTitle("Password Requirements");
                alertDialog.setMessage("8 Characters\nAt least: 1 Uppercase & 1 Special Character");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });


        //Executes actions after Back is clicked
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Login = new Intent(Registration.this, Login.class);
                startActivity(Login);

            }
        });

        //Executes Actions after Register is clicked
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(FirstName.getText().toString(), LastName.getText().toString(), netID.getText().toString(), password.getText().toString());
            }
        });
    }


    public void register(final String FirstName, final String LastName, final String netID, final String password) {

        String url = "http://cs309-pp-1.misc.iastate.edu:8080/users";
        JSONObject jsonObject = new JSONObject();


        //Creates a JSON to get ready to POST
        try {
            jsonObject.put("first_name", FirstName);
            jsonObject.put("last_name", LastName);
            jsonObject.put("net_Id", netID.toLowerCase());
            jsonObject.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Valid
        //invalid1: needs to be a isu net ID
        //invalid2: email already exists
        //invalid3: password needs to be 8 characters and needs to contain at least 1 number and 1 letter


        //POSTS the JSON, the add it to the Request Queue
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Intent intent = new Intent(Registration.this, Login.class);

                    JSONObject auth = response;
                    String responseCode = auth.getString("response");

                    if (responseCode.equals("Valid")) {
                        Toast.makeText(Registration.this, "Registered", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                    if (responseCode.equals("invalid1")) {
                        Toast.makeText(Registration.this, "Must be ISU net-id!", Toast.LENGTH_LONG).show();
                    }
                    if (responseCode.equals("invalid2")) {
                        Toast.makeText(Registration.this, "Email already exists!", Toast.LENGTH_LONG).show();
                    }
                    if (responseCode.equals("invalid3")) {
                        Toast.makeText(Registration.this, "Password does not meet requirements!", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}