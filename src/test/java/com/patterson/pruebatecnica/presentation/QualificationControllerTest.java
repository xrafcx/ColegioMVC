package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.controller.QualificationController;
import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.service.QualificationService;
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

@WebMvcTest(controllers = QualificationController.class)
public class QualificationControllerTest {

    @MockitoBean
    private QualificationService qualificationService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void createQualificationsTest() throws Exception {
        //Arrange
        var requestList = List.of(new QualificationDTO(), new QualificationDTO());
        var responseList = List.of(new QualificationDTO(), new QualificationDTO());
        when(qualificationService.createQualifications(anyList())).thenReturn(responseList);

        //Act
        ResultActions response = mockMvc.perform(post("/qualifications/batch").contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .accept(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(new com.fasterxml.jackson.databind.ObjectMapper()
                        .findAndRegisterModules()
                        .writeValueAsString(requestList)));

        //Assert
        response.andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(qualificationService, times(1)).createQualifications(argThat(l -> l != null && l.size() == 2));
    }

    @Test
    void findQualificationByIdTest() throws Exception {
        //Arrange
        Integer id = 3;
        QualificationDTO qualificationDTO = new QualificationDTO();
        when(qualificationService.findQualificationById(id)).thenReturn(qualificationDTO);

        //Act
        ResultActions response = mockMvc.perform(get("/qualifications/findById").param("id", id.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(qualificationService, times(1)).findQualificationById(id);
    }

    @Test
    void findQualificationsTest() throws Exception {
        //Arrange
        var lista = List.of(new QualificationDTO(),  new QualificationDTO());
        when(qualificationService.findAll()).thenReturn(lista);

        //Act
        ResultActions response = mockMvc.perform(get("/qualifications/findQualifications"));

        //Assert
        response.andExpect(status().isOk());
        verify(qualificationService, times(1)).findAll();
    }

    @Test
    void findQualificationsByStudentIdTest() throws Exception {
        //Arrange
        Integer idStudent = 3;
        var lista = List.of(new QualificationDTO());
        when(qualificationService.findByStudentId(idStudent)).thenReturn(lista);

        //Act
        ResultActions response = mockMvc.perform(get("/qualifications/qualifications").param("idStudent", idStudent.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(qualificationService, times(1)).findByStudentId(idStudent);
    }

    @Test
    void deleteQualificationByIdTest() throws Exception {
        //Arrange
        Integer id = 3;
        doNothing().when(qualificationService).deleteQualificationById(id);

        //Act
        ResultActions response = mockMvc.perform(delete("/qualifications/deleteById").param("id", id.toString()));

        //Assert
        response.andExpect(status().isOk());
        verify(qualificationService, times(1)).deleteQualificationById(id);
    }
}