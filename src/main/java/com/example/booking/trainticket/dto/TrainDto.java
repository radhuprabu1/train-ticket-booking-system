package com.example.booking.trainticket.dto;

import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a Train entity.
 */
@ToString
@Getter
@Setter
@AllArgsConstructor
public class TrainDto {

    private Long trainId;
    private String trainName;
    private String source;
    private String destination;
    private Map<LocalDate, Long> availableSeatsByDate; // Key: Date, Value: Available Seats
    private Map<LocalDate, Double> ticketPriceByDate;      // Key: Date, Value: Ticket Price

}
