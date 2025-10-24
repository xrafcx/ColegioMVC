package com.patterson.pruebatecnica.business.dto;

import com.patterson.pruebatecnica.data.entities.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();
    }
}