package com.nikitacouponproject.exception;

public class CouponAlreadyPurchasedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponAlreadyPurchasedException() {
		super("Coupon was purchased before, you cannot buy the same coupon twice");
	}

	public CouponAlreadyPurchasedException(String couponTitle) {
		super("Coupon '" + couponTitle + "' was purchased before, you cannot buy the same coupon twice");
	}

}
