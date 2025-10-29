package com.patterson.pruebatecnica.data;

import com.patterson.pruebatecnica.data.entities.Student;
import com.patterson.pruebatecnica.data.repositories.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findByIdTest(){
        //Arrange
        var student = studentRepository.save(new Student());

        //Act
        var response = studentRepository.findById(student.getId());

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(student.getId(), response.get().getId());
    }

    @Test
    void updateTest(){
        //Arrange
        var student = new Student();
        student.setFirstName("Juan");
        var studentSave = studentRepository.save(student);

        //Act
        var studentOptional = studentRepository.findById(studentSave.getId());
        var student2 = studentOptional.get();
        student2.setFirstName("Juan2");
        var response = studentRepository.save(student2);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(student2.getFirstName(), response.getFirstName());
    }

    @Test
    void deleteTest(){
        //Arrange
        var student = studentRepository.save(new Student());

        //Act
        studentRepository.delete(student);
        var response = studentRepository.findById(student.getId());

        //Assert
        Assertions.assertTrue(response.isEmpty());
    }
}