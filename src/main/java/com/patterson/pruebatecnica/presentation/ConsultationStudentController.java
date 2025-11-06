package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.service.QualificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/ui/consultationStudent")
public class ConsultationStudentController {

    private final QualificationService qualificationService;

    public ConsultationStudentController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) Integer idStudent, Model model) {
        model.addAttribute("idStudent", idStudent);
        List<QualificationDTO> results = Collections.emptyList();
        if (idStudent != null) {
            results = qualificationService.findByStudentId(idStudent);
        }
        model.addAttribute("results", results);
        model.addAttribute("hasSearched", idStudent != null);
        return "consultationStudent/list";
    }
}