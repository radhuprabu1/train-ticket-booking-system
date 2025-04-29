package com.example.booking.trainticket.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.booking.trainticket.model.BookingStatus;
import com.example.booking.trainticket.model.Ticket;
import com.example.booking.trainticket.model.Train;
import com.example.booking.trainticket.model.Traveller;

/**
 * Repository class to manage Ticket entities.
 */
@Repository
public class TicketRepository {

	// Map PNR -> a ticket
	private final Map<String, Ticket> ticketMap = new HashMap<>();
	// if the ticket status is waitlist add to this waitlistQueue
	private final Queue<Ticket> waitlistQueue = new LinkedList<>();

	public void saveTicket(Ticket ticket) {
		if (ticket.getBookingStatus()
				.equals(BookingStatus.WAITLISTED)) {
			waitlistQueue.add(ticket);
		}
		ticketMap.put(ticket.getPnr(), ticket);
	}

	public Ticket addBooking(Train train, Traveller traveller,
			LocalDate departuredate) {
		Integer availSeats = train.getAvailableSeatsByDate()
				.get(departuredate);
		
		// Creating ticket object to populate response
		Ticket ticket = new Ticket();
		ticket.setDepartureDate(departuredate);
		ticket.setFare(train.getTicketPriceByDate().get(departuredate));
		ticket.setTrainId(train.getTrainId());
		ticket.setTravellerId(traveller.getTravellerId());
		ticket.setPnr(UUID.randomUUID().toString());
		ticket.setTravellerName(traveller.getFirstName() + " " + traveller.getLastName());
		ticket.setDestination(train.getDestination());
		ticket.setSource(train.getSource());
		
		if(availSeats != null && availSeats > 0) {
			ticket.setBookingStatus(BookingStatus.CONFIRMED);
			ticket.setSeatNumber(availSeats);
		}
		else {
			ticket.setBookingStatus(BookingStatus.WAITLISTED);
			ticket.setSeatNumber(0);
		}
		saveTicket(ticket);
		return ticket;
	}
	public Optional<Ticket> findTicketByPnr(String pnr) {
		return Optional.ofNullable(ticketMap.get(pnr));
	}

	public void deleteTicket(String pnr) {
		ticketMap.remove(pnr);
	}

	public void addToWaitlist(Ticket ticket) {
		waitlistQueue.add(ticket);
	}

	public Ticket pollWaitlist() {
		return waitlistQueue.poll();
	}

	public boolean isWaitlistEmpty() {
		return waitlistQueue.isEmpty();
	}
}
