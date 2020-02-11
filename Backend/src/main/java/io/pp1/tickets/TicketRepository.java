package io.pp1.tickets;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

	@Query(value = "SELECT * FROM ticket u where u.sold = 0 ORDER BY ticket_id DESC", nativeQuery=true)
	List<Ticket> findAll();
	
	@Query(value = "SELECT * FROM ticket u WHERE u.ticket_id = ?1", nativeQuery=true)
	List<Ticket> getTicketByID(Integer ticket_id);
	
	@Query(value = "SELECT * FROM ticket u WHERE u.opponent = ?1 and u.sport = ?2 and u.game_date = ?3 ORDER BY ticket_id DESC", nativeQuery=true)
	List<Ticket> filterTicketsByOpponentSportDate(String opponent, String sport, String gameDate); 
	
	@Query(value = "SELECT * FROM ticket u WHERE u.sport = ?1 and u.game_date = ?2 ORDER BY ticket_id DESC", nativeQuery=true)
	List<Ticket> filterTicketsBySportDate(String sport, String gameDate);
	
	@Query(value = "SELECT * FROM ticket u WHERE u.opponent = ?1 and u.game_date = ?2 ORDER BY ticket_id DESC", nativeQuery=true)
	List<Ticket> filterTicketsByOpponentDate(String opponent, String gameDate);
	
	@Query(value = "SELECT * FROM ticket u WHERE u.opponent = ?1 and u.sport = ?2 ORDER BY ticket_id DESC", nativeQuery=true)
	List<Ticket> filterTicketsByOpponentSport(String opponent, String sport);
	
	@Query(value = "SELECT * FROM ticket u WHERE u.game_date = ?1 ORDER BY ticket_id DESC", nativeQuery=true)
	List<Ticket> filterTicketsByDate(String game_date);
	
	@Query(value = "SELECT * FROM ticket u WHERE u.sport = ?1 ORDER BY ticket_id DESC", nativeQuery=true)
	List<Ticket> filterTicketsBySport(String sport);
	
	@Query(value = "SELECT * FROM ticket u WHERE u.opponent = ?1 ORDER BY ticket_id DESC", nativeQuery=true)
	List<Ticket> filterTicketsByOpponent(String opponent);
	
	@Query(value = "SELECT * FROM ticket u WHERE u.game_location = ?1 ", nativeQuery=true)
	List<Ticket> getTicketByLocation(String game_location);

	@Query(value = "SELECT * FROM ticket u WHERE u.net_id = ?1", nativeQuery=true)
	List<Ticket> getTicketBySellerID(String net_id);

	@Query(value = "SELECT * FROM ticket u WHERE u.price = ?1", nativeQuery=true)
	List<Ticket> getTicketByPricet(Integer price);

	@Query(value = "SELECT url FROM logo u WHERE u.img_name = ?1", nativeQuery=true)
	String getIconURL(String opponent);
	
	@Query(value = "SELECT * FROM ticket u WHERE u.buyer = ?1 Order BY rated ASC,ticket_id DESC", nativeQuery=true)
	List<Ticket> getTicketByBuyer(String net_id);
	
}
