package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.SubjectDTO;
import com.patterson.pruebatecnica.business.exceptions.SubjectNotFoundException;
import com.patterson.pruebatecnica.business.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/batch")
    public ResponseEntity<List<SubjectDTO>> createSubjects(@RequestBody List<SubjectDTO> subjectDTOs) {
        List<SubjectDTO> subjects = subjectService.createSubjects(subjectDTOs);
        return ResponseEntity.status(201).body(subjects);
    }

    @GetMapping("/findById")
    public ResponseEntity<SubjectDTO> findSubjectById(@RequestParam Integer id) throws SubjectNotFoundException {
        SubjectDTO subjectDTO = subjectService.findSubjectById(id);
        return ResponseEntity.status(200).body(subjectDTO);
    }

    @GetMapping("/findSubjects")
    public List<SubjectDTO> findSubjects() throws SubjectNotFoundException {
        return subjectService.findAll();
    }

    @GetMapping("/findSubjectsByIdTeacher")
    public List <SubjectDTO> findSubjectsByIdTeacher(@RequestParam Integer idTeacher) {
        return subjectService.findSubjectsByTeacherId(idTeacher);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<SubjectDTO> deleteSubjectById(@RequestParam Integer id) throws SubjectNotFoundException {
        subjectService.deleteSubjectById(id);
        return ResponseEntity.noContent().build();
    }
}