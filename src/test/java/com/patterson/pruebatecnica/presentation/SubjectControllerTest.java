package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.SubjectDTO;
import com.patterson.pruebatecnica.business.service.SubjectService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SubjectController.class)
public class SubjectControllerTest {

    @MockitoBean
    private SubjectService subjectService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createSubjectsTest() throws Exception {
        //Arrange
        var requestList = List.of(new SubjectDTO(), new SubjectDTO());
        var responseList = List.of(new SubjectDTO(), new SubjectDTO());
        when(subjectService.createSubjects(anyList())).thenReturn(responseList);

        //Act
        ResultActions response = mockMvc.perform(post("/subjects/batch").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper()
                        .findAndRegisterModules()
                        .writeValueAsString(requestList)));

        //Assert
        response.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(subjectService, times(1)).createSubjects(argThat(l -> l != null && l.size() == 2));
    }

    @Test
    void findSubjectByIdTest() throws Exception {
        //Arrange
        Integer id = 3;
        SubjectDTO subjectDTO = new SubjectDTO();
        when(subjectService.findSubjectById(anyInt())).thenReturn(subjectDTO);

        //Act
        ResultActions response = mockMvc.perform(get("/subjects/findById").param("id", id.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(subjectService, times(1)).findSubjectById(id);
    }

    @Test
    void findSubjectsTest() throws Exception {
        //Arrange
        var lista = List.of(new SubjectDTO(), new SubjectDTO());
        when(subjectService.findAll()).thenReturn(lista);

        //Act
        ResultActions response = mockMvc.perform(get("/subjects/findSubjects"));

        //Assert
        response.andExpect(status().isOk());
        verify(subjectService, times(1)).findAll();

    }

    @Test
    void findSubjectsByIdTeacherTest() throws Exception {
        //Arrange
        Integer idTeacher = 3;
        var lista = List.of(new SubjectDTO());
        when(subjectService.findSubjectsByTeacherId(idTeacher)).thenReturn(lista);

        //Act
        ResultActions response = mockMvc.perform(get("/subjects/findSubjectsByIdTeacher").param("idTeacher", idTeacher.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(subjectService, times(1)).findSubjectsByTeacherId(idTeacher);
    }

    @Test
    void deleteSubjectByIdTest() throws Exception {
        //Arrange
        Integer id = 3;
        doNothing().when(subjectService).deleteSubjectById(id);

        //Act
        ResultActions response = mockMvc.perform(delete("/subjects/deleteById").param("id", id.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(subjectService, times(1)).deleteSubjectById(id);
    }
}