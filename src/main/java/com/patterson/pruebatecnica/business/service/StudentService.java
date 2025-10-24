package com.patterson.pruebatecnica.business.service;

import com.patterson.pruebatecnica.business.dto.StudentDTO;
import com.patterson.pruebatecnica.business.exceptions.StudentNotFoundException;
import com.patterson.pruebatecnica.data.entities.Student;
import com.patterson.pruebatecnica.data.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public List<StudentDTO> createStudents(List<StudentDTO> studentsDTOs) {
        List<Student> stds = studentsDTOs.stream().map(dto -> {
            Student student = new Student();
            student.setFirstName(dto.getFirstName());
            student.setLastName(dto.getLastName());
            student.setEmail(dto.getEmail());
            return student;
        }).toList();

        List<Student> students = studentRepository.saveAll(stds);
        return students.stream().map(StudentDTO::new).toList();
    }

    @Transactional
    public StudentDTO findStudentById(Integer id) throws StudentNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isEmpty()) throw new StudentNotFoundException("Student not found");
        Student student = optionalStudent.get();
        return new StudentDTO(student);
    }

    @Transactional
    public List<StudentDTO> findAll() throws StudentNotFoundException {
        return studentRepository.findAll().stream().map(StudentDTO::new).toList();
    }

    @Transactional
    public void deleteStudentById(Integer id) throws StudentNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isPresent()){
            studentRepository.delete(optionalStudent.get());
            return;
        }
        throw new StudentNotFoundException("Student not found");
    }
}