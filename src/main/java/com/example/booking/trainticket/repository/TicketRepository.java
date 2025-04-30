package com.example.booking.trainticket.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import org.springframework.stereotype.Repository;

import com.example.booking.trainticket.model.BookingStatus;
import com.example.booking.trainticket.model.Ticket;
import com.example.booking.trainticket.model.Train;
import com.example.booking.trainticket.model.Traveller;
import com.example.booking.trainticket.utils.PnrGenerator;

/**
 * Repository class to manage Ticket entities.
 */
@Repository
public class TicketRepository {

	// Map PNR -> a ticket
	private final Map<String, Ticket> ticketMap = new HashMap<>();
	// if the ticket status is waitlist add to this waitlistQueue
	private final Queue<Ticket> waitlistQueue = new LinkedList<>();

	// Book ticket for the traveller
	public Ticket addBooking(Train train, Traveller traveller,
			LocalDate departuredate) {
		Long availSeats = train.getAvailableSeatsByDate()
				.get(departuredate);

		// Creating ticket object to populate response
		Ticket ticket = new Ticket();
		ticket.setDepartureDate(departuredate);
		ticket.setFare(train.getTicketPriceByDate().get(departuredate));
		ticket.setTrainId(train.getTrainId());
		ticket.setTravellerId(traveller.getTravellerId());
		ticket.setPnr(PnrGenerator.generatePnr());
		ticket.setTravellerName(traveller.getFirstName() + " " + traveller.getLastName());
		ticket.setDestination(train.getDestination());
		ticket.setSource(train.getSource());
		// Set Booking Status -> Confirmed if the seats are available for the selected train.
		if(availSeats != null && availSeats > 0) {
			ticket.setBookingStatus(BookingStatus.CONFIRMED);
			ticket.setSeatNumber(availSeats);
		}
		// Set Booking Status -> Waitlisted if the seats are not available for the selected train.
		else {
			ticket.setBookingStatus(BookingStatus.WAITLISTED);
			ticket.setSeatNumber(availSeats);
		}
		// save the ticket to the ticket map/waitlist queue.
		saveTicket(ticket);
		return ticket;
	}

	// Check booking status and save the ticket to the ticket map/waitlist queue.
	public void saveTicket(Ticket ticket) {
		if (ticket.getBookingStatus()
				.equals(BookingStatus.WAITLISTED)) {
			waitlistQueue.add(ticket);
		}
		ticketMap.put(ticket.getPnr(), ticket);
	}
	
	// Checks for the same traveller id inside ticket map and returns true if the traveller already has a ticket.
	public boolean hasExistingBooking(Long travellerId, Long trainId, LocalDate date) {
		return ticketMap.values().stream().anyMatch(
				ticket -> ticket.getTravellerId() == travellerId &&
				ticket.getTrainId() == trainId &&
				ticket.getDepartureDate().equals(date)
				);
	}
	
	// Find ticket using pnr.
	public Optional<Ticket> findTicketByPnr(String pnr) {
		return Optional.ofNullable(ticketMap.get(pnr));
	}

	// Removes ticket from the map.
	public void deleteTicket(String pnr) {
		ticketMap.remove(pnr);
	}

	public void addToWaitlist(Ticket ticket) {
		waitlistQueue.add(ticket);
	}
	// Process waitlist tickets.
	public Ticket pollWaitlist() {
		return waitlistQueue.poll();
	}

	public boolean isWaitlistEmpty() {
		return waitlistQueue.isEmpty();
	}
}