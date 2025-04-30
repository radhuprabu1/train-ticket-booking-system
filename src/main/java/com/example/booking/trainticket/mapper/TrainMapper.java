package com.example.booking.trainticket.mapper;

import com.example.booking.trainticket.dto.TrainDto;
import com.example.booking.trainticket.model.Train;

public class TrainMapper {
	
	private TrainMapper() {
		throw new UnsupportedOperationException(
				"TrainMapper is a utility class and cannot be instantiated."
				);
	}

	// Convert Train model to TrainDto.
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

	// Convert Train Dto to Train model.
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
