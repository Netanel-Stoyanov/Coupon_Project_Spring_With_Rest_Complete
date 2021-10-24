package com.nikitacouponproject.exception;

public class NoMoreCouponsException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoMoreCouponsException() {
		super("It seems there are no more of this coupon");
	}
	

}
