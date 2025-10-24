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
    private String nameSubject;
    private Integer idStudent;
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