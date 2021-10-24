package com.nikitacouponproject.exception;

public class ExceptionUtil {
	public static void print(Exception e) {
		if(e.getMessage() == null) {
			String name = e.getClass().getSimpleName();
			name = name.substring(0, name.length()-9);
			name = name.replaceAll("(.)([A-Z])", "$1 $2");
			System.out.println(name);
		}else {
			System.out.println(e.getMessage());
		}
	}
}
