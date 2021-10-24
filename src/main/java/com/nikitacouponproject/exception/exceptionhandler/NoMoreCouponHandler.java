package com.nikitacouponproject.exception.exceptionhandler;

import com.nikitacouponproject.exception.NoMoreCouponsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class NoMoreCouponHandler {
    @ExceptionHandler(NoMoreCouponsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleNoMoreCouponException(NoMoreCouponsException noMoreCouponsException) {
        ErrorDetails details = new ErrorDetails();
        details.setCode(HttpStatus.BAD_REQUEST.value());
        details.setMessage("no more coupons");
        return details;
    }
}
