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
    private Long trainId;	// Associated Train ID
    private Long travellerId;// Associated Traveller ID
    private LocalDate departureDate;// Travel Date
    private BookingStatus bookingStatus;	// "CONFIRMED" or "WAITLISTED"
    private Long seatNumber;	// Seat Number (-1 if waitlisted)
    private String travellerName;
    private String source;
    private String destination;
    private double fare;	// Ticket Fare
}