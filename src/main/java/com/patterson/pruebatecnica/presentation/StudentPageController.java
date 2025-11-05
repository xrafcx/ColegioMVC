package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.StudentDTO;
import com.patterson.pruebatecnica.business.exceptions.StudentNotFoundException;
import com.patterson.pruebatecnica.business.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/student")
public class StudentPageController {

    private final StudentService service;

    public StudentPageController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) throws StudentNotFoundException {
        model.addAttribute("students", service.findAll());
        return "student/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("student", new StudentDTO());
        return "student/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) throws StudentNotFoundException {
        model.addAttribute("student", service.findStudentById(id));
        return "student/form";
    }

    @PostMapping
    public String create(@ModelAttribute("student") StudentDTO dto) {
        service.createStudents(List.of(dto));
        return "redirect:/ui/student";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute("student") StudentDTO dto) throws StudentNotFoundException {
        dto.setId(id);
        service.updateStudent(id, dto);
        return "redirect:/ui/student";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) throws StudentNotFoundException {
        service.deleteStudentById(id);
        return "redirect:/ui/student";
    }
}