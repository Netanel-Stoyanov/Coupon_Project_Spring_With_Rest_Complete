package com.nikitacouponproject.exception.exceptionhandler;

import com.nikitacouponproject.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class LoginHandller {
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDetails handleCouponExpiredException(LoginException loginException) {
        ErrorDetails details = new ErrorDetails();
        details.setCode(HttpStatus.FORBIDDEN.value());
        details.setMessage(loginException.getMessage());
        return details;
    }
}
