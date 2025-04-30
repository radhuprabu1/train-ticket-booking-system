package com.example.booking.trainticket.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.example.booking.trainticket.model.Train;

/**
 * Train Repository class to manage Train entity.
 */
@Repository
public class TrainRepository {

	// Map Train Id -> the corresponding Train
	private final ConcurrentHashMap<Long, Train> trainMap = new ConcurrentHashMap<>();

	// Add a new train
	public Train addTrain(Train train) {
		// If a train already present -> throw exception
		if (trainMap.containsKey(train.getTrainId())) {
			throw new IllegalArgumentException("Train ID " + train.getTrainId() + " already exists.");
		}
		trainMap.put(train.getTrainId(), train);
		return trainMap.get(train.getTrainId());
	}

	// find the train by trainId(For Service class impl logic)
	public Optional<Train> findTrainById(Long trainId) {
		return Optional.ofNullable(trainMap.get(trainId));
	}

	// Retrieving Train List based on user query.
	// Searches trains by source, destination and departure date and returns a Train List.
	public List<Train> searchTrains(String source, String destination,
			LocalDate departureDate) {
		List<Train> result = new ArrayList<>();
		for(Train train : trainMap.values()) {
			if(train.getSource().equalsIgnoreCase(source) &&
					train.getDestination().equalsIgnoreCase(destination) &&
					train.getAvailableSeatsByDate().containsKey(departureDate)) {
				result.add(train);
			}
		}
		return result;
	}

	//Returns all the trains.
	public List<Train> getAllTrains() {
		return new ArrayList<>(trainMap.values());
	}

	// Update the train repo after booking/cancelling a ticket.
	public void saveTrain(Train train) {
		trainMap.put(train.getTrainId(), train);
	}

}
