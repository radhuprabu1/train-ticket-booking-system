package com.example.booking.trainticket.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.booking.trainticket.dto.TicketDto;
import com.example.booking.trainticket.mapper.TicketMapper;
import com.example.booking.trainticket.model.BookingStatus;
import com.example.booking.trainticket.model.Ticket;
import com.example.booking.trainticket.model.Train;
import com.example.booking.trainticket.model.Traveller;
import com.example.booking.trainticket.repository.TicketRepository;
import com.example.booking.trainticket.repository.TrainRepository;
import com.example.booking.trainticket.repository.TravellerRepository;
import com.example.booking.trainticket.service.BookingService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

	// Inject Repositories as DI
	private TicketRepository ticketRepository;
	private TrainRepository trainRepository;
	private TravellerRepository travellerRepository;

	// Implement Book Ticket API method.
	@Override
	public TicketDto bookTicket(int trainId, int travellerId,
			LocalDate departureDate) {
		// Extract Train object from the train repo using TrainId.
		Train train = trainRepository.findTrainById(trainId).orElseThrow(null); // Throw exception if the train id is invalid.
		// Extract traveller obj from traveller repo using traveller id.
		Traveller traveller = travellerRepository.findTravellerById(travellerId).orElseThrow(null); // Handle exception if traveller id is invalid
		// Get available seats of the train for the given departure date.
		Integer availSeats = train.getAvailableSeatsByDate()
				.get(departureDate);
		// Initiate ticket booking using ticket repository
		Ticket ticket = ticketRepository.addBooking(train, traveller, departureDate);
		// If Ticket status is confirmed -> reduce the available seats of the corresponding train
		if(ticket.getBookingStatus().equals(BookingStatus.CONFIRMED)) {
			train.getAvailableSeatsByDate().put(departureDate, availSeats - 1);
			trainRepository.saveTrain(train); // save the updated Train
		}
		// Convert ticket model -> ticket dto and return
		return TicketMapper.mapToDto(ticket);
	}

	// Implement Cancel Ticket API method.
	@Override
	public void cancelTicket(String pnr) {

		// Validate ticket using pnr.
		Ticket ticket = ticketRepository.findTicketByPnr(pnr).orElseThrow(null); // Throw exception if the pnr is invalid
		// Extract train details using train repository from the ticket.
		Train train = trainRepository.findTrainById(ticket.getTrainId()).orElseThrow(null); // Throw exception if trainId is invalid
		// Extract traveller details using traveller repo from the ticket.
		travellerRepository.findTravellerById(ticket.getTravellerId()).orElseThrow(null);
		// Delete the ticket from the repository.
		ticketRepository.deleteTicket(pnr);
		/*
		 * After deleting the ticket, we have to-
		 *  1. Update the available seats of the train - Increase available seats
		 *  2. Process Waitlist ticket from the queue.
		 *  3. After issuing confirmed ticket to the traveller - Reduce the available seats again.
		 *  4. Update train Repo again.
		 */

		// 1. Increase available seats
		LocalDate departureDate = ticket.getDepartureDate();
		Integer currentSeats = train.getAvailableSeatsByDate().get(departureDate);
		if (currentSeats != null && currentSeats > 0) {
			train.getAvailableSeatsByDate().put(departureDate, currentSeats + 1);
		}

		// 2. Process waitlist
		if (!ticketRepository.isWaitlistEmpty()) {
			Ticket waitlistedTicket = ticketRepository.pollWaitlist();
			waitlistedTicket.setBookingStatus(BookingStatus.CONFIRMED);
			waitlistedTicket.setSeatNumber(train.getAvailableSeatsByDate().get(departureDate));
			ticketRepository.saveTicket(waitlistedTicket);

			// 3. Decrease seats again after confirming waitlist ticket
			if (currentSeats != null && currentSeats > 0) {
				train.getAvailableSeatsByDate().put(departureDate, currentSeats - 1);
			}
		}
		trainRepository.saveTrain(train); // save the updated Train
	}

	// Implement Retrieve Ticket API method.
	@Override
	public TicketDto retrieveTicket(String pnr) {
		// Validate ticket using pnr.
		Ticket getTicket = ticketRepository.findTicketByPnr(pnr).orElseThrow(null); // Throw exception if pnr is not valid.
		return TicketMapper.mapToDto(getTicket);
	}
}
