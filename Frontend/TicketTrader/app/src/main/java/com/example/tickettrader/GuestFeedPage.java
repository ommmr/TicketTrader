package com.example.tickettrader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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

public class GuestFeedPage extends AppCompatActivity {
    private ImageButton back;
    private RecyclerView mFeed;
    private feedAdapter mAdapter;
    private ImageButton refresh;
    RequestQueue requestQueue;
    String url;
    List<feed> feedData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_feed_page);

        this.mFeed = findViewById(R.id.guest_ticket_list);
        this.back = findViewById(R.id.guest_back);
        this.refresh = findViewById(R.id.guest_refresh);
        this.requestQueue = Volley.newRequestQueue(this);
        this.url = "http://cs309-pp-1.misc.iastate.edu:8080/tickets";

        mAdapter = new feedAdapter(GuestFeedPage.this, feedData);
        mFeed.setAdapter(mAdapter);

        refresh(this.url);

        this.refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh(url);
                Toast.makeText(GuestFeedPage.this, "Refreshed!", Toast.LENGTH_LONG).show();
            }
        });

        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void refresh(String url) {

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

                            feedData.add(Feed);
                        }

                        mAdapter = new feedAdapter(GuestFeedPage.this, feedData);
                        mFeed.setAdapter(mAdapter);
                        mFeed.setLayoutManager(new LinearLayoutManager(GuestFeedPage.this));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(GuestFeedPage.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
                }
            });

            requestQueue.add(request);
        }
    }
}
