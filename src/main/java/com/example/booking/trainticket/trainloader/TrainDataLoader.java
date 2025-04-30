package com.example.booking.trainticket.trainloader;

import com.example.booking.trainticket.model.Train;
import com.example.booking.trainticket.repository.TrainRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@AllArgsConstructor
public class TrainDataLoader implements CommandLineRunner {

	private final TrainRepository trainRepository;
	// Spring Boot auto-configures objectMapper.
	private final ObjectMapper objectMapper; 

	@Override
	public void run(String... args) throws Exception {

		// ClassPathResource - Spring utility class. It finds a file located
		// inside src/main/resources and returns the path.
		ClassPathResource resource = new ClassPathResource("trains.json");

		// InputStream -> Reads the content of the file.
		// resource.getInputStream() - opens the file for reading.
		try (InputStream inputStream = resource.getInputStream()) {
			// objectMapper.readValue(...) — reads the JSON data from the inputStream 
			// and converts it into a Java object.
			// new TypeReference<List<Train>>() {} -> deserializing json and returns a train list.
			List<Train> trains = objectMapper.readValue(inputStream, new TypeReference<List<Train>>() {});
			for (Train train : trains) {
				trainRepository.addTrain(train);
			}
		}
	}
}
