package com.example.tickettrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends AppCompatActivity {

    private EditText userName;
    private EditText Password;
    private TextView guestFeed;
    private Button Login;
    private Button Register;
    private DatabaseHelper dbHelper;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Initializes all of the UI aspects
        userName = (EditText) findViewById(R.id.etUsernameLogin);
        Password = (EditText) findViewById(R.id.etPasswordLogin);
        guestFeed = (TextView) findViewById(R.id.guest_feed);
        Login = (Button) findViewById(R.id.btnLogin);
        Register = (Button) findViewById(R.id.btnRegisterLogin);
        dbHelper = new DatabaseHelper(this);

        //Used for Volley
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();

        //@FIXME
        //Need to change this when we figure out a brute force stopper.

        guestFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, GuestFeedPage.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginVerify(userName.getText().toString(), Password.getText().toString());
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.tickettrader.Login.this, Registration.class);
                startActivity(intent);
            }
        });
    }


    /*
    This method will post a JSON filled with the inputted username and password. The backend then checks to
    see if the password and username that the endUser entered was registered or not. If it is registered,
    the POST request will return true and enter the app. If it isn't, then it returns false.
    */
    void loginVerify(final String userName, final String userPassword) {

        String url = "http://cs309-pp-1.misc.iastate.edu:8080/users/login"; //Our Server

        //Makes a JSON using the inputted userName and password
        JSONObject loginInfo = new JSONObject();
        try {
            loginInfo.put("net_Id", userName.toLowerCase());
            loginInfo.put("password", userPassword);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, loginInfo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject auth = response;
                    String verify = auth.getString("password");

                    if (verify.equals("true")) {
                        dbHelper.removeAll();

                        dbHelper.addData("first_name", auth.getString("first_name"));
                        dbHelper.addData("last_name", auth.getString("last_name"));
                        dbHelper.addData("net_ID", auth.getString("net_Id"));
                        dbHelper.addData("user_ID", auth.getString("user_Id"));

                        Intent intent = new Intent(com.example.tickettrader.Login.this, feedPage.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(getApplicationContext(),
                                "Incorrect Login!",
                                Toast.LENGTH_LONG).show();

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

        requestQueue.add(request);
    }

}