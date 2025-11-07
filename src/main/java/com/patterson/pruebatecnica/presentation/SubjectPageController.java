package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.SubjectDTO;
import com.patterson.pruebatecnica.business.exceptions.SubjectNotFoundException;
import com.patterson.pruebatecnica.business.exceptions.TeacherNotFoundException;
import com.patterson.pruebatecnica.business.service.SubjectService;
import com.patterson.pruebatecnica.business.service.TeacherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ui/subject")
public class SubjectPageController {

    private final SubjectService service;
    private final TeacherService teacherService;

    public SubjectPageController(SubjectService service, TeacherService teacherService) {
        this.service = service;
        this.teacherService = teacherService;
    }

    @GetMapping
    public String list(Model model) throws SubjectNotFoundException {
        model.addAttribute("subjects", service.findAll());
        return "subject/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) throws TeacherNotFoundException {
        model.addAttribute("subject", new SubjectDTO());
        model.addAttribute("teachers", teacherService.findAll());
        return "subject/form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) throws SubjectNotFoundException, TeacherNotFoundException {
        model.addAttribute("subject", service.findSubjectById(id));
        model.addAttribute("teachers", teacherService.findAll());
        return "subject/form";
    }

    @PostMapping
    public String create(@ModelAttribute("subject") SubjectDTO dto, org.springframework.validation.BindingResult binding, org.springframework.web.servlet.mvc.support.RedirectAttributes ra) {

        if (dto.getIdTeacher() != null) {
            try {
                teacherService.findTeacherById(dto.getIdTeacher());
            } catch (TeacherNotFoundException e) {
                binding.rejectValue("idTeacher", "teacher.notFound", "El ID del profesor no existe.");
            }
        }

        if (binding.hasErrors()) {
            return "subject/form";
        }

        service.createSubjects(java.util.List.of(dto));
        ra.addFlashAttribute("success", "Asignatura creada.");
        return "redirect:/ui/subject";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute("subject") SubjectDTO dto, org.springframework.validation.BindingResult binding, org.springframework.web.servlet.mvc.support.RedirectAttributes ra) throws SubjectNotFoundException {

        if (dto.getIdTeacher() != null) {
            try {
                teacherService.findTeacherById(dto.getIdTeacher());
            } catch (TeacherNotFoundException e) {
                binding.rejectValue("idTeacher", "teacher.notFound", "El ID del profesor no existe.");
            }
        }

        if (binding.hasErrors()) {
            return "subject/form";
        }

        dto.setId(id);
        service.updateSubject(id, dto);
        ra.addFlashAttribute("success", "Asignatura actualizada.");
        return "redirect:/ui/subject";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) throws SubjectNotFoundException {
        service.deleteSubjectById(id);
        return "redirect:/ui/subject";
    }
}