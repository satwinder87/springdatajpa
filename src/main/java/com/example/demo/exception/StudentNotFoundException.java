package com.example.demo.exception;

import lombok.Getter;

@Getter
public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(String message) {
    super(message);
  }
}
