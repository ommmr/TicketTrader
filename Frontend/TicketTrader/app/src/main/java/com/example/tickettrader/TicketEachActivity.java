package com.example.tickettrader;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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

public class TicketEachActivity extends AppCompatActivity {

    private static final String TAG = "TicketEachActivity";
    String sport;
    String awayLogo;
    String net_id;
    String date;
    String buyer;
    int ticketId;
    int price;
    int userID;
    int rating;
    Button btn_message, btn_sold;
    RequestQueue requestQueue;
    RatingBar starBar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_each);
        btn_message = findViewById(R.id.btnMessage);
        btn_sold = findViewById(R.id.btnSold);
        getIncomingIntent();

        //Used for Volley
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();


        btn_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketEachActivity.this, Chat.class);
                String otherUser = getIntent().getStringExtra("net_id");
                intent.putExtra("other_user", otherUser);
                intent.putExtra("ticket_id", ticketId);
                startActivity(intent);
            }
        });

        btn_sold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    buyTicket();
                Intent intent = new Intent(TicketEachActivity.this,feedPage.class);
                    startActivity(intent);
            }
        });


    }

    private void getIncomingIntent() {

        ticketId = getIntent().getIntExtra("ticketID", 0);
        sport = getIntent().getStringExtra("sport");
        awayLogo = getIntent().getStringExtra("logoURL");
        price = getIntent().getIntExtra("price", -1);
        userID = getIntent().getIntExtra("userID", -1);
        net_id = getIntent().getStringExtra("net_id");
        date = getIntent().getStringExtra("gameDate");
        buyer = getIntent().getStringExtra("buyer");
        rating = getIntent().getIntExtra("userRating",0);


        loadPage(awayLogo, price, sport, date, net_id);

    }

    private void loadPage(String awayLogo, int price, String sport, String date, String net_id) {
        ImageView away_logo = findViewById(R.id.awayLogo);
        ImageView isu_logo = findViewById(R.id.isuLogo);
        TextView tv_game_date = findViewById(R.id.game_date_ticket);
        TextView tv_sport = findViewById(R.id.sportTV);
        setPrice(price);
        TextView tv_net_id = findViewById(R.id.netID_tv);
        TextView starRating = findViewById(R.id.textStarRating);
        starBar = findViewById(R.id.starBar);


        starBar.setRating((float) rating);
//        starRating.setText(String.valueOf(rating));
        tv_sport.setText(sport);
        tv_game_date.setText(date);
        tv_net_id.setText("Seller: " + net_id);

        Glide.with(this).load(awayLogo).into(away_logo);
        Glide.with(this).load("https://i.imgur.com/Mhi5WN9.png").into(isu_logo);
    }


    private void setPrice(int price) {


        TextView tv_price = findViewById(R.id.priceTv);
        tv_price.setText("$" + String.valueOf(price));
    }

    private void buyTicket(){
        try {
            JSONObject jsonOBJ = new JSONObject();
            jsonOBJ.put("ticket_id",ticketId);
//            jsonOBJ.put("sold",true);
            jsonOBJ.put("buyer", buyer);
//            jsonOBJ.put("sport",sport);
//            jsonOBJ.put("price",price);
//            jsonOBJ.put("net_id",net_id);
//            jsonOBJ.put("game_date",date);

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,"http://cs309-pp-1.misc.iastate.edu:8080/tickets/sold", jsonOBJ, new Response.Listener<JSONObject>() {
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

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
