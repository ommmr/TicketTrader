package com.example.tickettrader;

public class contact {
    private String name;
    private String ticketId;

    public contact(String name, String ticketId) {
        this.name = name;
        this.ticketId = ticketId;
    }

    public String getName() {
        return this.name;
    }

    public String getTicketId() {
        return this.ticketId;
    }
}
