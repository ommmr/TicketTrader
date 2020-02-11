package io.pp1.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

	
	@Autowired 
	private NotificationRepository noteRepository;
	
	
	
}
