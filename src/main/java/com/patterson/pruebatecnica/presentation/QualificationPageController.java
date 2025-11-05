package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.exceptions.QualificationNotFoundException;
import com.patterson.pruebatecnica.business.service.QualificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/qualification")
public class QualificationPageController {

    private final QualificationService service;

    public QualificationPageController(QualificationService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) throws QualificationNotFoundException {
        model.addAttribute("qualifications", service.findAll());
        return "qualification/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("qualification", new QualificationDTO());
        return "qualification/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) throws QualificationNotFoundException {
        model.addAttribute("qualification", service.findQualificationById(id));
        return "qualification/form";
    }

    @PostMapping
    public String create(@ModelAttribute("qualification") QualificationDTO dto) {
        service.createQualifications(List.of(dto));
        return "redirect:/ui/qualification";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute("qualification") QualificationDTO dto) throws QualificationNotFoundException {
        dto.setId(id);
        service.updateQualification(id, dto);
        return "redirect:/ui/qualification";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) throws QualificationNotFoundException {
        service.deleteQualificationById(id);
        return "redirect:/ui/qualification";
    }
}