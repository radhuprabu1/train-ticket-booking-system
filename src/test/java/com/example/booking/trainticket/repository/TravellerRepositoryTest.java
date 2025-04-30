package com.example.booking.trainticket.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.booking.trainticket.model.Traveller;

class TravellerRepositoryTest {

	private TravellerRepository travellerRepository;

	@BeforeEach
	void setUp() {
		travellerRepository = new TravellerRepository();
	}

	@Test
	void testAddTravellerSuccessfully() {
		Traveller traveller = new Traveller(1L, "Sachin", "Tendulkar", "1234567890");
		Traveller addedTraveller = travellerRepository.addTraveller(traveller);
		assertNotNull(addedTraveller);
		assertEquals("Sachin", addedTraveller.getFirstName());
	}

	@Test
	void testAddDuplicateTravellerReturnsNull() {
		Traveller traveller = new Traveller(1L, "Sachin", "Tendulkar", "123456890");
		travellerRepository.addTraveller(traveller);
		Traveller duplicate = travellerRepository.addTraveller(traveller);

		assertNull(duplicate);
	}

	@Test
	void testFindTravellerById() {
		Traveller traveller = new Traveller(2L, "MS", "Dhoni", "0987654321");
		travellerRepository.addTraveller(traveller);

		Optional<Traveller> result = travellerRepository.findTravellerById(2L);
		assertTrue(result.isPresent());
		assertEquals("MS", result.get().getFirstName());
	}

	@Test
	void testFindTravellerById_NotFound() {
		Optional<Traveller> result = travellerRepository.findTravellerById(99L);
		assertTrue(result.isEmpty());
	}

	@Test
	void testRemoveTraveller() {
		Traveller traveller = new Traveller(3L, "Rohit", "Sharma", "1237894560");
		travellerRepository.addTraveller(traveller);

		travellerRepository.removeTraveller(traveller);

		Optional<Traveller> result = travellerRepository.findTravellerById(3L);
		assertTrue(result.isEmpty());
	}
}
