package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SubjectController.class)
public class SubjectControllerTest {

    @Autowired
    private SubjectService subjectService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createSubjectsTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findSubjectByIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findSubjectsTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findSubjectsByIdTeacherTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void deleteSubjectByIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }
}