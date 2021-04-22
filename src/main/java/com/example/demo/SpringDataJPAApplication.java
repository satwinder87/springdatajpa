package com.example.demo;

import com.example.demo.model.Book;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class SpringDataJPAApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDataJPAApplication.class, args);
  }

  @Bean
  public CommandLineRunner init(StudentRepository repository) {
    log.info("Application Starting, Adding some data to the repository");
    return args -> {
      repository.save(Student.builder()
          .name("satwinder")
          .email("sat@gmail.com")
          .created(ZonedDateTime.now())
          .books(List.of(Book.builder()
              .author("ravichandran")
              .title("java")
              .build()))
          .build());
      repository.save(Student.builder()
          .name("john")
          .email("john@gmail.com")
          .created(ZonedDateTime.now())
          .books(List.of(Book.builder()
              .author("richard")
              .title("oracle")
              .build()))
          .build());
      repository.save(Student.builder()
          .name("sam")
          .email("sam@gmail.com")
          .created(ZonedDateTime.now())
          .books(List.of(Book.builder()
              .author("richard")
              .title("mongoDB")
              .build()))
          .build());
    };
  }

}
