package com.example.tickettrader;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tickettrader.Adapters.feedAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RecyclerView mChat;
    private String url;
    private DatabaseHelper dbHelper;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        this.toolbar = findViewById(R.id.toolbar);
        this.toolbar.setTitle("Chat");

        this.drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawer.addDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);

        this.dbHelper = new DatabaseHelper(this);
        Cursor data = dbHelper.getData();
        data.moveToNext();
        data.moveToNext();
        data.moveToNext();
        this.user = data.getString(1);
        this.mChat = findViewById(R.id.messages);
        this.url = "http://cs309-pp-1.misc.iastate.edu:8080/message/people";
        this.getContactsList(this.url);
    }

    public void getContactsList(String url) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("net_id", this.user);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                try {
//
//                } catch(JSONException e) {
//                    e.printStackTrace();
//                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.feed) {
            Intent feed = new Intent(ChatPage.this, feedPage.class);
            startActivity(feed);
        } else if (id == R.id.sell) {
            Intent sell = new Intent(ChatPage.this, sellPage.class);
            startActivity(sell);
        } else if (id == R.id.userAccount) {
            Intent user = new Intent(ChatPage.this, UserAccountPage.class);
            startActivity(user);
        } else if (id == R.id.logout) {
            Intent login = new Intent(ChatPage.this, Login.class);
            startActivity(login);
        } else if (id == R.id.chat) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
