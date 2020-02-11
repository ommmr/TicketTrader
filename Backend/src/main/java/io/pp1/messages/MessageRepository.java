package io.pp1.messages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.pp1.tickets.Ticket;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	
	public List<Message> findAll();

	  @Query(value ="SELECT * FROM message u WHERE (u.sender = ?1 or u.receiver = ?1) and (u.sender = ?2 or u.receiver = ?2) and u.ticket_id = ?3", nativeQuery=true) 
	  Message getMessageBySBT(String seller, String buyer, Integer ticket_id);
	
	@Query(value = "SELECT * FROM message u WHERE u.ticket_id = ?1", nativeQuery=true)
	List<Message> getMessageByTicket_ID(String ticket_id);
	
	@Query(value = "SELECT * FROM message u WHERE u.receiver= ?1 or u.sender = ?1", nativeQuery=true)
	List<Message> getMessageByBuyer(String net_id);
	
//	
//	
//	@Query(value = "SELECT user_1_id FROM message u WHERE u.message_id = ?1", nativeQuery=true)
//	Integer getUser1Id(Integer message_id);
//	
//	
//	@Query(value = "SELECT user_2_id FROM message u WHERE u.message_id = ?1", nativeQuery=true)
//	Integer getUser2Id(Integer message_id);
//	
	//gets entire conversation between two users
	@Query(value = "SELECT * FROM message u WHERE (u.sender = ?1 or u.receiver = ?1) and (u.sender = ?2 or u.receiver =?2)", nativeQuery=true)
	Message[] getConvo(String sender, String receiver);

	
	@Query(value = "SELECT * FROM message u WHERE u.receiver= ?1 or u.sender = ?1", nativeQuery=true)
	List<Message> getPeople(String net_id);
	
}
