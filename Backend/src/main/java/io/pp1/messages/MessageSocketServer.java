package io.pp1.messages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import io.pp1.messages.MessageRepository;

//this is the url front end should call in order to initialize the session.
@ServerEndpoint(value = "/websocket/{seller}/{buyer}/{ticket}", configurator = CustomConfigurator.class)
@Component
public class MessageSocketServer {
	// ASLDKJASL:KDJASKL:DJA:LKSJDLK:SJD;lska
	// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();

	private final Logger logger = LoggerFactory.getLogger(MessageSocketServer.class);

	@Autowired
	private MessageRepository messageRepository;

	/**
	 * Takes in session, seller, buyer and ticket to use as specifiers for the
	 * opening sequence of actions of the socket connection. Sockets put in hashmaps
	 * and strings are parsed and sent between them/
	 * 
	 * @param session
	 * @param seller
	 * @param buyer
	 * @param ticket
	 * @throws IOException
	 */

	@OnOpen
	public void onOpen(Session session, @PathParam("seller") String seller, @PathParam("buyer") String buyer,
			@PathParam("ticket") String ticket) throws IOException {

		// logger just records what is happening
		logger.info("Entered into Open");
		// taking Integer and storing it
		Integer ticketInt = Integer.valueOf(ticket);
		List<Message> messageList;

		// developing string
		String message = "";

		// hashmap taking in buyer, and using session as key
		sessionUsernameMap.put(session, buyer+ticket);
		// this is taking in session, and using buy as id
		usernameSessionMap.put(buyer+ticket, session);
		// hashmap taking in buyer, and using session as key

		// mechanics
		if (buyer.equals(seller)) {
			if (messageRepository.getMessageByTicket_ID(ticket).size() != 0) {
				messageList = messageRepository.getMessageByTicket_ID(ticket);
				System.out.print("gets here");
				for (int i = 0; i < messageList.size(); i++) {
					Message toGoOver = messageList.get(i);
					if(message.isEmpty()) {
						message=toGoOver.getMessage();
					}
					else {
					message=message+"\n"+toGoOver.getMessage();
					//message = message +"\nMessages From :"+messageList.get(i).getReceiver()+"\n"+ toGoOver.getMessage() + "\n";
					}
				}
				//message = message + "\nEnter '#userName message' to send to user\n";
			} else {
				usernameSessionMap.get(buyer+ticket).getBasicRemote().sendText("No messages for this ticket.");
			}
		} else {
			if (messageRepository.getMessageBySBT(seller, buyer, ticketInt) != null) {
				Message toUse = messageRepository.getMessageBySBT(seller, buyer, ticketInt);
				message = toUse.getMessage();
			}
//			 else {
//				usernameSessionMap.get(buyer+ticket).getBasicRemote().sendText("Enter message to send to the Seller.\n");
//			}
		}
		usernameSessionMap.get(buyer+ticket).getBasicRemote().sendText(message);
	}

	/**
	 * This determines the sequence of code to be ran when a socket receives a
	 * message Logger will log the info, message is sent.
	 * 
	 * @param session
	 * @param message
	 * @param seller
	 * @param buyer
	 * @param ticket
	 * @throws IOException
	 */
	// what happens when socket receives a message
	@OnMessage
	public void onMessage(Session session, String message, @PathParam("seller") String seller,
			@PathParam("buyer") String buyer, @PathParam("ticket") String ticket) throws IOException {
		// Handle new messages
		logger.info("Entered into Message:: Got Message" + message);
		Integer ticketInt = Integer.valueOf(ticket);
		if (buyer.equals(seller) && message.charAt(0) == '#') {
			String destUsername = message.substring(1, message.indexOf(' '));
			message = message.substring(destUsername.length() + 2);
			System.out.print(destUsername + "    " + message);
			if (messageRepository.getMessageBySBT(seller, destUsername, ticketInt) != null) {
				if (usernameSessionMap.get(destUsername+ticket) != null) {
					usernameSessionMap.get(destUsername+ticket).getBasicRemote().sendText(seller + "\n" + message);
				} else {
					usernameSessionMap.get(seller+ticket).getBasicRemote().sendText("Message sent to user");
				}
				Message toUse = messageRepository.getMessageBySBT(seller, destUsername, ticketInt);
				message = toUse.getMessage() + "\n" + seller + "\n" + message;
				toUse.setMessage(message);
				messageRepository.save(toUse);
			} else {
				usernameSessionMap.get(seller+ticket).getBasicRemote().sendText("Wrong contact name, try again.");
			}
		} else {
			if (messageRepository.getMessageBySBT(seller, buyer, ticketInt) != null) {
				if (usernameSessionMap.get(seller+ticket) != null) {
					usernameSessionMap.get(seller+ticket).getBasicRemote().sendText(buyer + "\n" + message);
				}
				Message toUse = messageRepository.getMessageBySBT(seller, buyer, ticketInt);
				message = toUse.getMessage() + "\n" + buyer + "\n" + message;
				toUse.setMessage(message);
				messageRepository.save(toUse);
			} else {
				if (usernameSessionMap.get(seller+ticket) != null) {
					usernameSessionMap.get(seller+ticket).getBasicRemote().sendText(buyer + "\n" + message);
				}
				message = buyer + "\n" + message;
				Message newMessage = new Message(0, seller, buyer, message, ticket);
				messageRepository.save(newMessage);

			}
		}
	}

	/**
	 * Sequence of code when closing the socket. Session is removed from the hashmap
	 * 
	 * @param session
	 * @throws IOException
	 */

	// what happens when we close that session
	@OnClose
	public void onClose(Session session) throws IOException {
		logger.info("Entered into Close");
		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
	}

	/**
	 * Sequence of code during error.
	 * 
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
	}
}
