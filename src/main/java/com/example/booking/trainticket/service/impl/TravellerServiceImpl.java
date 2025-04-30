package com.example.booking.trainticket.service.impl;

import org.springframework.stereotype.Service;

import com.example.booking.trainticket.dto.TravellerDto;
import com.example.booking.trainticket.exception.BadRequestException;
import com.example.booking.trainticket.mapper.TravellerMapper;
import com.example.booking.trainticket.model.Traveller;
import com.example.booking.trainticket.repository.TravellerRepository;
import com.example.booking.trainticket.service.TravellerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TravellerServiceImpl implements TravellerService {
	
	// Inject TravellerRepository as DI.
	private TravellerRepository travellerRepository;
	
	// Add traveller step by the client
	@Override
	public TravellerDto addTraveller(TravellerDto travellerDto) {
		// Converting DTO -> Model Object
		Traveller traveller = TravellerMapper.mapToEntity(travellerDto);
		// Add new traveller -> returns null if the traveller already exists
		Traveller newTraveller = travellerRepository.addTraveller(traveller);
		// Handles null pointer exception
		if (newTraveller == null) {
		    throw new BadRequestException("Traveller already exists");
		}
		// Convert Model Object -> DTO
		return TravellerMapper.mapToDto(newTraveller); // Converting back model obj -> DTO and return
	}
}
