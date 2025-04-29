package com.example.booking.trainticket.mapper;

import com.example.booking.trainticket.dto.TrainDto;
import com.example.booking.trainticket.model.Train;

public class TrainMapper {
	
	private TrainMapper() {
		// handle exception.
	}

	public static TrainDto mapToDto(Train train) {
		return new TrainDto(
				train.getTrainId(),
				train.getTrainName(),
				train.getSource(),
				train.getDestination(),
				train.getAvailableSeatsByDate(),
				train.getTicketPriceByDate()
				);
	}

	public static Train mapToEntity(TrainDto trainDto) {
		return new Train(
				trainDto.getTrainId(),
				trainDto.getTrainName(),
				trainDto.getSource(),
				trainDto.getDestination(),
				trainDto.getAvailableSeatsByDate(),
				trainDto.getTicketPriceByDate()
				);
	}

}
