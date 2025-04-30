package com.example.booking.trainticket.model;

import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a Train entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Train {

    private Long trainId;
    private String trainName;
    private String source;
    private String destination;
    private Map<LocalDate, Long> availableSeatsByDate; // Key: Date, Value: Available Seats
    private Map<LocalDate, Double> ticketPriceByDate;      // Key: Date, Value: Ticket Price

}
