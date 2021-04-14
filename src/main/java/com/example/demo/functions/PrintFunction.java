package com.example.demo.functions;

import java.util.function.Consumer;

public class PrintFunction implements Consumer<String> {

  @Override
  public void accept(String s) {
    System.out.println(s);
  }
}
