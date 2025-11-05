package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.TeacherDTO;
import com.patterson.pruebatecnica.business.exceptions.TeacherNotFoundException;
import com.patterson.pruebatecnica.business.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/teacher")
public class TeacherPageController {

    private final TeacherService service;

    public TeacherPageController(TeacherService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) throws TeacherNotFoundException {
        model.addAttribute("teachers", service.findAll());
        return "teacher/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("teacher", new TeacherDTO());
        return "teacher/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) throws TeacherNotFoundException {
        model.addAttribute("teacher", service.findTeacherById(id));
        return "teacher/form";
    }

    @PostMapping
    public String create(@ModelAttribute("teacher") TeacherDTO dto) {
        service.createTeachers(List.of(dto));
        return "redirect:/ui/teacher";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,@ModelAttribute("teacher") TeacherDTO dto) throws TeacherNotFoundException {
        dto.setId(id);
        service.updateTeacher(id, dto);
        return "redirect:/ui/teacher";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) throws TeacherNotFoundException {
        service.deleteTeacherById(id);
        return "redirect:/ui/teacher";
    }
}