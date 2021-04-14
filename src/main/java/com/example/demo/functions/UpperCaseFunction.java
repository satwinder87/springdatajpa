package com.example.demo.functions;

import java.util.function.Function;

public class UpperCaseFunction implements Function<String, String> {

  @Override
  public String apply(String s) {
    return s.toUpperCase();
  }
}
