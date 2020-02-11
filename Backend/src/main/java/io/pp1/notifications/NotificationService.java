package io.pp1.notifications;

import java.util.List;

import io.pp1.messages.Message;

public class NotificationService {

	
	
	private List<Notification> notification;
	
	
	public NotificationService(List<Notification> notification) {
		this.notification = notification;
		
	}


	public List<Notification> getNotification() {
		return notification;
	}
}
