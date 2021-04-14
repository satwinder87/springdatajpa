package com.example.demo;

import com.example.demo.functions.PrintFunction;
import com.example.demo.functions.UpperCaseFunction;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringDataJPAApplicationTests {

  private static final Predicate<String> nameStartsWithM = name -> name.startsWith("m");

  @Test
  void contextLoads() {
  }

  @Test
  public void testPredicates() {

    UpperCaseFunction upperCaseFunction = new UpperCaseFunction();
    Function<String, String> upperCase = name -> name.toUpperCase();

    BiFunction<Integer, Integer, Integer> addFunction = (a, b) -> a + b;
    BiFunction<Integer, Integer, Integer> multiplyFunction = (a, b) -> a * b;

    List<String> names = List.of("ram", "john", "mike", "mikael");
    List<String> startsWithM = names.stream().filter(nameStartsWithM).collect(Collectors.toList());
    Assertions.assertEquals(2, startsWithM.size());
    names.stream().map(upperCaseFunction).forEach(new PrintFunction());
    names.stream().map(String::toUpperCase).forEach(new PrintFunction());
    names.stream().map(name -> compute(name, upperCase)).forEach(new PrintFunction());

    System.out.println(compute(5, 10, addFunction));
    System.out.println(compute(5, 10, multiplyFunction));

  }

  private int compute(int a, int b, BiFunction<Integer, Integer, Integer> function) {
    return function.apply(a, b);
  }

  private String compute(String input, Function<String, String> function) {
    return function.apply(input);
  }
}
