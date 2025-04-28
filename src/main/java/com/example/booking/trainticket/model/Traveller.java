package com.example.booking.trainticket.model;

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
public class Traveller {
	
	private int travellerId;
	private String firstName;
	private String lastName;
	private String contactNumber;
}
