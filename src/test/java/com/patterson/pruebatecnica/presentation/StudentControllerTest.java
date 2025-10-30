package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.StudentDTO;
import com.patterson.pruebatecnica.business.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

    @MockitoBean
    private StudentService studentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createStudentsTest() throws Exception {
        //Arrange
        var requestList = List.of(new StudentDTO(), new StudentDTO());
        var responseList = List.of(new StudentDTO(), new StudentDTO());
        when(studentService.createStudents(anyList())).thenReturn(responseList);

        //Act
        ResultActions response = mockMvc.perform(post("/students/batch").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper()
                        .findAndRegisterModules()
                        .writeValueAsString(requestList)));

        //Assert
        response.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void findStudentByIdTest() throws Exception {
        //Arrange
        Integer id = 3;
        StudentDTO studentDTO = new StudentDTO();
        when(studentService.findStudentById(id)).thenReturn(studentDTO);

        //Act
        ResultActions response = mockMvc.perform(get("/students/findById").param("id", id.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(studentService, times(1)).findStudentById(id);
    }

    @Test
    void findStudentsTest() throws Exception {
        //Arrange
        var lista = List.of(new StudentDTO(), new StudentDTO());
        when(studentService.findAll()).thenReturn(lista);

        //Act
        ResultActions response = mockMvc.perform(get("/students/findStudents"));

        //Assert
        response.andExpect(status().isOk());
        verify(studentService, times(1)).findAll();
    }

    @Test
    void deleteStudentByIdTest() throws Exception {
        //Arrange
        Integer id = 3;
        doNothing().when(studentService).deleteStudentById(id);

        //Act
        ResultActions response = mockMvc.perform(delete("/students/deleteById").param("id", id.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(studentService, times(1)).deleteStudentById(id);
    }
}