package com.patterson.pruebatecnica.data;

import com.patterson.pruebatecnica.data.entities.Qualification;
import com.patterson.pruebatecnica.data.entities.Student;
import com.patterson.pruebatecnica.data.repositories.QualificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QualificationRepositoryTest {

    @Autowired
    private QualificationRepository qualificationRepository;

    @Test
    void findByIdTest(){
        //Arrange
        var qualification = qualificationRepository.save(new Qualification());

        //Act
        var response = qualificationRepository.findById(qualification.getId());

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(qualification.getId(), response.get().getId());
    }

    @Test
    void updateTest(){
        //Arrange
        var qualification = new Qualification();
        qualification.setNote(5.0);
        var qualificationSave = qualificationRepository.save(qualification);

        //Act
        var qualificationOptional = qualificationRepository.findById(qualification.getId());
        var qualification2 = qualificationOptional.get();
        qualification2.setNote(5.0);
        var response = qualificationRepository.save(qualification2);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(qualification2.getNote(), response.getNote());
    }

    @Test
    void deleteTest(){
        //Arrange
        var qualification = qualificationRepository.save(new Qualification());

        //Act
        qualificationRepository.delete(qualification);
        var response = qualificationRepository.findById(qualification.getId());

        //Assert
        Assertions.assertTrue(response.isEmpty());
    }

    @Test
    void findByStudentIdTest(){
        //Arrange
        var studentA = new Student();

        //Act


        //Assert

    }
}