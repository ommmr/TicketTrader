package io.pp1.rating;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer>{

	
	@Query(value = "SELECT * FROM rating u WHERE u.net_id = ?1", nativeQuery=true)
	List<Rating> getRatingByID(String net_id);
	
}
