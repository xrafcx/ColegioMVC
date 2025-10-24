package com.patterson.pruebatecnica.data.repositories;

import com.patterson.pruebatecnica.data.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

}
