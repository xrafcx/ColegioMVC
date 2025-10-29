package com.patterson.pruebatecnica.data;

import com.patterson.pruebatecnica.data.entities.Subject;
import com.patterson.pruebatecnica.data.entities.Teacher;
import com.patterson.pruebatecnica.data.repositories.SubjectRepository;
import com.patterson.pruebatecnica.data.repositories.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void findByIdTest(){
        //Arrange
        var subject = subjectRepository.save(new Subject());

        //Act
        var response = subjectRepository.findById(subject.getId());

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(subject.getId(), response.get().getId());
    }

    @Test
    void updateTest(){
        //Arrange
        var subject = new Subject();
        subject.setName("Maths");
        var subjectSave = subjectRepository.save(subject);

        //Act
        var subjectOptional = subjectRepository.findById(subjectSave.getId());
        var subject2 = subjectOptional.get();
        subject2.setName("English");
        var response = subjectRepository.save(subject2);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(subject2.getName(), response.getName());
    }

    @Test
    void deleteTest(){
        //Arrange
        var subject = subjectRepository.save(new Subject());

        //Act
        subjectRepository.delete(subject);
        var response = subjectRepository.findById(subject.getId());

        //Assert
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void findByTeacherIdTest(){
        //Arrange
        var teacher = teacherRepository.save(new Teacher());

        var subject1 = new Subject();
        subject1.setTeacher(teacher);
        subjectRepository.save(subject1);
        var subject2 = new Subject();
        subject2.setTeacher(teacher);
        subjectRepository.save(subject2);

        //Act
        List<Subject> response = subjectRepository.findByTeacherId(teacher.getId());

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(2, response.size());
    }

//    @Test
//    void findQualificationByTeacherIdTest(){
//        //Arrange
//
//
//        //Act
//
//
//        //Assert
//
//    }
}