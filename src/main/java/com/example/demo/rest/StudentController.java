package com.example.demo.rest;

import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.model.Book;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.StudentRepositoryWithCriteriaApi;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class StudentController {

  private StudentRepository repository;
  private StudentRepositoryWithCriteriaApi studentRepositoryWithCriteriaApi;

  private static Comparator<Student> byName = new Comparator<Student>() {
    @Override
    public int compare(Student o1, Student o2) {
      return o1.getName().compareTo(o2.getName());
    }
  };

  private static Comparator<Student> byEmail = (a, b) -> a.getEmail().compareTo(b.getEmail());
  private static Comparator<Student> byEmailInReverseOrder = (a, b) -> b.getEmail().compareTo(a.getEmail());

  @Autowired
  public StudentController(StudentRepository repository,
      StudentRepositoryWithCriteriaApi studentRepositoryWithCriteriaApi) {
    this.repository = repository;
    this.studentRepositoryWithCriteriaApi = studentRepositoryWithCriteriaApi;
  }

  @PostMapping("create")
  public ResponseEntity<Student> create(@RequestBody Student request) {
    request.setCreated(ZonedDateTime.now());
    return ResponseEntity.ok(repository.save(request));
  }

  @GetMapping("all")
  public ResponseEntity<List<Student>> getAll(
      @RequestParam(required = false, defaultValue = "") Map<String, String> filtering) {
   // List<Student> students = repository.findAll();
    //List<Student> students = repository.findAll(Sort.by("name").ascending());
    List<Student> students = repository.findAll(Sort.by("name").ascending().and(Sort.by("email").descending()));
    //students.sort(byName);
    //students.sort(byEmail);
   // students.sort(byEmailInReverseOrder);

    // Collections.sort(students, Collections.reverseOrder());
    return ResponseEntity.ok(students);
  }

  @GetMapping("{studentId}")
  public ResponseEntity<Student> findOne(@PathVariable Long studentId) throws Exception {
    Student student = repository.findById(studentId)
        .orElseThrow(() -> new StudentNotFoundException("Student Not Found with Id = " + studentId));
    Thread.sleep(10000);
    return ResponseEntity.ok(student);
  }

  @GetMapping("student/{name}")
  public ResponseEntity<List<Student>> findByName(@PathVariable String name) {
    //return ResponseEntity.ok(repository.findStudentByName(name, Sort.by("studentId").descending()));
    return ResponseEntity.ok(studentRepositoryWithCriteriaApi.byName(name));
  }

  @GetMapping("student/book/{title}")
  public ResponseEntity<List<Student>> findByBookTitle(@PathVariable String title) {
    //return ResponseEntity.ok(repository.findStudentByBookTitle(title));
    return ResponseEntity.ok(repository.findStudentByBooks_Title(title));
  }

  @GetMapping("student/book")
  public ResponseEntity<List<Student>> findByBook(@RequestBody Book book) {
    return ResponseEntity
        .ok(repository.findStudentByBooks_Title_AndBooks_Author(book.getTitle(), book.getAuthor()));
  }

  @DeleteMapping("{studentId}")
  public ResponseEntity<Void> delete(@PathVariable Long studentId) {
    System.out.println("********** Delete for Student = " + studentId);
    //repository.findById(studentId).ifPresent(student -> repository.delete(student));
    repository.findById(studentId).ifPresent(repository::delete);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

}
