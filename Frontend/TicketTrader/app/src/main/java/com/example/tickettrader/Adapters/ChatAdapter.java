package com.example.tickettrader.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tickettrader.Message;
import com.example.tickettrader.R;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private List<Message> messages = new ArrayList<>();
    private Context context;

    public ChatAdapter(Context ct){
        this.context = ct;
    }

    public void add(Message message){
        this.messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.messages.size();
    }

    @Override
    public Object getItem(int i) {
        return this.messages.get(i).getText();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = this.messages.get(i);

        if(message.getSent() == 1){
            view = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) view.findViewById(R.id.message_text);
            view.setTag(holder);
            holder.messageBody.setText(message.getText());
        } else {
            view = messageInflater.inflate(R.layout.their_message, null);
            holder.messageBody = (TextView) view.findViewById(R.id.message_text);
            holder.messageSender = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
            holder.messageBody.setText(message.getText());
            holder.messageSender.setText(message.getSender());
        }

        return view;
    }

    class MessageViewHolder {
        public TextView messageBody;
        public TextView messageSender;
    }
}
