package com.example.booking.trainticket.dto;

import java.time.LocalDate;

import com.example.booking.trainticket.model.BookingStatus;

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
public class TicketDto {
	private String pnr;		// Unique PNR for each ticket
    private BookingStatus bookingStatus;	// "CONFIRMED" or "WAITLISTED"
    private int travellerId;// Associated Traveller ID
    private String travellerName;
    private LocalDate departureDate;// Travel Date
    private String source;
    private String destination;
    private int trainId;	// Associated Train ID
    private int seatNumber;	// Seat Number (-1 if waitlisted)
    private double fare;	// Ticket Fare
}