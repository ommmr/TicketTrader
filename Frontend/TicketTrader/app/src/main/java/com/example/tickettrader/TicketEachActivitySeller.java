package com.example.tickettrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class TicketEachActivitySeller extends AppCompatActivity {

    private static final String TAG = "TicketEachActivity";
    String sport;
    String awayLogo;
    String net_id;
    String date;
    int ticketId;
    int price;
    int userID;
    Button btn_message;
    Button btn_delete;
    Button btn_sold;
    String url = "http://cs309-pp-1.misc.iastate.edu:8080/tickets/delete"; //Our Server
    RequestQueue requestQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_each_seller);
        btn_message = findViewById(R.id.btnMessage_seller);
        btn_delete = findViewById(R.id.btnDelete_seller);
        getIncomingIntent();


        //Used for Volley
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);

        requestQueue.start();


        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketEachActivitySeller.this, Chat.class);
                String otherUser = getIntent().getStringExtra("net_id");
                intent.putExtra("other_user", otherUser);

                intent.putExtra("ticket_id", ticketId);
                startActivity(intent);
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ticketId = getIntent().getIntExtra("ticket_id", 0);

                JSONObject jsonOBJ = new JSONObject();
                try {
                    jsonOBJ.put("ticket_id",ticketId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                delete(jsonOBJ);
            }
        });


    }

    private void getIncomingIntent() {

        ticketId = getIntent().getIntExtra("ticket_id", 0);
        sport = getIntent().getStringExtra("sport");
        awayLogo = getIntent().getStringExtra("logoURL");
        price = getIntent().getIntExtra("price", -1);
        userID = getIntent().getIntExtra("userID", -1);
        net_id = getIntent().getStringExtra("net_id");
        date = getIntent().getStringExtra("gameDate");


        loadPage(awayLogo, price, sport, date, net_id);

    }

    private void loadPage(String awayLogo, int price, String sport, String date, String net_id) {
        ImageView away_logo = findViewById(R.id.awayLogo_seller);
        ImageView isu_logo = findViewById(R.id.isuLogo_seller);
        TextView tv_game_date = findViewById(R.id.game_date_ticket_seller);
        TextView tv_sport = findViewById(R.id.sportTV_seller);
        setPrice(price);
        TextView tv_net_id = findViewById(R.id.netID_tv_seller);

        tv_sport.setText(sport);
        tv_game_date.setText(date);

        Glide.with(this).load(awayLogo).into(away_logo);
        Glide.with(this).load("https://i.imgur.com/Mhi5WN9.png").into(isu_logo);
    }

    private void delete (JSONObject data) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        requestQueue.add(request);

        Intent intent = new Intent(TicketEachActivitySeller.this, feedPage.class);
        startActivity(intent);

        Toast.makeText(getApplicationContext(),
                "Deleted!",
                Toast.LENGTH_LONG).show();
    }


    private void setPrice(int price) {


        TextView tv_price = findViewById(R.id.priceTv_seller);
        tv_price.setText("$" + String.valueOf(price));
    }

}
