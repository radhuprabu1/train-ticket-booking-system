package com.example.booking.trainticket.dto;

import java.time.LocalDate;
import java.util.Map;

import lombok.ToString;

/**
 * Represents a Train entity.
 */
@ToString
public class TrainDto {

    private int trainId;
    private String trainName;
    private String source;
    private String destination;
    private Map<LocalDate, Integer> availableSeatsByDate; // Key: Date, Value: Available Seats
    private Map<LocalDate, Double> ticketPriceByDate;      // Key: Date, Value: Ticket Price

    // Constructors
    public TrainDto() {}

    public TrainDto(int trainId, String trainName, String source, String destination,
                 Map<LocalDate, Integer> availableSeatsByDate, Map<LocalDate, Double> ticketPriceByDate) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.availableSeatsByDate = availableSeatsByDate;
        this.ticketPriceByDate = ticketPriceByDate;
    }

    // Getters and Setters
    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Map<LocalDate, Integer> getAvailableSeatsByDate() {
        return availableSeatsByDate;
    }

    public void setAvailableSeatsByDate(Map<LocalDate, Integer> availableSeatsByDate) {
        this.availableSeatsByDate = availableSeatsByDate;
    }

    public Map<LocalDate, Double> getTicketPriceByDate() {
        return ticketPriceByDate;
    }

    public void setTicketPriceByDate(Map<LocalDate, Double> ticketPriceByDate) {
        this.ticketPriceByDate = ticketPriceByDate;
    }
}
