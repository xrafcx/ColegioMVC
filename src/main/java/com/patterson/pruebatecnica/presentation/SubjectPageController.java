package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.SubjectDTO;
import com.patterson.pruebatecnica.business.exceptions.SubjectNotFoundException;
import com.patterson.pruebatecnica.business.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/subject")
public class SubjectPageController {

    private final SubjectService service;

    public SubjectPageController(SubjectService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) throws SubjectNotFoundException {
        model.addAttribute("subjects", service.findAll());
        return "subject/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("subject", new SubjectDTO());
        return "subject/form";
    }


    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) throws SubjectNotFoundException {
        model.addAttribute("subject", service.findSubjectById(id));
        return "subject/form";
    }

    @PostMapping
    public String create(@ModelAttribute("subject") SubjectDTO dto) {
        service.createSubjects(List.of(dto));
        return "redirect:/ui/subject";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute("subject") SubjectDTO dto) throws SubjectNotFoundException {
        dto.setId(id);
        service.updateSubject(id, dto);
        return "redirect:/ui/subject";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) throws SubjectNotFoundException {
        service.deleteSubjectById(id);
        return "redirect:/ui/subject";
    }
}