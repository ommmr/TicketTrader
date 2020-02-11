package com.example.tickettrader.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tickettrader.GuestFeedPage;
import com.example.tickettrader.R;
import com.example.tickettrader.feed;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tickettrader.feedPage;

// This is an adapter that helps manage the ticket data that is received from the server.
// the data is parsed as JSON. Within this adapter there is a ViewHolder that assigns all of
// the locations for the data and what to do with it when it is parsed.
//
public class feedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mListener;
    List<feed> data;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    // Create constructor to initialize context and data sent from MainActivity
    public feedAdapter(Context context, List<feed> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(this.context.getClass().equals(GuestFeedPage.class)) {
            view=inflater.inflate(R.layout.guest_feed_card, parent,false);
        } else {
            view=inflater.inflate(R.layout.feed_card, parent,false);
        }
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        feed current = data.get(position);
        myHolder.price.setText("$" + current.price);
        myHolder.gameDate.setText(current.gameDate);
        myHolder.sport.setText(current.sport);
        if(current.yourTicket==false && myHolder.checkmark != null){
            myHolder.checkmark.setVisibility(View.GONE);
        }
        if(current.yourTicket==true && myHolder.checkmark != null){
            myHolder.checkmark.setVisibility(View.VISIBLE);
            myHolder.review.setVisibility(View.INVISIBLE);
            if(current.sold==true){
                myHolder.review.setText("SOLD!");
                myHolder.review.setVisibility(View.VISIBLE);
            }
        }
        else if(current.sold==true && current.rated==false)
        {
            myHolder.review.setVisibility(View.VISIBLE);
            myHolder.review.setTypeface(null, Typeface.BOLD);
        }
        else if(current.sold==false && myHolder.checkmark != null)
        {
            myHolder.review.setVisibility(View.INVISIBLE);
        }
        else if(current.rated==true)
        {
            myHolder.review.setText("Thanks for your review!");
            myHolder.review.setTextColor(Color.BLUE);
        }
//        myHolder.gameTime.setText("Start time: " + current.gameTime);
        Glide.with(context).load(current.logo).into(myHolder.logo);
        Glide.with(context).load("https://i.imgur.com/Mhi5WN9.png").into(myHolder.ISU);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    //Sets up holder for iteration of the RecyclerView
    class MyHolder extends RecyclerView.ViewHolder {

        TextView name, username, email, price, gameDate, gameTime, opponent, sport, review;
        ImageView logo, ISU, checkmark;

        // Constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.price);
            gameDate = (TextView) itemView.findViewById(R.id.Game_Date);
            logo = (ImageView) itemView.findViewById(R.id.logoIV);
            sport = (TextView) itemView.findViewById(R.id.sport);
            ISU = (ImageView) itemView.findViewById(R.id.isuLogo);
            checkmark = (ImageView) itemView.findViewById(R.id.youTicket);
            review = (TextView) itemView.findViewById(R.id.tvRate_card);

            if(context.getClass().equals(feedPage.class)) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                        }
            });}


        }


    }

}
