package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/ui/consultationTeacher")
public class ConsultationTeacherController {

    private final TeacherService teacherService;

    public ConsultationTeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) Integer idTeacher, Model model) {
        model.addAttribute("idTeacher", idTeacher);
        List<QualificationDTO> results = Collections.emptyList();
        if (idTeacher != null) {
            results = teacherService.findQualificationsByTeacherId(idTeacher);
        }
        model.addAttribute("results", results);
        model.addAttribute("hasSearched", idTeacher != null);
        return "consultationTeacher/list";
    }
}