package com.nikitacouponproject.exception;

public class NotExistsException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotExistsException(String object) {
        super(object + " is not exists");
    }
}
