package com.example.booking.trainticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a traveller entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TravellerDto {
	
	private int travellerId;
	private String firstName;
	private String lastName;
	private String contactNumber;
}
