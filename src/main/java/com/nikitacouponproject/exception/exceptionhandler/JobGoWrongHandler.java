package com.nikitacouponproject.exception.exceptionhandler;

import com.nikitacouponproject.exception.JobGoWrongException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class JobGoWrongHandler {
    @ExceptionHandler(JobGoWrongException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorDetails handleJobGoWrongException(JobGoWrongException jobGoWrongException) {
        ErrorDetails details = new ErrorDetails();
        details.setCode(HttpStatus.BAD_GATEWAY.value());
        details.setMessage("daily job go wrong");
        return details;
    }
}
