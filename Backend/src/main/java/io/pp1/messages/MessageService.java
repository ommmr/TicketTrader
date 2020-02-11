package io.pp1.messages;

import java.util.List;

public class MessageService {
	
	
	private List<Message> message;
	
	
	public MessageService(List<Message> message) {
		this.message = message;
		
	}


	public List<Message> getMessage() {
		return message;
	}
	
	



}
