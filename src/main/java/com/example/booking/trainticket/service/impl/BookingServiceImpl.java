package com.example.booking.trainticket.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.booking.trainticket.dto.TicketDto;
import com.example.booking.trainticket.exception.BadRequestException;
import com.example.booking.trainticket.exception.ResourceNotFoundException;
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

	// Inject Repositories = DI
	private TicketRepository ticketRepository;
	private TrainRepository trainRepository;
	private TravellerRepository travellerRepository;

	// Implement Book Ticket API method.
	@Override
	public TicketDto bookTicket(Long trainId, Long travellerId,
			LocalDate departureDate) {
		// Extract Train object from the train repo using TrainId.
		Train train = trainRepository.findTrainById(trainId).orElseThrow(
				() -> new ResourceNotFoundException("Train", "TrainId", trainId) // Throw exception if the train id is invalid.
				);
		// Extract traveller obj from traveller repo using traveller id.
		Traveller traveller = travellerRepository.findTravellerById(travellerId).orElseThrow(
				() -> new ResourceNotFoundException("Traveller", "TravellerId", travellerId) // Handle exception if traveller id is invalid
				);
		// Check if the traveller already has a ticket
		if (ticketRepository.hasExistingBooking(travellerId, trainId, departureDate)) {
		    throw new BadRequestException("Traveller has already booked a ticket for this train on the selected date.");
		}
		// Initiate ticket booking using ticket repository
		Ticket ticket = ticketRepository.addBooking(train, traveller, departureDate);
		// Get available seats of the train for the given departure date.
		Long availSeats = train.getAvailableSeatsByDate().get(departureDate);
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
		Ticket ticket = ticketRepository.findTicketByPnr(pnr).orElseThrow(
				() -> new BadRequestException("Invalid PNR Request") // Throw exception if the pnr is invalid
				);
		// Extract train details using train repository from the ticket.
		Train train = trainRepository.findTrainById(ticket.getTrainId()).orElseThrow(
				() -> new ResourceNotFoundException("Train", "Train_Id", ticket.getTrainId()) // Throw exception if trainId is invalid
				);
		// Extract traveller details using traveller repo from the ticket.
		travellerRepository.findTravellerById(ticket.getTravellerId()).orElseThrow(
				() -> new ResourceNotFoundException("Traveller", "TravellerId", ticket.getTravellerId())
				);
		// Delete the ticket from the repository.
		ticketRepository.deleteTicket(pnr);
		
		/* 
		 * For reference:
		 * After deleting the ticket, we need to -
		 *  1. Update the available seats of the train - Increase available seats
		 *  2. Process Waitlist ticket from the queue.
		 *  3. After issuing confirmed ticket to the traveller - Reduce the available seats again.
		 *  4. Update train Repo again.
		 */

		// 1. Increase available seats
		LocalDate departureDate = ticket.getDepartureDate();
		Long availSeats = train.getAvailableSeatsByDate().get(departureDate);
		if (availSeats != null) {
			train.getAvailableSeatsByDate().put(departureDate, availSeats + 1);
		}

		// 2. Process waitlist -> confirmed status
		if (!ticketRepository.isWaitlistEmpty()) {
			Ticket waitlistedTicket = ticketRepository.pollWaitlist();
			waitlistedTicket.setBookingStatus(BookingStatus.CONFIRMED);
			waitlistedTicket.setSeatNumber(train.getAvailableSeatsByDate().get(departureDate));
			ticketRepository.saveTicket(waitlistedTicket);

			// 3. Decrease seats again after confirming waitlist ticket
			if (availSeats != null && availSeats > 0) {
				train.getAvailableSeatsByDate().put(departureDate, availSeats - 1);
			}
		}
		// 4. Save the updated Train
		trainRepository.saveTrain(train);
	}

	// Implement Retrieve Ticket API method.
	@Override
	public TicketDto retrieveTicket(String pnr) {
		// Validate ticket using pnr.
		Ticket getTicket = ticketRepository.findTicketByPnr(pnr).orElseThrow(
				() -> new BadRequestException("Invalid PNR") // Throw exception if pnr is not valid.
				);
		// Convert model object to dto and return.
		return TicketMapper.mapToDto(getTicket);
	}
}
