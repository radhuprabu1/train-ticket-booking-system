package com.example.booking.trainticket.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

import com.example.booking.trainticket.model.Ticket;

/**
 * Repository class to manage Ticket entities.
 */
public class TicketRepository {

	// Map PNR -> a ticket
	private final Map<String, Ticket> ticketMap = new HashMap<>();

	private final Queue<Ticket> waitlistQueue = new LinkedList<>();

	public void saveTicket(Ticket ticket) {

		ticketMap.put(ticket.getPnr(), ticket);
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
