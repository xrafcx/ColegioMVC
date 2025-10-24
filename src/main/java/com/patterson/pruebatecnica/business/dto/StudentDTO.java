package com.patterson.pruebatecnica.business.dto;

import com.patterson.pruebatecnica.data.entities.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    public StudentDTO(Student student) {
        this.id =  student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
    }
}