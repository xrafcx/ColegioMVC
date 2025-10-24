package com.patterson.pruebatecnica.business.dto;

import com.patterson.pruebatecnica.data.entities.Qualification;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QualificationDTO {

    private Integer id;
    private double percentage;
    private double note;
    private Integer idSubject;
    private Integer idStudent;

    public QualificationDTO(Qualification qualification){
        this.id = qualification.getId();
        this.percentage = qualification.getPercentage();
        this.note = qualification.getNote();
        if(qualification.getSubject()!=null){
            this.idSubject = qualification.getSubject().getId();
        }
        if(qualification.getStudent()!=null){
            this.idStudent = qualification.getStudent().getId();
        }
    }
}