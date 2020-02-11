package io.pp1.messages;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Message {

	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer message_id;
	private String sender;
	private String receiver;
	private String message;
	private String ticket_id;
	
//	private Integer user_1_id;
//	private Date localTime;
//	private Integer user_2_id;


	public Message(@NotNull Integer message_id, String sender,String receiver, String message, String ticket_id) {
		super();
		this.message_id = message_id;
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.ticket_id = ticket_id;
//		this.net_id = net_id;
//		this.user_2_id = user_2_id;
//		this.ticket_id = ticket_id;
	}

	public Message() {

	}

	public String getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}

	public Integer getMessage_id() {
		return message_id;
	}

	public void setMessage_id(Integer message_id) {
		this.message_id = message_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getReceiver() {
		return receiver;
	}


	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

//	public String getNet_id() {
//		return net_id;
//	}
//
//	public void setNet_id(String net_id) {
//		this.net_id = net_id;
//	}

//	public Integer getUser_1_id() {
//	return user_1_id;
//}
//
//public void setUser_1_id(Integer user_1_id) {
//	this.user_1_id = user_1_id;
//}
//	public Integer getUser_2_id() {
//	return user_2_id;
//}
//public void setUser_2_id(Integer user_2_id) {
//	this.user_2_id = user_2_id;
//}
	// public void setLocalTime(Date localTime) {
//		this.localTime = localTime;
//	}
//
//	public Date getLocalTime() {
//		return localTime;
//	}
//	

}
