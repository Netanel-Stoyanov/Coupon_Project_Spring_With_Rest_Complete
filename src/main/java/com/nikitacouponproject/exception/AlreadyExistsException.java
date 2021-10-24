package com.nikitacouponproject.exception;

public class AlreadyExistsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExistsException(String enteredVariable) {
		super(enteredVariable + " already exists");
	}

	public AlreadyExistsException(String enteredVariable, String variableName) {
		super(enteredVariable + " already exists, please use a different " + variableName);
	}
}
