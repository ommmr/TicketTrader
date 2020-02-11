package com.example.tickettrader.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.tickettrader.R;
import com.example.tickettrader.contact;

import java.util.List;

public class ChatPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<contact> data;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    // create constructor to innitilize context and data sent from MainActivity
    public ChatPageAdapter(Context context, List<contact> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.contact_card, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        contact current = data.get(position);
        myHolder.contactName.setText("Name: " + current.getName());
        myHolder.contactTicketId.setText("Ticket ID: " + current.getTicketId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView contactName;
        TextView contactTicketId;
        private AdapterView.OnItemClickListener itemClickListener;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            this.contactName = itemView.findViewById(R.id.contact_name);
            this.contactTicketId = itemView.findViewById(R.id.contact_ticketId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        mListener.onItemClick(position);
                    }
                }
            });
        }
    }
}