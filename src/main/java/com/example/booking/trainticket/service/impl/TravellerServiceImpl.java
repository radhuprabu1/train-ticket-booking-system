package com.example.booking.trainticket.service.impl;

import org.springframework.stereotype.Service;

import com.example.booking.trainticket.dto.TravellerDto;
import com.example.booking.trainticket.mapper.TravellerMapper;
import com.example.booking.trainticket.model.Traveller;
import com.example.booking.trainticket.repository.TravellerRepository;
import com.example.booking.trainticket.service.TravellerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TravellerServiceImpl implements TravellerService {
	/*
	 *  Potential Bug: addTraveller in the repository returns null if the traveller already exists. 
	 *  This can cause NullPointerException during mapping. You should handle that case and throw a DuplicateTravellerException.
	 */
	// Inject TravellerRepository as DI.
	private TravellerRepository travellerRepository;
	
	// Add traveller step by the client
	@Override
	public TravellerDto addTraveller(TravellerDto travellerDto) {
		// Converting DTO -> Model Object
		Traveller traveller = TravellerMapper.mapToEntity(travellerDto);
		// Add new traveller
		Traveller newTraveller = travellerRepository.addTraveller(traveller);
		// Throw exception if the traveller obj is empty
		if (newTraveller == null) {
		   // throw new DuplicateResourceException("Traveller", "Traveller_Id", traveller.getTravellerId());
		}
		return TravellerMapper.mapToDto(newTraveller); // Converting back model obj -> DTO and return
	}
}
