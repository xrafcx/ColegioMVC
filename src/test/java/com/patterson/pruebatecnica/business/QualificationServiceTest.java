package com.patterson.pruebatecnica.business;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.exceptions.QualificationNotFoundException;
import com.patterson.pruebatecnica.business.service.QualificationService;
import com.patterson.pruebatecnica.data.entities.Qualification;
import com.patterson.pruebatecnica.data.repositories.QualificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QualificationServiceTest {

    @Mock
    private QualificationRepository qualificationRepository;
    @InjectMocks
    private QualificationService qualificationService;

    @Test
    void createQualificationsTest() throws Exception{
        //Arrange


        //Act


        //Assert
    }

    @Test
    void findQualificationByIdTest() throws Exception {

        //Arrange
        Qualification qualification = new Qualification();
        Integer id = 5;
        qualification.setId(id);
        when(qualificationRepository.findById(5)).thenReturn(Optional.of(qualification));

        //Act
        QualificationDTO qualificationDTO = qualificationService.findQualificationById(qualification.getId());

        //Assert
        Assertions.assertNotNull(qualificationDTO);
        Assertions.assertEquals(qualification.getId(), qualificationDTO.getId());
        verify(qualificationRepository, times(1)).findById(5);
    }

    @Test
    void findAllTest() throws Exception{
        //Arrange
        int expectedSize = 3;
        ArrayList<Qualification> qualifications = new ArrayList<>();
        qualifications.add(new Qualification());
        qualifications.add(new Qualification());
        qualifications.add(new Qualification());

        when(qualificationRepository.findAll()).thenReturn(qualifications);

        //Act
        var response = qualificationService.findAll();

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedSize, response.size());
        verify(qualificationRepository, times(1)).findAll();
    }

    @Test
    void deleteQualificationByIdTest() throws Exception{
        //Arrange
        Integer id = 3;
        Qualification qualification = new Qualification();
        qualification.setId(id);
        when(qualificationRepository.findById(id)).thenReturn(Optional.of(qualification));

        //Act
        qualificationService.deleteQualificationById(id);

        //Assert
        verify(qualificationRepository, times(1)).findById(id);
        verify(qualificationRepository, times(1)).delete(qualification);
    }

    @Test
    void deleteQualificationByIdWhenQualificationNotExistTest() throws Exception{
        //Arrange
        Integer id = 3;
        when(qualificationRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        var response = Assertions.assertThrows(QualificationNotFoundException.class, () -> {qualificationService.deleteQualificationById(id);});

        //Assert
        Assertions.assertNotNull(response);
        verify(qualificationRepository, times(1)).findById(id);
    }

    @Test
    void findByStudentIdTest() throws Exception{
        //Arrange
        Integer idStudent = 5;
        int expectedSize = 2;

        List<Qualification> qualifications = new ArrayList<>();
        qualifications.add(new Qualification());
        qualifications.add(new Qualification());

        when(qualificationRepository.findByStudentId(idStudent)).thenReturn(qualifications);

        //Act
        var response = qualificationService.findByStudentId(idStudent);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedSize, response.size());
        verify(qualificationRepository, times(1)).findByStudentId(idStudent);
    }
}