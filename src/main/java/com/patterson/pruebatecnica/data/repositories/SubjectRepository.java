package com.patterson.pruebatecnica.data.repositories;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.data.entities.Qualification;
import com.patterson.pruebatecnica.data.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {

    List<Subject> findByTeacherId(Integer idTeacher);

    @Query("""
        SELECT q 
        FROM Qualification q JOIN q.subject s
            WHERE s.teacher.id = :idTeacher
    """)
    List<QualificationDTO> findQualificationsByTeacherId(@Param("idTeacher") Integer idTeacher);
}