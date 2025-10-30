package com.patterson.pruebatecnica.business.service;

import com.patterson.pruebatecnica.business.dto.SubjectDTO;
import com.patterson.pruebatecnica.business.exceptions.SubjectNotFoundException;
import com.patterson.pruebatecnica.data.entities.Subject;
import com.patterson.pruebatecnica.data.repositories.SubjectRepository;
import com.patterson.pruebatecnica.data.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    public SubjectService(SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    /**
     * Crea y guarda las asignaturas a partir de los DTO, y devuelve las creadas como DTO.
     * @param subjectDTOs lista de asignaturas en DTO.
     * @return lista de asignaturas DTO creadas.
     */

    @Transactional
    public List<SubjectDTO> createSubjects(List<SubjectDTO> subjectDTOs) {
        List<Subject> sbjs = subjectDTOs.stream().map(dto -> {
            Subject subject = new Subject();
            subject.setName(dto.getName());
            subject.setTeacher(dto.getIdTeacher()==null ? null : teacherRepository.getReferenceById(dto.getIdTeacher()));
            return subject;
        }).toList();

        List<Subject> subjects = subjectRepository.saveAll(sbjs);
        return subjects.stream().map(SubjectDTO::new).toList();
    }

    /**
     * Se busca una asignatura a partir del ID.
     * @param id identificador de la asignatura.
     * @return asignatura buscada.
     */

    @Transactional
    public SubjectDTO findSubjectById(Integer id) throws SubjectNotFoundException {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if(optionalSubject.isEmpty()) throw new SubjectNotFoundException("Subject not found");
        Subject subject = optionalSubject.get();
        return new SubjectDTO(subject);
    }

    /**
     * Devuelve todas las asignaturas guardadas.
     * @return lista de las asignaturas.
     */

    @Transactional
    public List<SubjectDTO> findAll() throws SubjectNotFoundException {
        return subjectRepository.findAll().stream().map(SubjectDTO::new).toList();
    }

    /**
     * Elimina una asignatura a partir del ID.
     * @param id identificador de la asignatura.
     */

    @Transactional
    public void deleteSubjectById(Integer id) throws SubjectNotFoundException {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
            if(optionalSubject.isPresent()){
               subjectRepository.delete(optionalSubject.get());
               return;
            }
        throw new SubjectNotFoundException("Subject not found");
    }

    /**
     * Devuelve todas las asignaturas que imparte un profesor.
     * @param idTeacher identificador del profesor.
     * @return lista de las asignaturas que imparte el profesor.
     */

    @Transactional
    public List<SubjectDTO> findSubjectsByTeacherId(Integer idTeacher) {
        return subjectRepository.findByTeacherId(idTeacher).stream().map(SubjectDTO::new).toList();
    }
}