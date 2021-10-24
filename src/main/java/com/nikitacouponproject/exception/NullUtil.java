package com.nikitacouponproject.exception;

public class NullUtil {
	public static void check(Object o) throws NullVariableException {
		if(o==null) {
			throw new NullVariableException();
		}
	}
	
	public static void check(Object o, String name) throws NullVariableException {
		if(o==null) {
			throw new NullVariableException(name);
		}
	}
	
}
