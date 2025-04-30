package com.example.booking.trainticket.dto;

import jakarta.validation.constraints.NotBlank;
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
	
	private Long travellerId;
    
	@NotBlank(message = "First name is required")
	private String firstName;
    
    @NotBlank(message = "Last name is required")
	private String lastName;
    
    @NotBlank(message = "Contact Number is required")
	private String contactNumber;
}
