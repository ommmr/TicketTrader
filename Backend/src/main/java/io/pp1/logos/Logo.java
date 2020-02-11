package io.pp1.logos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class Logo {
	
	@Id
	@Column(name="img_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer img_id;
	private String img_name;
	private String url;
	
	public Logo() {
		
		
	}
	
	public Logo(String img_name, String url){
		super();
		this.img_name = img_name;
		this.url = url;
		
	}
	
	public Integer getImg_id() {
		
		return img_id;
	}
	
	public void setImg_id(Integer id) {
		img_id = id;
	}

	public String getImg_name() {
		return img_name;
	}

	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	

}
