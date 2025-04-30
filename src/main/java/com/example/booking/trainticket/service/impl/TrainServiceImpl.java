package com.example.booking.trainticket.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.booking.trainticket.dto.TrainDto;
import com.example.booking.trainticket.exception.BadRequestException;
import com.example.booking.trainticket.exception.ResourceNotFoundException;
import com.example.booking.trainticket.mapper.TrainMapper;
import com.example.booking.trainticket.model.Train;
import com.example.booking.trainticket.repository.TrainRepository;
import com.example.booking.trainticket.service.TrainService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TrainServiceImpl implements TrainService {
	
	/*
	 *  Enhancement Suggestion: Log a message or throw a custom exception if no train is found (optional depending on design).
	 */

	// Inject TrainRepository as DI
	private TrainRepository trainRepository;

	// Implement Retrieve Trains API method
	@Override
	public List<TrainDto> searchTrains(String source, String destination,
			LocalDate departureDate) {
		// Retrieve train list from the repository.
		List<Train> trains = trainRepository.searchTrains(source, destination, departureDate);
		// 1. Check if the train list is empty for the given search criteria.
		// 2. Convert Train model obj -> Train DTO and return.
		return Optional.ofNullable(trains)
				.filter(train -> !train.isEmpty())
				.orElseThrow(() -> new BadRequestException("Train not found for the given criteria"))
				.stream()
				.map(TrainMapper::mapToDto)
				.toList();
	}

	// Implement fetch Train By Id and date API method. 
	@Override
	public TrainDto fetchTrain(Long trainId, LocalDate departureDate) {
		// Extract Train object by train id, throw exception if trainId is invalid
		Train train = trainRepository.findTrainById(trainId).orElseThrow(
				() -> new ResourceNotFoundException("Train", "TrainId", trainId)
				);
		// Throw exception if the train doesn't operate for the given departure date.
		if (!train.getAvailableSeatsByDate().containsKey(departureDate)) {
			throw new BadRequestException("No Train available for departure date");
		}
		// Convert Train model -> Train DTO and return
		return TrainMapper.mapToDto(train);
	}
}