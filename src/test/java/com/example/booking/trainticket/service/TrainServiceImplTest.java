
package com.example.booking.trainticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.booking.trainticket.dto.TrainDto;
import com.example.booking.trainticket.exception.ResourceNotFoundException;
import com.example.booking.trainticket.model.Train;
import com.example.booking.trainticket.repository.TrainRepository;
import com.example.booking.trainticket.service.impl.TrainServiceImpl;

class TrainServiceImplTest {

	@Mock
	private TrainRepository trainRepository;

	@InjectMocks
	private TrainServiceImpl trainServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSearchTrains() {
		Train dummyTrain = createDummyTrain();
		List<Train> trainList = Collections.singletonList(dummyTrain);

		when(trainRepository.searchTrains(anyString(), anyString(), any(LocalDate.class))).thenReturn(trainList);

		List<TrainDto> result = trainServiceImpl.searchTrains("CityA", "CityB", LocalDate.now());

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals(dummyTrain.getTrainName(), result.get(0).getTrainName());
	}

	@Test
	void testFetchTrainFound() {
		Train dummyTrain = createDummyTrain();

		when(trainRepository.findTrainById(anyLong())).thenReturn(Optional.of(dummyTrain));

		TrainDto result = trainServiceImpl.fetchTrain(1L, LocalDate.now());

		assertNotNull(result);
		assertEquals(dummyTrain.getTrainName(), result.getTrainName());
	}

	    @Test
	    void testFetchTrainNotFound() {
	        when(trainRepository.findTrainById(anyLong())).thenReturn(Optional.empty());
	
	
	        assertThrows(ResourceNotFoundException.class, () -> 
	        trainServiceImpl.fetchTrain(1L, LocalDate.now()));
	    }

	private Train createDummyTrain() {
		Map<LocalDate, Long> seats = new HashMap<>();
		Map<LocalDate, Double> prices = new HashMap<>();
		LocalDate date = LocalDate.now();
		seats.put(date, 10L);
		prices.put(date, 100.0);

		return new Train(1L, "ExpressTest", "CityA", "CityB", seats, prices);
	}

}