package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.dto.TeacherDTO;
import com.patterson.pruebatecnica.business.exceptions.TeacherNotFoundException;
import com.patterson.pruebatecnica.business.service.TeacherService;
import com.patterson.pruebatecnica.data.entities.Qualification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/batch")
    public ResponseEntity<List<TeacherDTO>> createTeachers(@RequestBody List<TeacherDTO> teacherDTOs) {
        List<TeacherDTO> teachers = teacherService.createTeachers(teacherDTOs);
        return ResponseEntity.status(201).body(teachers);
    }

    @GetMapping("/findById")
    public ResponseEntity<TeacherDTO> findTeacherById(@RequestParam Integer id) throws TeacherNotFoundException {
        TeacherDTO teacherDTO = teacherService.findTeacherById(id);
        return ResponseEntity.status(200).body(teacherDTO);
    }

    @GetMapping("/findTeachers")
    public List<TeacherDTO> findTeachers() throws TeacherNotFoundException {
        return teacherService.findAll();
    }

    @GetMapping("/qualifications")
    public List<QualificationDTO> findQualificationsByTeacherId(@RequestParam Integer idTeacher) {
        return teacherService.findQualificationsByTeacherId(idTeacher);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<TeacherDTO> deleteTeacherById(@RequestParam Integer id) throws TeacherNotFoundException {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }
}