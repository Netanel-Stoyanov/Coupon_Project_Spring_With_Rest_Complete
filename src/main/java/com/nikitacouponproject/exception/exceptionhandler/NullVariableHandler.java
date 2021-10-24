package com.nikitacouponproject.exception.exceptionhandler;

import com.nikitacouponproject.exception.NullVariableException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class NullVariableHandler {
    @ExceptionHandler(NullVariableException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorDetails handleNullVariableException(NullVariableException nullVariableException) {
        ErrorDetails details = new ErrorDetails();
        details.setCode(HttpStatus.BAD_GATEWAY.value());
        details.setMessage("Null Variable");
        return details;
    }
}
