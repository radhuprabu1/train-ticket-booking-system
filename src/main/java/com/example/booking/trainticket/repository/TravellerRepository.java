package com.example.booking.trainticket.repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.example.booking.trainticket.model.Traveller;

@Repository
public class TravellerRepository {
	
	// HashSet - to avoid duplicate traveller entries
	private Set<Traveller> travellers = new HashSet<>();
	
	// ID tracker for assigning unique travellerId
    private Long nextTravellerId = 101L;

	// Add new traveller to the traveller set if not present.
	public Traveller addTraveller(Traveller traveller) {
		if (traveller.getTravellerId() == null) {
            traveller.setTravellerId(nextTravellerId++); // Assign and increment ID
        }
		if(!travellers.add(traveller)) {
			return null;
		}
		return traveller;
	}
	
	// Find traveller by traveller id.
	public Optional<Traveller> findTravellerById(Long travellerId) {
		return travellers.stream()
				.filter(t -> t.getTravellerId().equals(travellerId))
				.findFirst();
	}
	
	// Remove traveller if same traveller id.
	public void removeTraveller(Traveller traveller) {
	    travellers.removeIf(t -> t.getTravellerId().equals(traveller.getTravellerId()));
	}
	
	// For internal testing purposes
	public Set<Traveller> getAllTravellers() {
		return travellers;
	}

}
