package io.pp1.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.pp1.tickets.Ticket;
import io.pp1.tickets.TicketController;
import io.pp1.tickets.TicketRepository;
import io.pp1.tickets.TicketService;

public class TestTicketRepository {

	@InjectMocks
	TicketController ticketController;
	


	@Mock
	TicketRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getTicketBySellerID() {
		List<Ticket> list = new ArrayList<Ticket>();
		
		Ticket acctOne = new Ticket(10, "SportTest", "LocationTest", "DateTest", "TimeTest", 1, "aaa", "Baylor");
		Ticket acctTwo = new Ticket(10, "SportTest2", "LocationTest2", "DateTest2", "TimeTest2", 2, "bbb", "ISU");
		Ticket acctthree = new Ticket(10, "SportTest3", "LocationTest3", "DateTest3", "TimeTest3", 3, "aaa", "Baylor");
		Ticket acctfour = new Ticket(10, "SportTest4", "LocationTest4", "DateTest4", "TimeTest4", 4, "bbb", "Baylor");

		list.add(acctOne);
		list.add(acctTwo);
		list.add(acctthree);
		list.add(acctfour);
		
		Ticket thisone = new Ticket(10, "SportTest2", "LocationTest2", "DateTest2", "TimeTest2", 2, "aaa", "ISU");

		when(repo.getTicketBySellerID("aaa")).thenReturn(list);
		
		TicketService check = ticketController.getSellerTickets(thisone);
		List<Ticket> lookAT=check.getTicket();
		
		assertEquals("aaa", lookAT.get(0).getNet_id());
		assertNotSame("aaa", lookAT.get(1).getNet_id());
		assertEquals("aaa", lookAT.get(2).getNet_id());
		assertNotSame("aaa", lookAT.get(3).getNet_id());
		
	}
	
	@Test
	public void  getTicketByID() {
		List<Ticket> list = new ArrayList<Ticket>();
		
		Ticket acctOne = new Ticket(10, "SportTest", "LocationTest", "DateTest", "TimeTest", 1, "aaa", "Baylor");
		Ticket acctTwo = new Ticket(10, "SportTest2", "LocationTest2", "DateTest2", "TimeTest2", 2, "bbb", "ISU");
		
		
		list.add(acctOne);
		list.add(acctTwo);
		
		when(repo.getTicketByID(10)).thenReturn(list);
		
		Ticket thisone = new Ticket(10, "SportTest2", "LocationTest2", "DateTest2", "TimeTest2", 2, "bbb", "ISU");
		
		TicketService check = ticketController.getTicket(thisone);
		List<Ticket> lookAT=check.getTicket();
		
		assertEquals("10", lookAT.get(0).getTicket_id().toString());
		assertEquals("SportTest", lookAT.get(0).getSport());
		assertEquals("LocationTest2", lookAT.get(1).getGame_location());
		assertEquals("DateTest", lookAT.get(0).getGame_date());
		assertNotSame("TimeTest", lookAT.get(1).getGame_time());
		assertNotSame("10", lookAT.get(0).getPrice().toString());
		assertNotSame("fail", lookAT.get(1).getNet_id());
		assertNotSame("B", lookAT.get(0).getOpponent());
	}
	
	@Test
	public void getAll() {
		List<Ticket> list = new ArrayList<Ticket>();
		
		Ticket acctOne = new Ticket(10, "SportTest", "LocationTest", "DateTest", "TimeTest", 1, "aaa", "Baylor");
		Ticket acctTwo = new Ticket(1, "SportTest2", "LocationTest2", "DateTest2", "TimeTest2", 2, "bbb", "ISU");
		Ticket acctthree = new Ticket(9, "SportTest3", "LocationTest3", "SportTest3", "TimeTest3", 3, "aaa", "Baylor");

		list.add(acctOne);
		list.add(acctTwo);
		list.add(acctthree);

		when(repo.findAll()).thenReturn(list);
		
		TicketService check = ticketController.getAll();
		List<Ticket> lookAT=check.getTicket();
		
		assertEquals("aaa", lookAT.get(0).getNet_id());
		assertNotSame("aaa", lookAT.get(1).getNet_id());
		assertEquals("aaa", lookAT.get(2).getNet_id());
		assertEquals("SportTest3", lookAT.get(2).getSport());
		assertNotSame("LocationTest3", lookAT.get(0).getGame_location());
		assertEquals("SportTest3", lookAT.get(2).getSport());
		
		
	}
	
}
