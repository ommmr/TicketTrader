package com.example.tickettrader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class UserAccountPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView user_pic;
    private TextView name;
    private TextView netID;
    private TextView userID;
    private TextView starRating;
    private Button myTickets, ticketsBought;
    DatabaseHelper dbHelper;
    String net,first,last,user, rating, url;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_page);
        url = "http://cs309-pp-1.misc.iastate.edu:8080/users";

        myTickets = (Button) findViewById(R.id.btnMyTickets);
        ticketsBought = (Button) findViewById(R.id.btnTicketsBought_profile);
        user_pic = (ImageView) findViewById(R.id.user_pic);
        name = (TextView) findViewById(R.id.name);
        netID = (TextView) findViewById(R.id.net_id);
        userID = (TextView) findViewById(R.id.user_id);
//        starRating = (TextView) findViewById(R.id.tvStarRating_profile);
        dbHelper = new DatabaseHelper(this);

        Cursor data = dbHelper.getData();

        //Used for Volley
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

//        getUserRating(url);




        if (data.getCount() < 0) {
            name.setText("Name");
            netID.setText("Net ID");
            userID.setText("User ID");
        } else {
            data.moveToNext();
            first = data.getString(1);
            data.moveToNext();
            last = data.getString(1);
            data.moveToNext();
            name.setText("Name: " + first + " " + last);

            net = data.getString(1);
            data.moveToNext();
            netID.setText("Net ID: " + net);

            user = data.getString(1);
            userID.setText("User ID: " + user);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Your Account");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        myTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.tickettrader.UserAccountPage.this,feedPage.class);
                intent.putExtra("net_id",net);
                intent.putExtra("type","seller");
                startActivity(intent);

            }
        });

        ticketsBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.tickettrader.UserAccountPage.this,feedPage.class);
                intent.putExtra("net_id",net);
                intent.putExtra("type","buyer");
                startActivity(intent);

            }
        });
    }

//    void getUserRating(String url){
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url , null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//
//                    JSONObject auth = response;
//                    rating = auth.getString("rating");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//
//            }
//        });
//
//        starRating.setText(rating);
//        requestQueue.add(request);
//
//
//
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.feed) {
            Intent feed = new Intent(UserAccountPage.this, feedPage.class);
            startActivity(feed);
        } else if (id == R.id.sell) {
            Intent sell = new Intent(UserAccountPage.this, sellPage.class);
            startActivity(sell);
        } else if (id == R.id.userAccount) {

        } else if (id == R.id.logout) {
            Intent login = new Intent(UserAccountPage.this, Login.class);
            startActivity(login);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}