package com.patterson.pruebatecnica.data.repositories;

import com.patterson.pruebatecnica.data.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    List<Subject> findByTeacherId(Integer idTeacher);
}
