package com.microservices.auth.exception;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> procesarResourceNotFound(ResourceNotFoundException resourceNotFoundException) {
        logger.error(resourceNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resourceNotFoundException.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> procesarBadRequest(BadRequestException badRequestException){
        logger.error(badRequestException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> allErrors(Exception exception, WebRequest webRequest){
        logger.error(exception.getMessage());
        return new ResponseEntity("Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
