package com.uxpsystems.assignment.exception;

public class UserExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserExistException() {
	}

	public UserExistException(String message) {
		super(message);
	}

}
