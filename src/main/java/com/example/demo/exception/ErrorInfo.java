package com.example.demo.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorInfo {

  private String errorMessage;
}
