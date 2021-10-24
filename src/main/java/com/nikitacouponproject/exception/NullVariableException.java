package com.nikitacouponproject.exception;

public class NullVariableException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullVariableException() {
		super();
	}

	public NullVariableException(String variableName) {
		super("You must enter a valid "+variableName);
	}
}
