package com.patterson.pruebatecnica.business.dto;

import com.patterson.pruebatecnica.data.entities.Qualification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Calificación de un estudiante en una asignatura")
public class QualificationDTO {

    @Schema(description = "ID de la calificación", example = "101")
    private Integer id;

    @Schema(description = "Porcentaje de la calificación (%)", example = "40")
    private Double percentage;

    @Schema(description = "Nota obtenida", example = "8.5")
    private Double note;

    @Schema(description = "ID de la asignatura", example = "10", nullable = true)
    private Integer idSubject;

    @Schema(description = "Nombre de la asignatura", example = "Matemáticas I", nullable = true)
    private String nameSubject;

    @Schema(description = "ID del estudiante", example = "1", nullable = true)
    private Integer idStudent;

    @Schema(description = "Nombre completo del estudiante", example = "Ada Lovelace", nullable = true)
    private String nameStudent;

    public QualificationDTO(Qualification qualification){
        this.id = qualification.getId();
        this.percentage = qualification.getPercentage();
        this.note = qualification.getNote();
        if(qualification.getSubject()!=null){
            this.idSubject = qualification.getSubject().getId();
            this.nameSubject = qualification.getSubject().getName();
        }
        if(qualification.getStudent()!=null){
            this.idStudent = qualification.getStudent().getId();
            this.nameStudent = qualification.getStudent().getFirstName()+" "+qualification.getStudent().getLastName();
        }
    }
}