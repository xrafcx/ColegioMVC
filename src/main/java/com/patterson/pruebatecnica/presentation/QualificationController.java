package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.exceptions.QualificationNotFoundException;
import com.patterson.pruebatecnica.business.service.QualificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/qualifications")
public class QualificationController {

    private QualificationService qualificationService;

    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    @PostMapping("/batch")
    public ResponseEntity<List<QualificationDTO>> createQualifications(@RequestBody List<QualificationDTO> qualificationDTOs) {
        List<QualificationDTO> qualifications = qualificationService.createQualifications(qualificationDTOs);
        return ResponseEntity.status(201).body(qualifications);
    }

    @GetMapping("/findById")
    public ResponseEntity<QualificationDTO> findQualificationById(@RequestParam Integer id) throws QualificationNotFoundException {
        QualificationDTO qualificationDTO = qualificationService.findQualificationById(id);
        return ResponseEntity.status(200).body(qualificationDTO);
    }

    @GetMapping("/findQualifications")
    public List<QualificationDTO> findQualifications()  throws QualificationNotFoundException {
        return qualificationService.findAll();
    }

    @GetMapping("/qualifications")
    public List<QualificationDTO> findQualificationsByStudentId(@RequestParam Integer idStudent) throws QualificationNotFoundException {
        return qualificationService.findByStudentId(idStudent);
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteQualificationById(@RequestParam Integer id) throws QualificationNotFoundException {
        qualificationService.deleteQualificationById(id);
        return ResponseEntity.notFound().build();
    }
}