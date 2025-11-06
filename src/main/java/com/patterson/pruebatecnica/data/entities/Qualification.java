package com.patterson.pruebatecnica.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "qualification")
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "percentage")
    private Double percentage;

    @Column(name = "note")
    private Double note;

    @ManyToOne
    @JoinColumn(name = "idSubject", referencedColumnName = "id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "idStudent", referencedColumnName = "id")
    private Student student;
}