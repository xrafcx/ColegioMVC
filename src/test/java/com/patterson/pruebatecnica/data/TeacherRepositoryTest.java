package com.patterson.pruebatecnica.data;

import com.patterson.pruebatecnica.data.entities.Teacher;
import com.patterson.pruebatecnica.data.repositories.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void findByIdTest(){
        //Arrange
        var teacher = teacherRepository.save(new Teacher());

        //Act
        var response = teacherRepository.findById(teacher.getId());

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(teacher.getId(), response.get().getId());
    }

    @Test
    void updateTest(){
        //Arrange
        var teacher = new Teacher();
        teacher.setFirstName("Juan");
        var teacherSave = teacherRepository.save(teacher);

        //Act
        var teacherOptional = teacherRepository.findById(teacherSave.getId());
        var teacher2 = teacherOptional.get();
        teacher2.setFirstName("Martin");
        var response = teacherRepository.save(teacher2);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(teacher2.getFirstName(), response.getFirstName());
    }

    @Test
    void deleteTest(){
        //Arrange
        var teacher = teacherRepository.save(new Teacher());

        //Act
        teacherRepository.delete(teacher);
        var response = teacherRepository.findById(teacher.getId());

        //Assert
        Assertions.assertTrue(response.isEmpty());
    }
}