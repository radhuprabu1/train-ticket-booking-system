package com.example.booking.trainticket.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PnrGenerator {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final Random RANDOM = new Random();
	private static final Set<String> generatedPnrs = new HashSet<>();

	// Private constructor to prevent instantiation
	private PnrGenerator() {
		throw new UnsupportedOperationException("PnrGenerator is a utility class and cannot be instantiated.");
	}


	public static String generatePnr() {
		String pnr;
		do {
			pnr = generateRandomAlphanumeric(10); // Generate 10-character alphanumeric PNR
		} while (!generatedPnrs.add(pnr)); // Ensure uniqueness

		return pnr;
	}

	private static String generateRandomAlphanumeric(int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		return sb.toString();
	}
}