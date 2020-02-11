package io.pp1.tickets;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.pp1.rating.Rating;
import io.pp1.rating.RatingRepository;
import io.pp1.users.User;
import io.pp1.users.UserRepository;

@RestController
public class TicketController {

	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private UserRepository userRepository;	
	@Autowired
	private RatingRepository ratingRepository;


	@RequestMapping(method = RequestMethod.GET, path = "/tickets")
	public TicketService getAll() {
		return new TicketService(ticketRepository.findAll());
	}

	/**
	 * Filtering mechanism used to filter the data in a parsable way to the front end
	 * Sent as TicketService to identify object 
	 * @param filter
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/tickets/filter")
	public TicketService getTicketsByFilter(@RequestBody Ticket filter) {
		if(filter.getGame_date()!=null && filter.getSport()!=null && filter.getOpponent()!=null) {
			return new TicketService(ticketRepository.filterTicketsByOpponentSportDate(filter.getOpponent(),filter.getSport(),filter.getGame_date()));
		}
		else if(filter.getGame_date()!=null && filter.getSport()!=null && filter.getOpponent()==null) {
			return new TicketService(ticketRepository.filterTicketsBySportDate(filter.getSport(),filter.getGame_date()));
		}
		else if(filter.getGame_date()!=null && filter.getSport()==null && filter.getOpponent()!=null) {
			return new TicketService(ticketRepository.filterTicketsByOpponentDate(filter.getOpponent(),filter.getGame_date()));
		}
		else if(filter.getGame_date()==null && filter.getSport()!=null && filter.getOpponent()!=null) {
			return new TicketService(ticketRepository.filterTicketsByOpponentSport(filter.getOpponent(),filter.getSport()));
		}
		else if(filter.getGame_date()!=null && filter.getSport()==null && filter.getOpponent()==null) {
			return new TicketService(ticketRepository.filterTicketsByDate(filter.getGame_date()));
		}
		else if(filter.getGame_date()==null && filter.getSport()!=null && filter.getOpponent()==null) {
			return new TicketService(ticketRepository.filterTicketsBySport(filter.getSport()));
		}
		else if(filter.getGame_date()==null && filter.getSport()==null && filter.getOpponent()!=null) {
			return new TicketService(ticketRepository.filterTicketsByOpponent(filter.getOpponent()));
		}
		else 
			return new TicketService(ticketRepository.findAll());
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/tickets/ticket_id")
	public TicketService getTicket(@RequestBody Ticket id) {
		return new TicketService(ticketRepository.getTicketByID(id.getTicket_id()));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/tickets/net_id")
	public TicketService getSellerTickets(@RequestBody Ticket net_id) {
		return new TicketService(ticketRepository.getTicketBySellerID(net_id.getNet_id()));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/tickets/buyer")
	public TicketService getBuyerTickets(@RequestBody Ticket buyer) {
		return new TicketService(ticketRepository.getTicketByBuyer(buyer.getBuyer()));
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/tickets/location")
	public TicketService getByLocation(@RequestBody Ticket game_location) {
		return new TicketService(ticketRepository.getTicketByLocation(game_location.getGame_location()));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/tickets/price")
	public List<Ticket> getByPrice(@RequestBody Ticket price) {
		return ticketRepository.getTicketByPricet(price.getPrice());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/tickets") // @PostMapping(value = "/tickets")
	public void persist(@RequestBody final Ticket ticket) {
		ticket.setLogoURL(ticketRepository.getIconURL(ticket.getOpponent()));
		List<User> toUse = userRepository.getUserByNetID(ticket.getNet_id());
		ticket.setRating(toUse.get(0).getRating());
		ticketRepository.save(ticket);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/tickets/sold") 
	public void markSold(@RequestBody final Ticket ticket) {
		List<Ticket> markSold=ticketRepository.getTicketByID(ticket.getTicket_id());
		markSold.get(0).setSold(true);
		markSold.get(0).setBuyer(ticket.getBuyer());
		ticketRepository.save(markSold.get(0));
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/tickets/rated") 
	public void markRated(@RequestBody final Ticket ticket) {
		List<Ticket> markSold=ticketRepository.getTicketByID(ticket.getTicket_id());
		markSold.get(0).setRated(true);
		ticketRepository.save(markSold.get(0));
		Rating rating = new Rating();
		rating.setNet_id(ticket.getNet_id());
		rating.setRating(ticket.getRating());
		rating.setRating_id(0);
		 ratingRepository.save(rating);
			List<Rating> ratings =ratingRepository.getRatingByID(rating.getNet_id());
			int totalRatings=0;
			for(int i=0;i<ratings.size();i++) {
				totalRatings+= ratings.get(i).getRating();
			}
			double checking= Math.round(totalRatings/(ratings.size()+0.0));
			totalRatings= (int)checking;
			User userRating=userRepository.existByNetID(rating.getNet_id());
			userRating.setRating(totalRatings);
			userRepository.save(userRating);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/tickets/delete")
	public void delete(@RequestBody final Ticket ticket) {
		ticketRepository.deleteById(ticket.getTicket_id());
	}
	
}
