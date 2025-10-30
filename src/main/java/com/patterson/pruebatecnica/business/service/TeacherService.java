package com.patterson.pruebatecnica.business.service;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.dto.TeacherDTO;
import com.patterson.pruebatecnica.business.exceptions.TeacherNotFoundException;
import com.patterson.pruebatecnica.data.entities.Teacher;
import com.patterson.pruebatecnica.data.repositories.SubjectRepository;
import com.patterson.pruebatecnica.data.repositories.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public TeacherService(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    /**
     *
     * @param
     * @return
     */

    @Transactional
    public List<TeacherDTO> createTeachers(List<TeacherDTO> teacherDTOs) {
        List<Teacher> tchs = teacherDTOs.stream().map(dto->{
            Teacher teacher = new Teacher();
            teacher.setFirstName(dto.getFirstName());
            teacher.setLastName(dto.getLastName());
            teacher.setEmail(dto.getEmail());
            return teacher;
        }).toList();

        List<Teacher> teachers = teacherRepository.saveAll(tchs);
        return teachers.stream().map(TeacherDTO::new).toList();
    }

    /**
     *
     * @param
     * @return
     */

    @Transactional
    public TeacherDTO findTeacherById (Integer id) throws TeacherNotFoundException {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if(optionalTeacher.isEmpty()) throw new TeacherNotFoundException("Teacher not found");
        Teacher teacher = optionalTeacher.get();
        return new TeacherDTO(teacher);
    }

    /**
     *
     * @param
     * @return
     */

    @Transactional
    public List<TeacherDTO> findAll () throws TeacherNotFoundException {
        return teacherRepository.findAll().stream().map(TeacherDTO::new).toList();
    }

    /**
     *
     * @param
     * @return
     */

    @Transactional
    public void deleteTeacherById(Integer id) throws TeacherNotFoundException {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if(optionalTeacher.isPresent()){
            teacherRepository.delete(optionalTeacher.get());
            return;
        }
        throw new TeacherNotFoundException("Teacher not found");
    }

    /**
     *
     * @param
     * @return
     */

    @Transactional
    public List<QualificationDTO> findQualificationsByTeacherId(Integer idTeacher) {
        return subjectRepository.findQualificationsByTeacherId(idTeacher);
    }
}