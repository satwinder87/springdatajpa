package com.example.demo.repository;

import com.example.demo.model.Student;
import com.example.demo.model.Student_;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Component;

@Component
public class StudentRepositoryWithCriteriaApi {

  @PersistenceContext
  private EntityManager entityManager;

  public List<Student> byName(final String name) {

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
    Root<Student> root = query.from(Student.class);

    Predicate hasName = criteriaBuilder.equal(root.get(Student_.NAME), name);

    query.where(hasName);

    return entityManager.createQuery(query.select(root)).getResultList();

  }

}
