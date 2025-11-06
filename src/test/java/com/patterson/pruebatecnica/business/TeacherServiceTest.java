package com.patterson.pruebatecnica.business;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.dto.TeacherDTO;
import com.patterson.pruebatecnica.business.exceptions.TeacherNotFoundException;
import com.patterson.pruebatecnica.business.service.SubjectService;
import com.patterson.pruebatecnica.business.service.TeacherService;
import com.patterson.pruebatecnica.data.entities.Teacher;
import com.patterson.pruebatecnica.data.repositories.SubjectRepository;
import com.patterson.pruebatecnica.data.repositories.TeacherRepository;
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
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private TeacherService teacherService;

    @InjectMocks
    private SubjectService subjectService;

    @Test
    void createTeachersTest() throws  Exception {
        //Arrange
        List<TeacherDTO> teachers = new ArrayList<>();
        teachers.add(new TeacherDTO());
        teachers.add(new TeacherDTO());
        when(teacherRepository.saveAll(anyList())).thenAnswer(i -> i.getArguments()[0]);

        //Act
        List <TeacherDTO> teacherDTOS = teacherService.createTeachers(teachers);

        //Assert
        Assertions.assertNotNull(teacherDTOS);
        Assertions.assertEquals(teachers.size(), teacherDTOS.size());
        verify(teacherRepository.saveAll(argThat(l -> ((List<?>) l).size() == 2)));
    }

    @Test
    void findTeacherByIdTest() throws  Exception {
        //Arrange
        Teacher teacher = new Teacher();
        Integer id = 5;
        teacher.setId(id);
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        //Act
        TeacherDTO teacherDTO = teacherService.findTeacherById(teacher.getId());

        //Assert
        Assertions.assertNotNull(teacherDTO);
        Assertions.assertEquals(teacher.getId(), teacherDTO.getId());
        verify(teacherRepository, times(1)).findById(teacher.getId());
    }

    @Test
    void findAllTest() throws  Exception {
        //Arrange
        int expectedSize = 3;
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher());
        teachers.add(new Teacher());
        teachers.add(new Teacher());
        when(teacherRepository.findAll()).thenReturn(teachers);

        //Act
        var result = teacherService.findAll();

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals(expectedSize, result.size());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void deleteTeacherByIdTest() throws  Exception {
        //Arrange
        Integer id = 3;
        Teacher teacher = new Teacher();
        teacher.setId(id);
        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));

        //Act
        teacherService.deleteTeacherById(teacher.getId());

        //Assert
        verify(teacherRepository, times(1)).findById(teacher.getId());
    }

    @Test
    void deleteTeacherByIdWhenTeacherNotExistTest() throws  Exception {
        //Arrange
        Integer id = 3;
        when(teacherRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        var response = Assertions.assertThrows(TeacherNotFoundException.class, () -> teacherService.deleteTeacherById(id));

        //Assert
        Assertions.assertNotNull(response);
        verify(teacherRepository, times(1)).findById(id);
    }

    @Test
    void findQualificationsByTeacherIdTest() throws  Exception {
        //Arrange
        Integer idTeacher = 3;
        int expectedSize = 2;
        List<QualificationDTO> qualifications = new ArrayList<>();
        qualifications.add(new QualificationDTO());
        qualifications.add(new QualificationDTO());
        when(subjectRepository.findQualificationsByTeacherId(idTeacher)).thenReturn(qualifications);

        //Act
        List<QualificationDTO> qualificationDTOS = subjectRepository.findQualificationsByTeacherId(idTeacher);

        //Assert
        Assertions.assertNotNull(qualificationDTOS);
        Assertions.assertEquals(qualifications.size(), qualificationDTOS.size());
        verify(subjectRepository).findQualificationsByTeacherId(idTeacher);
    }
}