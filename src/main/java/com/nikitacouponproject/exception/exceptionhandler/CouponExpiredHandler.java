package com.nikitacouponproject.exception.exceptionhandler;

import com.nikitacouponproject.exception.CouponExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CouponExpiredHandler {
    @ExceptionHandler(CouponExpiredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleCouponExpiredException(CouponExpiredException couponExpiredException) {
        ErrorDetails details = new ErrorDetails();
        details.setCode(HttpStatus.BAD_REQUEST.value());
        details.setMessage("coupon expired");
        return details;
    }
}
