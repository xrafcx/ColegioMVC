package com.patterson.pruebatecnica.business.service;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.exceptions.QualificationNotFoundException;
import com.patterson.pruebatecnica.data.entities.Qualification;
import com.patterson.pruebatecnica.data.repositories.QualificationRepository;
import com.patterson.pruebatecnica.data.repositories.StudentRepository;
import com.patterson.pruebatecnica.data.repositories.SubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QualificationService {

    private final QualificationRepository qualificationRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    public QualificationService(QualificationRepository qualificationRepository, SubjectRepository subjectRepository, StudentRepository studentRepository) {
        this.qualificationRepository = qualificationRepository;
        this.subjectRepository = subjectRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Crea y guarda las calificaciones a partir de los DTO, y devuelve las creadas como DTO.
     * @param qualificationDTOS lista de calificaciones en DTO.
     * @return lista de calificaciones DTO creadas.
     */

    @Transactional
    public List<QualificationDTO> createQualifications(List<QualificationDTO> qualificationDTOS) {
        List<Qualification> qlfs = qualificationDTOS.stream().map(dto -> {
            Qualification qualification = new Qualification();
            qualification.setNote(dto.getNote());
            qualification.setPercentage(dto.getPercentage());
            qualification.setStudent(dto.getIdStudent()==null?null:studentRepository.getReferenceById(dto.getIdStudent()));
            qualification.setSubject(dto.getIdSubject()==null?null:subjectRepository.getReferenceById(dto.getIdSubject()));
            return qualification;
        }).toList();

        List<Qualification> qualifications = qualificationRepository.saveAll(qlfs);
        return qualifications.stream().map(QualificationDTO::new).toList();
    }

    /**
     * Se busca una calificación a partir del ID.
     * @param id identificador de la calificacion.
     * @return calificacion buscada.
     */

    @Transactional
    public QualificationDTO findQualificationById(Integer id) throws QualificationNotFoundException {
        Optional<Qualification> optionalQualification = qualificationRepository.findById(id);
        if (optionalQualification.isEmpty()) throw new QualificationNotFoundException("Qualification not found");
        Qualification qualification = optionalQualification.get();
        return new QualificationDTO(qualification);
    }

    /**
     * Devuelve todas las calificaciones guardadas.
     * @return lista de las calificaciones.
     */

    @Transactional
    public List<QualificationDTO> findAll() throws QualificationNotFoundException {
        return qualificationRepository.findAll().stream().map(QualificationDTO::new).toList();
    }

    /**
     * Actualiza la información de una calificación a partir de su ID.
     * @param id identificador de la calificación.
     * @param dto calificación en dto.
     */

    @Transactional
    public void updateQualification(Integer id, QualificationDTO dto) throws QualificationNotFoundException {
        Qualification qualification = qualificationRepository.findById(id).orElseThrow(() -> new QualificationNotFoundException("Qualification not found: " + id));
        qualification.setPercentage(dto.getPercentage());
        qualification.setNote(dto.getNote());
        if (dto.getIdStudent() != null) {
            studentRepository.findById(dto.getIdStudent()).ifPresent(qualification::setStudent);
        }
    }

    /**
     * Elimina una calificacion a partir del ID.
     * @param id identificador de la calificacion.
     */

    @Transactional
    public void deleteQualificationById(Integer id) throws QualificationNotFoundException {
        Optional<Qualification> optionalQualification = qualificationRepository.findById(id);
        if(optionalQualification.isPresent()){
            qualificationRepository.delete(optionalQualification.get());
            return;
        }
        throw new QualificationNotFoundException("Qualification not found");
    }

    /**
     * Devuelve todas las calificaciones de un estudiante.
     * @param idStudent identificador del estudiante.
     * @return lista de las calificaciones del estudiante.
     */

    @Transactional
    public List<QualificationDTO> findByStudentId(Integer idStudent) {
        return qualificationRepository.findByStudentId(idStudent).stream().map(QualificationDTO::new).toList();
    }
}