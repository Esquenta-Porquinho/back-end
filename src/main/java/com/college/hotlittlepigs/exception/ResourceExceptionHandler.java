package com.college.hotlittlepigs.exception;

import com.college.hotlittlepigs.exception.common.Error;
import com.college.hotlittlepigs.exception.common.JwtException;
import com.college.hotlittlepigs.exception.common.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Error> handleNotFoundException(NotFoundException ex) {
    Error error = new Error(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new Date(), null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<Error> handleJwtException(JwtException ex) {
    Error error = new Error(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), new Date(), null);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
