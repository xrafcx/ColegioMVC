package com.patterson.pruebatecnica.data.repositories;

import com.patterson.pruebatecnica.data.entities.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Integer> {

    List<Qualification> findByStudentId(Integer idStudent);
}