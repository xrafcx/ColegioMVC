package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private StudentService studentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createStudentsTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findStudentByIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void findStudentsTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }

    @Test
    void deleteStudentByIdTest() throws Exception {
        //Arrange


        //Act


        //Assert

    }
}
