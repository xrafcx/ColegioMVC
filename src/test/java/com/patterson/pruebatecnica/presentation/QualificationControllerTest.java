package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.service.QualificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TeacherController.class)
public class QualificationControllerTest {

    @Autowired
    private QualificationService qualificationService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createQualificationsTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findQualificationByIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findQualificationsTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findQualificationsByStudentIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void deleteQualificationByIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }
}