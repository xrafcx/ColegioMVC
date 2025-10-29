package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createTeachersTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findTeacherByIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findTeachersTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findQualificationsByTeacherIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void deleteTeacherByIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }
}