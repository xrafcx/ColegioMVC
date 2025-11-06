package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.exceptions.QualificationNotFoundException;
import com.patterson.pruebatecnica.business.exceptions.StudentNotFoundException;
import com.patterson.pruebatecnica.business.exceptions.SubjectNotFoundException;
import com.patterson.pruebatecnica.business.service.QualificationService;
import com.patterson.pruebatecnica.business.service.StudentService;
import com.patterson.pruebatecnica.business.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui/qualification")
public class QualificationPageController {

    private final QualificationService service;
    private final SubjectService subjectService;
    private final StudentService studentService;

    public QualificationPageController(QualificationService service,  SubjectService subjectService, StudentService studentService) {
        this.service = service;
        this.subjectService = subjectService;
        this.studentService = studentService;
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
    public String create(@ModelAttribute("qualification") QualificationDTO dto, org.springframework.validation.BindingResult binding,
                         Model model, RedirectAttributes ra) {

        if (dto.getIdStudent() != null) {
            try {
                studentService.findStudentById(dto.getIdStudent()); // existe?
            } catch (StudentNotFoundException e) {
                binding.rejectValue("idStudent", "student.notFound", "El ID de alumno no existe.");
            }
        }

        if (dto.getIdSubject() != null) {
            try {
                subjectService.findSubjectById(dto.getIdSubject()); // existe?
            } catch (SubjectNotFoundException e) {
                binding.rejectValue("idSubject", "subject.notFound", "El ID de asignatura no existe.");
            }
        }

        if (binding.hasErrors()) {
            return "qualification/form";
        }

        service.createQualifications(java.util.List.of(dto));
        ra.addFlashAttribute("success", "Calificación creada.");
        return "redirect:/ui/qualification";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute("qualification") QualificationDTO dto,
                         org.springframework.validation.BindingResult binding, Model model,
                         RedirectAttributes ra) throws QualificationNotFoundException {

        if (dto.getIdStudent()!=null) {
            try {
                studentService.findStudentById(dto.getIdStudent());
            } catch (StudentNotFoundException e) {
                binding.rejectValue("idStudent", "student.notFound", "El ID de alumno no existe.");
            }
        }

        if (dto.getIdSubject()!=null) {
            try {
                subjectService.findSubjectById(dto.getIdSubject());
            } catch (SubjectNotFoundException e) {
                binding.rejectValue("idSubject", "subject.notFound", "El ID de asignatura no existe.");
            }
        }

        if (binding.hasErrors()) {
            return "qualification/form";
        }

        dto.setId(id);
        service.updateQualification(id, dto);
        ra.addFlashAttribute("success", "Calificación actualizada.");
        return "redirect:/ui/qualification";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) throws QualificationNotFoundException {
        service.deleteQualificationById(id);
        return "redirect:/ui/qualification";
    }
}