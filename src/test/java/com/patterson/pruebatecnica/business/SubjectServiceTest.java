package com.patterson.pruebatecnica.business;

import com.patterson.pruebatecnica.business.dto.SubjectDTO;
import com.patterson.pruebatecnica.business.exceptions.SubjectNotFoundException;
import com.patterson.pruebatecnica.business.service.SubjectService;
import com.patterson.pruebatecnica.data.entities.Subject;
import com.patterson.pruebatecnica.data.repositories.SubjectRepository;
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
public class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    void createSubjectsTest() throws Exception{
        //Arrange
        List<SubjectDTO> subjects = new ArrayList<>();
        subjects.add(new SubjectDTO());
        subjects.add(new SubjectDTO());
        when(subjectRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        //Act
        List <SubjectDTO> subjectDTOS = subjectService.createSubjects(subjects);

        //Assert
        Assertions.assertNotNull(subjectDTOS);
        Assertions.assertEquals(subjects.size(),subjectDTOS.size());
        verify(subjectRepository).saveAll(argThat(s -> ((List<?>) s). size() == 2));
    }

    @Test
    void findSubjectByIdTest() throws Exception{
        //Arrange
        Subject subject = new Subject();
        Integer id = 5;
        subject.setId(id);
        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        //Act
        SubjectDTO subjectDTO = subjectService.findSubjectById(subject.getId());

        //Assert
        Assertions.assertNotNull(subjectDTO);
        Assertions.assertEquals(subject.getId(),subjectDTO.getId());
        verify(subjectRepository, times(1)).findById(id);
    }

    @Test
    void findAllTest() throws Exception{
        //Arrange
        int expectedSize = 3;
        ArrayList<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject());
        subjects.add(new Subject());
        subjects.add(new Subject());
        when(subjectRepository.findAll()).thenReturn(subjects);

        //Act
        var response = subjectService.findAll();

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedSize,response.size());
        verify(subjectRepository, times(1)).findAll();
    }

    @Test
    void deleteSubjectByIdTest() throws Exception{
        //Arrange
        Integer id = 3;
        Subject subject = new Subject();
        subject.setId(id);
        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        //Act
        subjectService.deleteSubjectById(subject.getId());

        //Assert
        verify(subjectRepository, times(1)).findById(subject.getId());
    }

    @Test
    void deleteSubjectByIdWhenSubjectNotExistTest() throws Exception{
        //Arrange
        Integer id = 3;
        when(subjectRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        var response = Assertions.assertThrows(SubjectNotFoundException.class, () -> {subjectService.deleteSubjectById(id);});

        //Assert
        Assertions.assertNotNull(response);
        verify(subjectRepository, times(1)).findById(id);
    }

    @Test
    void findSubjectsByTeacherIdTest() throws Exception{
        //Arrange
        Integer idSubject = 1;
        int expectedSize = 2;
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject());
        subjects.add(new Subject());
        when(subjectRepository.findByTeacherId(idSubject)).thenReturn(subjects);

        //Act
        var response = subjectService.findSubjectsByTeacherId(idSubject);

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedSize,response.size());
        verify(subjectRepository, times(1)).findByTeacherId(idSubject);
    }
}