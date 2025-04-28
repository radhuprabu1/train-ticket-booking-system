package com.example.booking.trainticket.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket {
	private String pnr;		// Unique PNR for each ticket
    private int trainId;	// Associated Train ID
    private int travellerId;// Associated Traveller ID
    private LocalDate departureDate;// Travel Date
    private String bookingStatus;	// "CONFIRMED" or "WAITLISTED"
    private int seatNumber;	// Seat Number (-1 if waitlisted)
    private double fare;	// Ticket Fare
}