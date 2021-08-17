package com.uxpsystems.assignment.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(Exception e) {
		Map<String, String> errorMessage = new HashMap<>();
		errorMessage.put("timeStamp", String.valueOf(new Date(System.currentTimeMillis())));
		errorMessage.put("status", "404");
		errorMessage.put("message", e.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserExistException.class)
	public ResponseEntity<Object> handleUserExistException(Exception e) {
		Map<String, String> errorMessage = new HashMap<>();
		errorMessage.put("timeStamp", String.valueOf(new Date(System.currentTimeMillis())));
		errorMessage.put("status", "400");
		errorMessage.put("message", e.getMessage());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
}
