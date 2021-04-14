package com.example.demo.advice;

import com.example.demo.exception.ErrorInfo;
import com.example.demo.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerErrorHandler {

  @ExceptionHandler(StudentNotFoundException.class)
  public ResponseEntity<ErrorInfo> handle(StudentNotFoundException exception) {
    ErrorInfo errorInfo = ErrorInfo.builder().errorMessage(exception.getMessage()).build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
  }

}
