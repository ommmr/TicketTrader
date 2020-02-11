package io.pp1.rating;

import java.util.List;

public class RatingService {

	
	
	private List<Rating> rating;
	
	
	public RatingService(List<Rating> rating) {
		this.rating = rating;
		
	}


	public List<Rating> getRating() {
		return rating;
	}
	
	
	
	
}
