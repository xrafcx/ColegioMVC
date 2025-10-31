package com.patterson.pruebatecnica.business.dto;

import com.patterson.pruebatecnica.data.entities.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Estudiante")
public class StudentDTO {

    @Schema(description = "ID del estudiante", example = "1")
    private Integer id;

    @Schema(example = "Ada")
    private String firstName;

    @Schema(example = "Lovelace")
    private String lastName;

    @Schema(example = "ada@uni.es")
    private String email;

    public StudentDTO(Student student) {
        this.id =  student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.email = student.getEmail();
    }
}