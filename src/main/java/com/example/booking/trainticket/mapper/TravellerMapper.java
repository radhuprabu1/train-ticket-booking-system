package com.example.booking.trainticket.mapper;

import com.example.booking.trainticket.dto.TravellerDto;
import com.example.booking.trainticket.model.Traveller;

public class TravellerMapper {
	
	private TravellerMapper() {
		//throw exception
	}

	public static TravellerDto mapToDto(Traveller traveller) {
		return new TravellerDto(
				traveller.getTravellerId(),
				traveller.getFirstName(),
				traveller.getLastName(),
				traveller.getContactNumber()
				);
	}

	public static Traveller mapToEntity(TravellerDto travellerDto) {
		return new Traveller(
				travellerDto.getTravellerId(),
				travellerDto.getFirstName(),
				travellerDto.getLastName(),
				travellerDto.getContactNumber()
				);
	}

}
