package com.nikitacouponproject.exception;

public class CouponExpiredException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponExpiredException() {
		super("Coupon expired");
	}

}
