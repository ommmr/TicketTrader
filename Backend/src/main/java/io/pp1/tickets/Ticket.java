package io.pp1.tickets;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Ticket {
	
	@Id
	@NotNull
	private Integer ticket_id;
	private Boolean sold;
	
	private String sport;
	private String game_location;
	private String game_date;
	private String game_time;
	private Integer price;
	private String net_id;
	private String opponent;
	private Integer rating;
	private String LogoURL;
	private String buyer;
	private Boolean rated;
//	private Integer userRating;



	public Ticket() {	
		sold=false;
		rated=false;
	}
	
//	public Ticket(Integer ticket_id, String sport, String game_location, String game_date,  Integer game_time,Integer price, Integer seller_id) {
//	
//	this.ticket_id = ticket_id;
//	this.seller_id = seller_id;
//	this.price = price;
//	this.game_date = game_date;
//	this.sport = sport;
//	this.game_location = game_location;
//	this.game_time = game_time;
//}



	public String getLogoURL() {
		return LogoURL;
	}

	public Boolean getRated() {
		return rated;
	}

	public void setRated(Boolean rated) {
		this.rated = rated;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}

	public void setLogoURL(String logoURL) {
		LogoURL = logoURL;
	}

	public String getNet_id() {
		return net_id;
	}

	public void setNet_id(String net_id) {
		this.net_id = net_id;
	}
	public Integer getTicket_id() {
		return ticket_id;
	}

	public Ticket(@NotNull Integer ticket_id, String sport, String game_location, String game_date, String game_time,
		Integer price, String net_id, String opponent) {
	super();
	this.ticket_id = ticket_id;
	this.sport = sport;
	this.game_location = game_location;
	this.game_date = game_date;
	this.game_time = game_time;
	this.price = price;
	this.net_id = net_id;
	this.opponent = opponent;
}

	public void setTicket_id(Integer ticket_id) {
		this.ticket_id = ticket_id;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getGame_location() {
		return game_location;
	}

	public void setGame_location(String game_location) {
		this.game_location = game_location;
	}

	public String getGame_date() {
		return game_date;
	}

	public void setGame_date(String game_date) {
		this.game_date = game_date;
	}

	public String getGame_time() {
		return game_time;
	}

	public void setGame_time(String game_time) {
		this.game_time = game_time;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getOpponent() {
		return opponent;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

//	public Integer getuserRating() {
//		return userRating;
//	}
//
//	public void setuserRating(Integer userRating) {
//		this.userRating = userRating;
//	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}	
	
}
