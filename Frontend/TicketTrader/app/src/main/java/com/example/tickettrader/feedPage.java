package com.example.tickettrader;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.tickettrader.Adapters.feedAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class feedPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RequestQueue requestQueue;
    String url, netID, type;
    List<feed> feedData = new ArrayList<>();
    JSONObject filter = new JSONObject();
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RecyclerView mFeed;
    private feedAdapter mAdapter;
    private ImageButton bRefresh;
    private ImageButton bFilter;
    DatabaseHelper dbHelper;
    SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_page);
        requestQueue = Volley.newRequestQueue(this);
        mFeed = findViewById(R.id.ticketList);
        bRefresh = findViewById(R.id.refresh);
        bFilter = findViewById(R.id.filter);
        dbHelper = new DatabaseHelper(this);
        url = "http://cs309-pp-1.misc.iastate.edu:8080/tickets";
        type = "default";

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                updatePage();

            }

        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_red_light);


        Cursor data = dbHelper.getData();
        data.moveToNext();
        data.moveToNext();
        data.moveToNext();
        netID = data.getString(1);

        //Sets all the filter data to NULL
        try {
            filter.put("opponent", null);
            filter.put("game_date", null);
            filter.put("sport", null);
            filter.put("user_id",null);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Sets up the Side Drawer
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Feed");
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updatePage();


        bRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePage();//Refresh data test
                Toast.makeText(feedPage.this, "Refreshed!", Toast.LENGTH_LONG).show();
            }
        });

        bFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(feedPage.this, popup_filter.class);
                startActivityForResult(intent, 999);

            }
        });


    }


    // When the filter page is done. It will run filter with the requested data to filter found in the
    // mJSONObject.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            try {
                JSONObject mJSONObject = new JSONObject(data.getStringExtra("json"));
                filter("http://cs309-pp-1.misc.iastate.edu:8080/tickets/filter", mJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // This method takes the data from the database from the url. The data that is returned is a JSON
    // array. It is then parsed and facilitates the feedAdapter Adapter to set the different values
    // in each of the XML's and their cards.
    public void refresh(final String url) {

        feedData.clear();

        {
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("ticket");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json_data = jsonArray.getJSONObject(i);

                            feed Feed = new feed();
                            Feed.setSport(json_data.getString("sport"));
                            Feed.setOpponent(json_data.getString("opponent"));
                            Feed.setLogo(json_data.getString("logoURL"));
                            Feed.setSport(json_data.getString("sport"));
                            Feed.setGame_Date(json_data.getString("game_date"));
                            Feed.setPrice(json_data.getInt("price"));
                            Feed.setNet_id(json_data.getString("net_id"));
                            Feed.setTicketiD(json_data.getInt("ticket_id"));
                            Feed.setRating(json_data.getInt("rating"));


                            //This makes a check mark appear if the current logged in user is selling this ticket
                            if(netID.equals(json_data.getString("net_id"))){
                                Feed.setYourTicket(true);
                            }
                            else{
                                Feed.setYourTicket(false);
                            }


                            feedData.add(Feed);

                        }

                        mAdapter = new feedAdapter(feedPage.this, feedData);
                        mFeed.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new feedAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Cursor data = dbHelper.getData();
                                data.moveToNext();
                                data.moveToNext();
                                data.moveToNext();
                                String currentUser = data.getString(1);
                                String tmpSellerID = String.valueOf((feedData.get(position).net_id));
                                Intent intent = new Intent(feedPage.this, TicketEachActivity.class);

                                if(tmpSellerID.equalsIgnoreCase(currentUser)){
                                    intent = new Intent(feedPage.this, TicketEachActivitySeller.class);
                                }
                                int tmp_price = feedData.get(position).price;
                                int tmp_ticketID = feedData.get(position).ticketID;
                                int tmp_sellerID = feedData.get(position).sellerID;


                                intent.putExtra("price", tmp_price);
                                intent.putExtra("sport", feedData.get(position).sport);
                                intent.putExtra("gameTime", feedData.get(position).gameTime);
                                intent.putExtra("gameDate", feedData.get(position).gameDate);
                                intent.putExtra("gameLocation", feedData.get(position).gameLocation);
                                intent.putExtra("logoURL", feedData.get(position).logo);
                                intent.putExtra("sellerID", feedData.get(position).sellerID);
                                intent.putExtra("net_id", feedData.get(position).net_id);
                                intent.putExtra("ticket_id", feedData.get(position).ticketID);
                                intent.putExtra("ticketID", tmp_ticketID);
                                intent.putExtra("buyer",currentUser);
                                intent.putExtra("userRating", feedData.get(position).rating);

                                startActivity(intent);

                            }
                        });
                        mFeed.setLayoutManager(new LinearLayoutManager(feedPage.this));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(feedPage.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                }
            });

            requestQueue.add(request);
        }
    }

    //This method is used to filter the data based on the filter criteria found in the filter page.
    private void filter(String url, JSONObject filterData) {

        if(type.equals("buyer")||type.equals("seller")){
            bFilter.setVisibility(View.INVISIBLE);
        }

        feedData.clear();

        {
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, filterData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray = response.getJSONArray("ticket");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject json_data = jsonArray.getJSONObject(i);

                            feed Feed = new feed();
                            Feed.setSport(json_data.getString("sport"));
                            Feed.setLogo(json_data.getString("logoURL"));
                            Feed.setSport(json_data.getString("sport"));
                            Feed.setGame_Date(json_data.getString("game_date"));
                            Feed.setPrice(json_data.getInt("price"));
                            Feed.setNet_id(json_data.getString("net_id"));
                            Feed.setRating(json_data.getInt("rating"));
                            Feed.setTicketiD(json_data.getInt("ticket_id"));
                            Feed.setRated(json_data.getBoolean("rated"));

                            //This makes a check mark appear if the current logged in user is selling this ticket
                            if(netID.equals(json_data.getString("net_id"))){
                                Feed.setYourTicket(true);
                            }
                            else{
                                Feed.setYourTicket(false);
                            }
                            if(json_data.getBoolean("sold")==true){
                                Feed.setSold(true);
                            }

                            feedData.add(Feed);

                        }

                        mAdapter = new feedAdapter(feedPage.this, feedData);
                        mFeed.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new feedAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Cursor data = dbHelper.getData();
                                data.moveToNext();
                                data.moveToNext();
                                data.moveToNext();
                                String currentUser = data.getString(1);
                                if(type.equals("buyer")) {
                                    if(feedData.get(position).rated==false){
                                        Intent intent = new Intent(feedPage.this, ReviewPage.class);
                                        intent.putExtra("sellerID", feedData.get(position).net_id);
                                        intent.putExtra("ticket_id",feedData.get(position).ticketID);
                                        startActivity(intent);
                                    }

//                                    intent.putExtra("currentUser",currentUser);
//                                    int tmp_price = feedData.get(position).price;
//                                    int tmp_ticketID = feedData.get(position).ticketID;
//                                    int tmp_sellerID = feedData.get(position).sellerID;
//
//
//                                    intent.putExtra("price", tmp_price);
//                                    intent.putExtra("sport", feedData.get(position).sport);
//                                    intent.putExtra("gameTime", feedData.get(position).gameTime);
//                                    intent.putExtra("gameDate", feedData.get(position).gameDate);
//                                    intent.putExtra("gameLocation", feedData.get(position).gameLocation);
//                                    intent.putExtra("logoURL", feedData.get(position).logo);
//                                    intent.putExtra("ticketID", tmp_ticketID);


                                }
                                else{String tmpSellerID = String.valueOf((feedData.get(position).net_id));
                                    Intent intent = new Intent(feedPage.this, TicketEachActivity.class);
                                    if(tmpSellerID.equalsIgnoreCase(currentUser)){
                                        intent = new Intent(feedPage.this, TicketEachActivitySeller.class);
                                    }
                                    int tmp_price = feedData.get(position).price;
                                    int tmp_ticketID = feedData.get(position).ticketID;
                                    int tmp_sellerID = feedData.get(position).sellerID;


                                    intent.putExtra("price", tmp_price);
                                    intent.putExtra("sport", feedData.get(position).sport);
                                    intent.putExtra("gameTime", feedData.get(position).gameTime);
                                    intent.putExtra("gameDate", feedData.get(position).gameDate);
                                    intent.putExtra("gameLocation", feedData.get(position).gameLocation);
                                    intent.putExtra("logoURL", feedData.get(position).logo);
                                    intent.putExtra("sellerID", feedData.get(position).sellerID);
                                    intent.putExtra("ticketID", tmp_ticketID);
                                    intent.putExtra("userRating", feedData.get(position).rating);

                                    startActivity(intent);
                                }


                            }
                        });
                        mFeed.setLayoutManager(new LinearLayoutManager(feedPage.this));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(),
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(feedPage.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                }
            });

            requestQueue.add(request);
        }
//        Toast.makeText(feedPage.this, "Filtered!", Toast.LENGTH_LONG).show();
    }

    void updatePage(){

        if (getIntent()!=null && getIntent().getExtras()!=null){
            type = getIntent().getStringExtra("type");
            if(type.equals("seller")){
                try {
                    filter.put("net_id",getIntent().getStringExtra("net_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                filter("http://cs309-pp-1.misc.iastate.edu:8080/tickets/net_id",filter);
            }
            if(type.equals("buyer")){
                try {
                    filter.put("buyer",getIntent().getStringExtra("net_id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                filter("http://cs309-pp-1.misc.iastate.edu:8080/tickets/buyer",filter);
            }

        }
        else{
            refresh(url);
        }
        swipeContainer.setRefreshing(false);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
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
            Intent feed = new Intent(feedPage.this, feedPage.class);
            startActivity(feed);
        } else if (id == R.id.sell) {
            Intent sell = new Intent(feedPage.this, sellPage.class);
            startActivity(sell);
        } else if (id == R.id.userAccount) {
            Intent user = new Intent(this, UserAccountPage.class);
            startActivity(user);
        } else if (id == R.id.logout) {
            Intent login = new Intent(feedPage.this, Login.class);
            startActivity(login);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
