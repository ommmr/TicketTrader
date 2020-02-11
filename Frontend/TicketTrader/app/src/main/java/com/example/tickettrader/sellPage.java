package com.example.tickettrader;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public class sellPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private String url;
    private Spinner sport, opponent;
    private EditText date;
    private EditText time;
    private EditText price;
    private String  net_id;
    private Button sell;
    RequestQueue requestQueue;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_page);


        opponent = (Spinner) findViewById(R.id.opponent_team);
        sport = (Spinner) findViewById(R.id.game_sport);
        date = (EditText) findViewById(R.id.game_date);
        time = (EditText) findViewById(R.id.game_time);
        price = (EditText) findViewById(R.id.game_price);
        sell = (Button) findViewById(R.id.sell_btn);
        url = "http://cs309-pp-1.misc.iastate.edu:8080/tickets";


        dbHelper = new DatabaseHelper(this);
        Cursor data = dbHelper.getData();
        data.moveToNext();
        data.moveToNext();
        data.moveToNext();
        net_id = data.getString(1);

        ArrayAdapter sportAdapter = ArrayAdapter.createFromResource(this, R.array.sport_array_sell, android.R.layout.simple_list_item_1);
        sportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sport.setAdapter(sportAdapter);



        final ArrayAdapter opponentAdapter = ArrayAdapter.createFromResource(this, R.array.opponent_array_sell,android.R.layout.simple_list_item_1);
        opponentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opponent.setAdapter(opponentAdapter);


        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        /*
        Sets up the sidebar.
         */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sell A Ticket");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (opponent.getSelectedItem().toString().equals("Select One") || sport.getSelectedItem().toString().equals("Select One")) {
                    Toast.makeText(sellPage.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
                } else {


                    new AlertDialog.Builder(sellPage.this)
                            .setTitle("Sell Ticket")
                            .setMessage("Are all the details correct?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int priceNum = Integer.parseInt(price.getText().toString());
                                    sellTicket(opponent.getSelectedItem().toString(),
                                            sport.getSelectedItem().toString(),
                                            date.getText().toString(),
                                            time.getText().toString(), priceNum);
                                    Intent intent = new Intent(sellPage.this, com.example.tickettrader.feedPage.class);
                                    startActivity(intent);
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();


                }
            }
        });
    }


    private void sellTicket(String opponent, String sport, String date, String time, int price) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("opponent", opponent);
            jsonObject.put("sport", sport);
            jsonObject.put("game_date", date);
            jsonObject.put("game_time", time);
            jsonObject.put("price", price);
            jsonObject.put("game_location", "Ames");
            jsonObject.put("net_id", net_id);
            jsonObject.put("ticket_id", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, null, null);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.feed) {
            Intent feed = new Intent(sellPage.this, feedPage.class);
            startActivity(feed);
        } else if (id == R.id.sell) {

        } else if (id == R.id.userAccount) {
            Intent user = new Intent(sellPage.this, UserAccountPage.class);
            startActivity(user);
        } else if (id == R.id.logout) {
            Intent login = new Intent(sellPage.this, Login.class);
            startActivity(login);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}