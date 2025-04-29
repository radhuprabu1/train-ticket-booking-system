package com.example.booking.trainticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.booking.trainticket.dto.TravellerDto;
import com.example.booking.trainticket.mapper.TravellerMapper;
import com.example.booking.trainticket.model.Traveller;
import com.example.booking.trainticket.repository.TravellerRepository;
import com.example.booking.trainticket.service.impl.TravellerServiceImpl;

class TravellerServiceImplTest {

    @Mock // @Mock -> Creates a fake TravellerRepository
    private TravellerRepository travellerRepository;

 // @InjectMocks -> Injects the mocked repository into TravellerServiceImpl
    @InjectMocks
    private TravellerServiceImpl travellerServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTraveller() {
        TravellerDto dummyTravellerDto = new TravellerDto(1, "Sachin", "Tendulkar", "1234567890");

        Traveller dummyTraveller = TravellerMapper.mapToEntity(dummyTravellerDto);
        // when...thenReturn -> Tells Mockito what to return when a method is called
        when(travellerRepository.addTraveller(any(Traveller.class))).thenReturn(dummyTraveller);

        TravellerDto result = travellerServiceImpl.addTraveller(dummyTravellerDto);

        assertNotNull(result);
        assertEquals("Sachin", result.getFirstName());
        // verify -> Ensures method was actually called (1 time)
        // verify(travellerRepository, times(1)).addTraveller(dummyTraveller);
    }
}
