package com.patterson.pruebatecnica.data.repositories;

import com.patterson.pruebatecnica.data.entities.Qualification;
import com.patterson.pruebatecnica.data.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Integer> {

//    List<Qualification> findBySubjectIdIn(List<Integer> idSubjects);
//    List<Qualification> findByStudentId(Integer IdStudent);
}
