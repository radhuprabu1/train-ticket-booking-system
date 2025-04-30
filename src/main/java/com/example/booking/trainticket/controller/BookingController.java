package com.example.booking.trainticket.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.booking.trainticket.dto.TicketDto;
import com.example.booking.trainticket.dto.TrainDto;
import com.example.booking.trainticket.dto.TravellerDto;
import com.example.booking.trainticket.service.BookingService;
import com.example.booking.trainticket.service.TrainService;
import com.example.booking.trainticket.service.TravellerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class BookingController {

	private BookingService bookingService;
	private TrainService trainService;
	private TravellerService travellerService;


	@GetMapping("/trains/search")
	public ResponseEntity<List<TrainDto>> retrieveTrains(
			@RequestParam String source,
			@RequestParam String destination,
			@RequestParam LocalDate departureDate
			) {
		List<TrainDto> trains = trainService.searchTrains(
				source, destination, departureDate);
		return new ResponseEntity<>(trains, HttpStatus.OK);
	}

	@PostMapping("/journey/{TrainId}/{DepartureDate}")
	public ResponseEntity<TrainDto> addJourney(
			@PathVariable("TrainId") Long trainId,
			@PathVariable("DepartureDate") LocalDate departureDate
			) {
		TrainDto train = trainService.fetchTrain(trainId, departureDate);
		return new ResponseEntity<>(train,HttpStatus.OK);
	}

	@PostMapping("/travellers")
	public ResponseEntity<TravellerDto> addTraveller(@Valid @RequestBody TravellerDto traveller) {
		TravellerDto createdTraveller = travellerService.addTraveller(traveller);
		return new ResponseEntity<>(createdTraveller, HttpStatus.OK);
	}

	@PostMapping("/tickets/{trainId}/{travellerId}/{departureDate}")
	public ResponseEntity<TicketDto> bookTicket(
			@PathVariable Long trainId,
			@PathVariable Long travellerId,
			@PathVariable LocalDate departureDate
			) {
		TicketDto ticket = bookingService.bookTicket(trainId, travellerId, departureDate);
		return new ResponseEntity<>(ticket, HttpStatus.CREATED);
	}

	@DeleteMapping("/cancel/{pnr}")
	public ResponseEntity<String> cancelTicket(@PathVariable String pnr) {
		bookingService.cancelTicket(pnr);
		return new ResponseEntity<>("Ticket Cancelled Successfully", HttpStatus.OK);
	}

	@GetMapping("/ticket/{pnr}")
	public ResponseEntity<TicketDto> retrieveTicket(@PathVariable String pnr) {
		TicketDto ticket = bookingService.retrieveTicket(pnr);
		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}
}
