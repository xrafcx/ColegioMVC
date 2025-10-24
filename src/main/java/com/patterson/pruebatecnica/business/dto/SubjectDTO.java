package com.patterson.pruebatecnica.business.dto;

import com.patterson.pruebatecnica.data.entities.Subject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubjectDTO {

    private Integer id;
    private String name;
    private Integer idTeacher;
    private String nameTeacher;

    public SubjectDTO(Subject subject) {
        this.id = subject.getId();
        this.name = subject.getName();
        if (subject.getTeacher() != null){
            this.idTeacher = subject.getTeacher().getId();
            this.nameTeacher = subject.getTeacher().getFirstName() + " " + subject.getTeacher().getLastName();
        }
    }
}