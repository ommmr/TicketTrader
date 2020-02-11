package com.example.tickettrader;

public class feed {

    public String name;
    public String gameLocation;
    public int ticketID;
    public String gameDate;
    public String sport;
    public int sellerID;
    public String gameTime;
    public int price;
    public String opponent;
    public String logo;
    public String net_id;
    public boolean yourTicket;
    public boolean sold;
    public int rating;
    public boolean rated;


    public void setTicketiD(int tickID) {
        this.ticketID = tickID;
    }

    public void setLogo(String photoData) {
        this.logo = photoData;
    }

    public void setOpponent(String opponentData) {
        this.opponent = opponent;
    }

    public void setSport(String sportData) {
        this.sport = sportData;
    }

    public void setGameLocation(String gameLocationData) {
        this.gameLocation = gameLocationData;
    }

    public void setGame_Date(String dateData) {
        this.gameDate = dateData;
    }

    public void setGame_Time(String timeData) {
        this.gameTime = timeData;
    }

    public void setPrice(int priceData) {
        this.price = priceData;
    }

    public void setSellerID(int sellerIDData) {
        this.sellerID = sellerIDData;
    }

    public void setNet_id(String net_id) {
        this.net_id = net_id;
    }

    public void setYourTicket(boolean yourTicket) { this.yourTicket = yourTicket;}

    public void setSold(boolean sold) { this.sold = sold;}

    public void setRating(int rating) {this.rating = rating;}

    public void setRated(boolean rated) {this.rated = rated;}
    }




