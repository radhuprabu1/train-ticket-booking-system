package com.example.booking.trainticket.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.booking.trainticket.model.BookingStatus;
import com.example.booking.trainticket.model.Ticket;
import com.example.booking.trainticket.model.Train;
import com.example.booking.trainticket.model.Traveller;

class TicketRepositoryTest {

	private TicketRepository ticketRepository;
	private Train dummyTrain;
	private Traveller dummyTraveller;

	@BeforeEach
	void setUp() {
		ticketRepository = new TicketRepository();
		dummyTrain = createDummyTrain();
		dummyTraveller = createDummyTraveller();
	}

	@Test
	void testAddAndFindTicket() {
		Ticket ticket = ticketRepository.addBooking(dummyTrain, dummyTraveller, LocalDate.now());

		Optional<Ticket> foundTicket = ticketRepository.findTicketByPnr(ticket.getPnr());

		assertTrue(foundTicket.isPresent());
		assertEquals(ticket.getPnr(), foundTicket.get().getPnr());
	}

	@Test
	void testDeleteTicket() {
		Ticket ticket = ticketRepository.addBooking(dummyTrain, dummyTraveller, LocalDate.now());

		ticketRepository.deleteTicket(ticket.getPnr());

		Optional<Ticket> deletedTicket = ticketRepository.findTicketByPnr(ticket.getPnr());

		assertTrue(deletedTicket.isEmpty());
	}

	@Test
	void testWaitlistBehavior() {
		Ticket ticket = new Ticket();
		ticket.setPnr(UUID.randomUUID().toString());
		ticket.setBookingStatus(BookingStatus.WAITLISTED);

		ticketRepository.saveTicket(ticket);

		assertFalse(ticketRepository.isWaitlistEmpty());

		Ticket polled = ticketRepository.pollWaitlist();

		assertEquals(ticket.getPnr(), polled.getPnr());
		assertTrue(ticketRepository.isWaitlistEmpty());
	}

	// Utility methods
	private Train createDummyTrain() {
		Map<LocalDate, Long> seats = new HashMap<>();
		Map<LocalDate, Double> prices = new HashMap<>();
		LocalDate date = LocalDate.now();
		seats.put(date, 5L);
		prices.put(date, 150.0);

		return new Train(10L, "Delhi Express", "Chennai", "Delhi", seats, prices);
	}

	private Traveller createDummyTraveller() {
		return new Traveller(101L, "John", "Cena", "1239874560");
	}
}
