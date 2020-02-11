package io.pp1.users;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public List<User> findAll();

//	//Give the netid of someone, it will look it up in database and return password
	@Query(value = "SELECT password FROM user u WHERE u.net_id = ?1", nativeQuery=true)
	String getPassByNetID(String netid);
	
	@Query(value = "SELECT * FROM user u WHERE u.password = ?1", nativeQuery=true)
	User getUserByPass(String pass);
	
	@Query(value = "SELECT * FROM user u WHERE u.net_id= ?1", nativeQuery=true)
	User existByNetID(String netId);
	
	@Query(value = "SELECT * FROM user u WHERE u.net_id= ?1", nativeQuery=true)
	List<User> getUserByNetID(String netId);
	
}
