package com.example.booking.trainticket.exception;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

	private LocalDateTime timestamp;

	private String message;

	private String path;

	private String errorCode;
}