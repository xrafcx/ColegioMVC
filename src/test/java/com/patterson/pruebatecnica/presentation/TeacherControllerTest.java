package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.dto.TeacherDTO;
import com.patterson.pruebatecnica.business.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TeacherController.class)
public class TeacherControllerTest {

    @MockitoBean
    private TeacherService teacherService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createTeachersTest() throws Exception {
        //Arrange
        var requestList = List.of(new TeacherDTO(), new TeacherDTO());
        var responseList = List.of(new TeacherDTO(), new TeacherDTO());
        when(teacherService.createTeachers(anyList())).thenReturn(responseList);

        //Act
        ResultActions response = mockMvc.perform(post("/teachers/batch").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper()
                        .findAndRegisterModules()
                        .writeValueAsString(requestList)));

        //Assert
        response.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(teacherService, times(1)).createTeachers(argThat(l -> l != null && l.size() == 2));
    }

    @Test
    void findTeacherByIdTest() throws Exception {
        //Arrange
        Integer id = 3;
        TeacherDTO teacherDTO = new TeacherDTO();
        when(teacherService.findTeacherById(id)).thenReturn(teacherDTO);

        //Act
        ResultActions response = mockMvc.perform(get("/teachers/findById").param("id", id.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(teacherService, times(1)).findTeacherById(id);
    }

    @Test
    void findTeachersTest() throws Exception {
        //Arrange
        var lista = List.of(new TeacherDTO(), new TeacherDTO());
        when(teacherService.findAll()).thenReturn(lista);

        //Act
        ResultActions response = mockMvc.perform(get("/teachers/findTeachers"));

        //Assert
        response.andExpect(status().isOk());
        verify(teacherService, times(1)).findAll();
    }

    @Test
    void findQualificationsByTeacherIdTest() throws Exception {
        //Arrange
        Integer idTeacher = 3;
        var lista = List.of(new QualificationDTO());
        when(teacherService.findQualificationsByTeacherId(idTeacher)).thenReturn(lista);

        //Act
        ResultActions response = mockMvc.perform(get("/teachers/qualifications").param("idTeacher", idTeacher.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(teacherService, times(1)).findQualificationsByTeacherId(idTeacher);
    }

    @Test
    void deleteTeacherByIdTest() throws Exception {
        //Arrange
        Integer id = 3;
        doNothing().when(teacherService).deleteTeacherById(id);

        //Act
        ResultActions response = mockMvc.perform(delete("/teachers/deleteById").param("id", id.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(teacherService, times(1)).deleteTeacherById(id);
    }
}