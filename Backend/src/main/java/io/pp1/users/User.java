package io.pp1.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class User {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer user_id;

	private String password;
	private String first_name;
	private String last_name;
	private String net_id;
	private Integer rating;
	
	public User() {
		
		
	}
	
	public User(Integer user_id, String first_name, String password, String last_name, String net_id){
		super();
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
		this.net_id = net_id;
	}
	
	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getUser_Id() {
		return user_id;
	}
	public void setUser_Id(Integer id) {
		this.user_id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNet_Id() {
		return net_id;
	}

	public void setNet_Id(String netId) {
		this.net_id = netId;
	}

}
