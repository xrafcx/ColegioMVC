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
     *
     * @param
     * @return
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
     *
     * @param
     * @return
     */

    @Transactional
    public SubjectDTO findSubjectById(Integer id) throws SubjectNotFoundException {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if(optionalSubject.isEmpty()) throw new SubjectNotFoundException("Subject not found");
        Subject subject = optionalSubject.get();
        return new SubjectDTO(subject);
    }

    /**
     *
     * @param
     * @return
     */

    @Transactional
    public List<SubjectDTO> findAll() throws SubjectNotFoundException {
        return subjectRepository.findAll().stream().map(SubjectDTO::new).toList();
    }

    /**
     *
     * @param
     * @return
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
     *
     * @param
     * @return
     */

    @Transactional
    public List<SubjectDTO> findSubjectsByTeacherId(Integer idTeacher) {
        return subjectRepository.findByTeacherId(idTeacher).stream().map(SubjectDTO::new).toList();
    }
}