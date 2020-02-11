package io.pp1.tickets;

import java.util.List;


public class TicketService {
	
	
	private List<Ticket> ticket;
	
	
	public TicketService(List<Ticket> ticket) {
		this.ticket = ticket;
		
	}


	public List<Ticket> getTicket() {
		return ticket;
	}
	
	


}
