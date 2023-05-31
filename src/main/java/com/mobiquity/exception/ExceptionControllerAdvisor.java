package com.mobiquity.exception;

import com.mobiquity.dto.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This is the Controller Advisor which will catch the exceptions and will return as a response body with error message
 * */

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorInfo> handleValidationException(APIException exception) {
        log.error("Validation exception occurred with errors: {}", exception.getMessage());
        return new ResponseEntity<>(new ErrorInfo(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleNotFoundException(NotFoundException exception) {
        log.error("Exception occurred with errors: {}", exception.getMsg());
        return new ResponseEntity<>(new ErrorInfo(exception.getCode(), exception.getMsg()), HttpStatus.NOT_FOUND);
    }
}
