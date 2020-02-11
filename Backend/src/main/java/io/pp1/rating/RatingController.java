package io.pp1.rating;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.pp1.users.User;
import io.pp1.users.UserRepository;

@RestController
public class RatingController {


		@Autowired
		private RatingRepository ratingRepository;
		@Autowired
		private UserRepository userRepository;
		
//		@RequestMapping(method = RequestMethod.POST, path = "/rating/net_id")
//		public void  getRating(@RequestBody final Rating net_id) {
//			List<Rating> ratings =ratingRepository.getRatingByID(net_id.getNet_id());
//			int totalRatings=0;
//			for(int i=0;i<ratings.size();i++) {
//				totalRatings+= ratings.get(i).getRating();
//			}
//			double checking= Math.round(totalRatings/(ratings.size()+0.0));
//			totalRatings= (int)checking;
//			User userRating=userRepository.existByNetID(net_id.getNet_id());
//			userRating.setRating(totalRatings);
//			userRepository.save(userRating);
//			
//		}
		
//		@RequestMapping(method = RequestMethod.POST, path = "/rating")
//		public void saveRating(@RequestBody final Rating rating) {
//			 ratingRepository.save(rating);
//				List<Rating> ratings =ratingRepository.getRatingByID(rating.getNet_id());
//				int totalRatings=0;
//				for(int i=0;i<ratings.size();i++) {
//					totalRatings+= ratings.get(i).getRating();
//				}
//				double checking= Math.round(totalRatings/(ratings.size()+0.0));
//				totalRatings= (int)checking;
//				User userRating=userRepository.existByNetID(rating.getNet_id());
//				userRating.setRating(totalRatings);
//				userRepository.save(userRating);
//		}
		
		
}
