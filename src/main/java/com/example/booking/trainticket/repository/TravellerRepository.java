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
	
	// Add new traveller to the traveller set if not present.
	public Traveller addTraveller(Traveller traveller) {
		if(!travellers.add(traveller)) {
			return null; //throw manual exception here
		}
		return traveller;
	}
	
	// Find traveller by traveller id.
	public Optional<Traveller> findTravellerById(int travellerId) {
		return travellers.stream()
				.filter(t -> t.getTravellerId() == travellerId)
				.findFirst();
	}
	
	public void removeTraveller(Traveller traveller) {
	    travellers.removeIf(t -> t.getTravellerId() == traveller.getTravellerId());
	}
	
	// For internal testing purposes
	public Set<Traveller> getAllTravellers() {
		return travellers;
	}

}
