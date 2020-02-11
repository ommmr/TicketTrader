package io.pp1.rating;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Rating {

	@Id
	@NotNull
	private Integer rating_id;
	private String net_id;
	private Integer rating;

	
	public Rating() {
	}

	
	
	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getRating_id() {
		return rating_id;
	}
	public void setRating_id(Integer rating_id) {
		this.rating_id = rating_id;
	}
	public String getNet_id() {
		return net_id;
	}
	public void setNet_id(String net_id) {
		this.net_id = net_id;
	}


}
