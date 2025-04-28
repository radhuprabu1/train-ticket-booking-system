package com.example.booking.trainticket.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.booking.trainticket.model.Train;

/**
 * Train Repository class to manage Train entity.
 */
public class TrainRepository {

	// Map Train Id -> the corresponding Train
	private final HashMap<Integer, Train> trainMap = new HashMap<>();

	// Add a new train
	public Train addTrain(Train train) {
		trainMap.put(train.getTrainId(), train);
		return trainMap.get(train.getTrainId());
	}

	public Train findTrainById(int trainId) {
		return trainMap.get(trainId);
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

}
