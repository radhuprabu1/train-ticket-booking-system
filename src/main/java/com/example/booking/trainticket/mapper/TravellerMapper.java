package com.example.booking.trainticket.mapper;

import com.example.booking.trainticket.dto.TravellerDto;
import com.example.booking.trainticket.model.Traveller;

public class TravellerMapper {
	
	private TravellerMapper() {
		throw new UnsupportedOperationException(
				"Traveller Mapper is a utility class and cannot be instantiated."
				);
	}

	// Convert traveller to traveller dto.
	public static TravellerDto mapToDto(Traveller traveller) {
		return new TravellerDto(
				traveller.getTravellerId(),
				traveller.getFirstName(),
				traveller.getLastName(),
				traveller.getContactNumber()
				);
	}

	// Convert traveller dto to traveller object.
	public static Traveller mapToEntity(TravellerDto travellerDto) {
		return new Traveller(
				travellerDto.getTravellerId(),
				travellerDto.getFirstName(),
				travellerDto.getLastName(),
				travellerDto.getContactNumber()
				);
	}

}
