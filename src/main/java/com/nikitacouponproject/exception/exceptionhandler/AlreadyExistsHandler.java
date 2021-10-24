package com.nikitacouponproject.exception.exceptionhandler;

import com.nikitacouponproject.exception.AlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class AlreadyExistsHandler {
    @ExceptionHandler(AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleAreadyExistsException(AlreadyExistsException alreadyExistsException) {
        ErrorDetails details = new ErrorDetails();
        details.setCode(HttpStatus.BAD_REQUEST.value());
        details.setMessage("Already exists");
        return details;
    }
}
