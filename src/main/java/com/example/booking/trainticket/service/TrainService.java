package com.example.booking.trainticket.service;

import java.time.LocalDate;
import java.util.List;

import com.example.booking.trainticket.dto.TrainDto;

public interface TrainService {

	List<TrainDto> searchTrains(String source, String destination,
			LocalDate departureDate);
	
	TrainDto fetchTrain(int trainId, LocalDate departureDate);
}
