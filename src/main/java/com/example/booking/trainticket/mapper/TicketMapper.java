package com.example.booking.trainticket.mapper;

import com.example.booking.trainticket.dto.TicketDto;
import com.example.booking.trainticket.model.Ticket;

public class TicketMapper {

	private TicketMapper() {
		throw new UnsupportedOperationException(
				"TicketMapper is a utility class and cannot be instantiated."
				);
	}

	// Map model object to Dto.
	public static TicketDto mapToDto(Ticket ticket) {
		return new TicketDto(
				ticket.getPnr(),
				ticket.getBookingStatus(),
				ticket.getTravellerId(),
				ticket.getTravellerName(),
				ticket.getDepartureDate(),
				ticket.getSource(),
				ticket.getDestination(),
				ticket.getTrainId(),
				ticket.getSeatNumber(),
				ticket.getFare()
				);
	}

	// Convert Dto to model object.
	public static Ticket mapToEntity(TicketDto ticketDto) {
		return new Ticket(
				ticketDto.getPnr(),
				ticketDto.getTrainId(),
				ticketDto.getTravellerId(),
				ticketDto.getDepartureDate(),
				ticketDto.getBookingStatus(),
				ticketDto.getSeatNumber(),
				ticketDto.getTravellerName(),
				ticketDto.getSource(),
				ticketDto.getDestination(),
				ticketDto.getFare()
				);
	}

}
