package com.example.booking.trainticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.booking.trainticket.dto.TicketDto;
import com.example.booking.trainticket.model.BookingStatus;
import com.example.booking.trainticket.model.Ticket;
import com.example.booking.trainticket.model.Train;
import com.example.booking.trainticket.model.Traveller;
import com.example.booking.trainticket.repository.TicketRepository;
import com.example.booking.trainticket.repository.TrainRepository;
import com.example.booking.trainticket.repository.TravellerRepository;
import com.example.booking.trainticket.service.impl.BookingServiceImpl;

class BookingServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private TravellerRepository travellerRepository;

    @InjectMocks
    private BookingServiceImpl bookingServiceImpl;

    private Train dummyTrain;
    private Traveller dummyTraveller;
    private Ticket dummyTicket;
    private LocalDate departureDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        departureDate = LocalDate.now();

        dummyTrain = createDummyTrain();
        dummyTraveller = createDummyTraveller();
        dummyTicket = createDummyTicket();
    }

    @Test
    void testBookTicket_ConfirmedBooking() {
        // Arrange (Set up mocks)
        when(trainRepository.findTrainById(dummyTrain.getTrainId())).thenReturn(Optional.of(dummyTrain));
        when(travellerRepository.findTravellerById(dummyTraveller.getTravellerId())).thenReturn(Optional.of(dummyTraveller));
        when(ticketRepository.addBooking(dummyTrain, dummyTraveller, departureDate)).thenReturn(dummyTicket);

        // Act
        TicketDto bookedTicket = bookingServiceImpl.bookTicket(dummyTrain.getTrainId(), dummyTraveller.getTravellerId(), departureDate);

        // Assert
        assertNotNull(bookedTicket);
        assertEquals(BookingStatus.CONFIRMED, bookedTicket.getBookingStatus());
        verify(trainRepository, times(1)).findTrainById(dummyTrain.getTrainId());
        verify(travellerRepository, times(1)).findTravellerById(dummyTraveller.getTravellerId());
        verify(ticketRepository, times(1)).addBooking(dummyTrain, dummyTraveller, departureDate);
        verify(trainRepository, times(1)).saveTrain(dummyTrain);
    }

    // Later, second test for cancelTicket will come here...

    // Helper methods
    private Train createDummyTrain() {
        Map<LocalDate, Integer> seats = new HashMap<>();
        seats.put(departureDate, 10);
        Map<LocalDate, Double> prices = new HashMap<>();
        prices.put(departureDate, 500.0);

        return new Train(1, "Shatabdi Express", "Chennai", "Bengaluru", seats, prices);
    }

    private Traveller createDummyTraveller() {
        return new Traveller(1, "Sachin", "Tendulkar", "1234567890");
    }

    private Ticket createDummyTicket() {
        Ticket ticket = new Ticket();
        ticket.setBookingStatus(BookingStatus.CONFIRMED);
        ticket.setSeatNumber(1);
        ticket.setDepartureDate(departureDate);
        ticket.setFare(500.0);
        ticket.setPnr(UUID.randomUUID().toString());
        ticket.setTrainId(dummyTrain.getTrainId());
        ticket.setTravellerId(dummyTraveller.getTravellerId());
        ticket.setTravellerName(dummyTraveller.getFirstName() + " " + dummyTraveller.getLastName());
        ticket.setSource(dummyTrain.getSource());
        ticket.setDestination(dummyTrain.getDestination());
        return ticket;
    }
    
    @Test
    void testCancelTicket_WithWaitlistProcessing() {
        // Arrange (Mocks)
        when(ticketRepository.findTicketByPnr(dummyTicket.getPnr()))
            .thenReturn(Optional.of(dummyTicket));
        when(trainRepository.findTrainById(dummyTicket.getTrainId()))
            .thenReturn(Optional.of(dummyTrain));
        when(travellerRepository.findTravellerById(dummyTicket.getTravellerId()))
            .thenReturn(Optional.of(dummyTraveller));
        when(ticketRepository.isWaitlistEmpty())
            .thenReturn(false);

        Ticket waitlistedTicket = createWaitlistedTicket();
        when(ticketRepository.pollWaitlist())
            .thenReturn(waitlistedTicket);

        // Act
        bookingServiceImpl.cancelTicket(dummyTicket.getPnr());

        // Assert
        verify(ticketRepository, times(1)).deleteTicket(dummyTicket.getPnr());
        verify(ticketRepository, times(1)).pollWaitlist();
        verify(ticketRepository, times(1)).saveTicket(waitlistedTicket);
        verify(trainRepository, atLeastOnce()).saveTrain(dummyTrain);

        assertEquals(BookingStatus.CONFIRMED, waitlistedTicket.getBookingStatus());
       // assertEquals(dummyTrain.getAvailableSeatsByDate().get(departureDate), Integer.valueOf(10));
    }

    @Test
    void testCancelTicket_NoWaitlist() {
        // Arrange
        when(ticketRepository.findTicketByPnr(dummyTicket.getPnr()))
            .thenReturn(Optional.of(dummyTicket));
        when(trainRepository.findTrainById(dummyTicket.getTrainId()))
            .thenReturn(Optional.of(dummyTrain));
        when(travellerRepository.findTravellerById(dummyTicket.getTravellerId()))
            .thenReturn(Optional.of(dummyTraveller));
        when(ticketRepository.isWaitlistEmpty())
            .thenReturn(true);

        // Act
        bookingServiceImpl.cancelTicket(dummyTicket.getPnr());

        // Assert
        verify(ticketRepository, times(1)).deleteTicket(dummyTicket.getPnr());
        verify(ticketRepository, never()).pollWaitlist();
        verify(ticketRepository, never()).saveTicket(any());
        verify(trainRepository, atLeastOnce()).saveTrain(dummyTrain);
    }

    private Ticket createWaitlistedTicket() {
        Ticket ticket = new Ticket();
        ticket.setBookingStatus(BookingStatus.WAITLISTED);
        ticket.setSeatNumber(0);
        ticket.setDepartureDate(departureDate);
        ticket.setFare(500.0);
        ticket.setPnr(UUID.randomUUID().toString());
        ticket.setTrainId(dummyTrain.getTrainId());
        ticket.setTravellerId(dummyTraveller.getTravellerId());
        ticket.setTravellerName(dummyTraveller.getFirstName() + " " + dummyTraveller.getLastName());
        ticket.setSource(dummyTrain.getSource());
        ticket.setDestination(dummyTrain.getDestination());
        return ticket;
    }

}
