package com.patterson.pruebatecnica.business.dto;

import com.patterson.pruebatecnica.data.entities.Subject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Asignatura con datos resumidos del profesor")
public class SubjectDTO {

    @Schema(description = "ID de la asignatura", example = "10")
    private Integer id;

    @Schema(description = "Nombre de la asignatura", example = "Matemáticas I")
    private String name;

    @Schema(description = "ID del profesor asignado", example = "1", nullable = true)
    private Integer idTeacher;

    @Schema(description = "Nombre del profesor", example = "Ada Lovelace", nullable = true)
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