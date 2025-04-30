package com.example.booking.trainticket.service;

import java.time.LocalDate;

import com.example.booking.trainticket.dto.TicketDto;

public interface BookingService {

	TicketDto bookTicket(Long trainId, Long travellerId,
			LocalDate departureDate);
	
	void cancelTicket(String pnr);
	
	TicketDto retrieveTicket(String pnr);
}
