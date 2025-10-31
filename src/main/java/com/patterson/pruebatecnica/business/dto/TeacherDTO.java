package com.patterson.pruebatecnica.business.dto;

import com.patterson.pruebatecnica.data.entities.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Profesor")
public class TeacherDTO {

    @Schema(description = "ID del profesor", example = "1")
    private Integer id;

    @Schema(example = "Ada")
    private String firstName;

    @Schema(example = "Lovelace")
    private String lastName;

    @Schema(example = "ada@uni.es")
    private String email;

    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
        this.email = teacher.getEmail();
    }
}