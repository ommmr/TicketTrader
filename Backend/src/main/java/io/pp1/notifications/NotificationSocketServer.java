package io.pp1.notifications;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.pp1.messages.CustomConfigurator;
import io.pp1.messages.MessageSocketServer;

@ServerEndpoint(value = "/webNote/{net_id}", configurator = CustomConfigurator.class)
@Component
public class NotificationSocketServer {

//	DONT NEED HASHMAP SINCE NOTIFICATION GOING TO ONE PORSEN	
//	// Store all socket session and their corresponding username.
//	private static Map<Session, String> sessionUsernameMap = new HashMap<>();
	private static Map<String, Session>	net_idSessionMap = new HashMap<>();

	private final Logger logger = LoggerFactory.getLogger(MessageSocketServer.class);
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	@OnOpen
	public void onOpen(Session session, @PathParam("net_id")String net_id) throws IOException
	{
		//record log info
		logger.info(net_id + " Has received notification: ");
		
		//store use seller_id to
		//store message that tickt is sold in varaible
		String message = "Your ticket has been sold!";
		net_idSessionMap.put(net_id, session);
		
		//send it
		net_idSessionMap.get(net_id).getBasicRemote().sendText(message);
	}
	
	@OnMessage
	public void OnMessage(Session session, @PathParam("net_id") String net_id, String message) throws IOException
	{
		
		logger.info("Entered into Message:: Got Message" + message);
		net_idSessionMap.get(net_id).getBasicRemote().sendText(message);
	}
	
	@OnClose
	public void onClose(Session session) throws IOException{
		
		logger.info("CLOSING ");
		
		net_idSessionMap.remove(session);
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		
		logger.info("entered into Error");
	}
}

