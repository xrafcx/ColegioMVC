package com.patterson.pruebatecnica.business;

import com.patterson.pruebatecnica.business.dto.StudentDTO;
import com.patterson.pruebatecnica.business.exceptions.StudentNotFoundException;
import com.patterson.pruebatecnica.business.service.StudentService;
import com.patterson.pruebatecnica.data.entities.Student;
import com.patterson.pruebatecnica.data.repositories.StudentRepository;
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
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    @Test
    void createStudentTest() throws Exception{
        //Arrange
        List<StudentDTO> students = new ArrayList<>();
        students.add(new StudentDTO());
        students.add(new StudentDTO());
        when(studentRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

        //Act
        List<StudentDTO> studentDTOS = studentService.createStudents(students);

        //Assert
        Assertions.assertNotNull(studentDTOS);
        Assertions.assertEquals(students.size(),studentDTOS.size());
        verify(studentRepository).saveAll(argThat(s -> ((List<?>) s).size() == 2));
    }

    @Test
    void findStudentByIdTest() throws Exception{
        //Arrange
        Student student = new Student();
        Integer id = 5;
        student.setId(id);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        //Act
        StudentDTO studentDTO = studentService.findStudentById(student.getId());

        //Assert
        Assertions.assertNotNull(studentDTO);
        Assertions.assertEquals(student.getId(),studentDTO.getId());
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void findAllTest() throws Exception{
        //Arrange
        int expectedSize = 3;
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student());
        students.add(new Student());
        students.add(new Student());
        when(studentRepository.findAll()).thenReturn(students);

        //Act
        var response = studentService.findAll();

        //Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedSize,response.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void deleteStudentByIdTest() throws Exception{
        //Arrange
        Integer id = 3;
        Student student = new Student();
        student.setId(id);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        //Act
        studentService.deleteStudentById(id);

        //Assert
        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void deleteStudentByIdWhenStudentNotExistTest() throws Exception{
        //Arrange
        Integer id = 3;
        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        //Act
        var response = Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudentById(id));

        //Assert
        Assertions.assertNotNull(response);
        verify(studentRepository, times(1)).findById(id);
    }
}