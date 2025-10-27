package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.dto.StudentDTO;
import com.patterson.pruebatecnica.business.exceptions.StudentNotFoundException;
import com.patterson.pruebatecnica.business.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/batch")
    public ResponseEntity<List<StudentDTO>> createStudents(@RequestBody List<StudentDTO> studentsDTOs) {
        List<StudentDTO> students = studentService.createStudents(studentsDTOs);
        return ResponseEntity.status(201).body(students);
    }

    @GetMapping("/findById")
    public ResponseEntity<StudentDTO> findStudentById(@RequestParam Integer id) throws StudentNotFoundException {
        StudentDTO studentDTO = studentService.findStudentById(id);
        return ResponseEntity.status(200).body(studentDTO);
    }

    @GetMapping("/findStudents")
    public List<StudentDTO> findStudents() throws StudentNotFoundException {
        return studentService.findAll();
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<StudentDTO> deleteStudentById(@RequestParam Integer id) throws StudentNotFoundException {
        studentService.deleteStudentById(id);
        return ResponseEntity.notFound().build();
    }
}