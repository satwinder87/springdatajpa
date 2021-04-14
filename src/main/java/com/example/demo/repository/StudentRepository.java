package com.example.demo.repository;

import com.example.demo.model.Student;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

  List<Student> findStudentByName(String name);

  List<Student> findStudentByName(String name, Sort sort);

  @Query("select s from Student s "
      + "join s.books b "
      + "where"
      + " b.title = :title ")
  List<Student> findStudentByBookTitle(String title);

  List<Student> findStudentByBooks_Title_AndBooks_Author(String title, String author);

  List<Student> findStudentByBooks_Title(String title);


}
