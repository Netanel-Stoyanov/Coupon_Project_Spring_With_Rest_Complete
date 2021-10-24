package com.nikitacouponproject.exception.exceptionhandler;

import com.nikitacouponproject.exception.NotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class NotExistsHandler {
    @ExceptionHandler(NotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleNotExistsException(NotExistsException notExistsException) {
        ErrorDetails details = new ErrorDetails();
        details.setCode(HttpStatus.BAD_REQUEST.value());
        details.setMessage("not exists");
        return details;
    }
}
