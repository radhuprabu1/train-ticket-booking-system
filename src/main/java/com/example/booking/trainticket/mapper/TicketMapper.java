package com.example.booking.trainticket.mapper;

import com.example.booking.trainticket.dto.TicketDto;
import com.example.booking.trainticket.model.Ticket;

public class TicketMapper {

	private TicketMapper() {
		// throw exception();
	}

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
