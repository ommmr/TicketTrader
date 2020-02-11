package com.example.tickettrader;

public class Message {
    private String text;
    private String sender;
    private int sent;

    public Message(String text, int sent){
        this.sent = sent;
        this.text = text;
    }

    public Message(String text, int sent, String sender){
        this.sent = sent;
        this.text = text;
        this.sender = sender;
    }

    public String getText() {
        return this.text;
    }

    public int getSent() {
        return this.sent;
    }

    public String getSender() {
        return this.sender;
    }
}
