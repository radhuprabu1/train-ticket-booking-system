package com.example.booking.trainticket.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.booking.trainticket.model.Train;

class TrainRepositoryTest {

    private TrainRepository trainRepository;

    @BeforeEach
    void setUp() {
        trainRepository = new TrainRepository();
    }

    @Test
    void testSaveTrainSuccessfully() {
        Train train = createDummyTrain(1L);
        trainRepository.saveTrain(train);
        Train savedTrain = trainRepository.findTrainById(train.getTrainId()).get();
        assertNotNull(savedTrain);
        assertEquals(1, savedTrain.getTrainId());
    }

    @Test
    void testFindTrainByIdSuccess() {
        Train train = createDummyTrain(2L);
        trainRepository.saveTrain(train);

        Optional<Train> result = trainRepository.findTrainById(2L);

        assertTrue(result.isPresent());
        assertEquals("GT Express", result.get().getTrainName());
    }

    @Test
    void testFindTrainById_NotFound() {
        Optional<Train> result = trainRepository.findTrainById(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchTrainsFound() {
        Train train = createDummyTrain(3L);
        trainRepository.saveTrain(train);

        var results = trainRepository.searchTrains("Chennai", "Nagpur", LocalDate.now());

        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
    }

    @Test
    void testSearchTrainsNotFound() {
        var results = trainRepository.searchTrains("NonExist", "NonExist", LocalDate.now());

        assertTrue(results.isEmpty());
    }

    // Utility method for creating dummy train
    private Train createDummyTrain(Long trainId) {
        Map<LocalDate, Long> seats = new HashMap<>();
        Map<LocalDate, Double> prices = new HashMap<>();
        LocalDate date = LocalDate.now();
        seats.put(date, 50L);
        prices.put(date, 100.0);

        return new Train(trainId, "GT Express", "Chennai", "Nagpur", seats, prices);
    }
}
