package com.example.booking.trainticket.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.booking.trainticket.dto.TicketDto;
import com.example.booking.trainticket.dto.TrainDto;
import com.example.booking.trainticket.dto.TravellerDto;
import com.example.booking.trainticket.model.BookingStatus;
import com.example.booking.trainticket.service.BookingService;
import com.example.booking.trainticket.service.TrainService;
import com.example.booking.trainticket.service.TravellerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private TrainService trainService;

    @MockBean
    private TravellerService travellerService;

    @Autowired
    private ObjectMapper objectMapper;

    private TrainDto sampleTrain;
    private TicketDto sampleTicket;
    private TravellerDto sampleTraveller;

    @BeforeEach
    void setUp() {
        sampleTrain = createSampleTrainDto();
        sampleTicket = createSampleTicketDto();
        sampleTraveller = createSampleTravellerDto();
    }
    
    @Test
    void testRetrieveTrains() throws Exception {
        Mockito.when(trainService.searchTrains(any(), any(), any())).thenReturn(List.of(sampleTrain));

        mockMvc.perform(get("/api/trains/search")
                        .param("source", "Chennai")
                        .param("destination", "Mumbai")
                        .param("departureDate", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].source").value("Chennai"));
    }

    @Test
    void testAddJourney() throws Exception {
        Mockito.when(trainService.fetchTrain(eq(1L), any())).thenReturn(sampleTrain);

        mockMvc.perform(post("/api/journey/1/{DepartureDate}", LocalDate.now()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainId").value(1L));
    }

    @Test
    void testAddTraveller() throws Exception {
        Mockito.when(travellerService.addTraveller(any())).thenReturn(sampleTraveller);

        mockMvc.perform(post("/api/travellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTraveller)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Virat"));
    }

    @Test
    void testBookTicket() throws Exception {
        Mockito.when(bookingService.bookTicket(eq(1L), eq(101L), any())).thenReturn(sampleTicket);

        mockMvc.perform(post("/api/tickets/1/101/{departureDate}", LocalDate.now()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.pnr").value("PNR1234"));
    }

    @Test
    void testCancelTicket() throws Exception {
        Mockito.doNothing().when(bookingService).cancelTicket("PNR1234");

        mockMvc.perform(delete("/api/cancel/PNR1234"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket Cancelled Successfully"));
    }

    @Test
    void testRetrieveTicket() throws Exception {
        Mockito.when(bookingService.retrieveTicket("PNR1234")).thenReturn(sampleTicket);

        mockMvc.perform(get("/api/ticket/PNR1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.travellerName").value("Virat Kohli"));
    }

    @Test
    void testAddTraveller_BadRequest() throws Exception {
        TravellerDto invalidTraveller = new TravellerDto(); // Missing required fields

        mockMvc.perform(post("/api/travellers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTraveller)))
                .andExpect(status().isBadRequest());
    }
    
    private TrainDto createSampleTrainDto() {
        Map<LocalDate, Long> seats = new HashMap<>();
        seats.put(LocalDate.now(), 10L);

        Map<LocalDate, Double> fares = new HashMap<>();
        fares.put(LocalDate.now(), 1200.0);

        return new TrainDto(1L, "Chennai Express", "Chennai", "Mumbai", seats, fares);
    }

    private TicketDto createSampleTicketDto() {
        return new TicketDto(
                "PNR1234",
                BookingStatus.CONFIRMED,
                1L,
                "Virat Kohli",
                LocalDate.now(),
                "Chennai",
                "Mumbai",
                101L,
                5L,
                1200.0
        );
    }

    private TravellerDto createSampleTravellerDto() {
        return new TravellerDto(101L, "Virat", "Kohli", "virat.kohli@example.com");
    }


}