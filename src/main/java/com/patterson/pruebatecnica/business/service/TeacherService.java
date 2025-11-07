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
     * Crea y guarda los profesores a partir de los DTO, y devuelve los creados como DTO.
     * @param teacherDTOs lista de profesores en DTO.
     * @return lista de profesores DTO creados.
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
     * Se busca un profesor a partir del ID.
     * @param id identificador del profesor.
     * @return profesor buscado.
     */

    @Transactional
    public TeacherDTO findTeacherById (Integer id) throws TeacherNotFoundException {
        Optional<Teacher> optionalTeacher = teacherRepository.findById(id);
        if(optionalTeacher.isEmpty()) throw new TeacherNotFoundException("Teacher not found");
        Teacher teacher = optionalTeacher.get();
        return new TeacherDTO(teacher);
    }

    /**
     * Devuelve todos los profesores guardados.
     * @return lista de los profesores.
     */

    @Transactional
    public List<TeacherDTO> findAll () throws TeacherNotFoundException {
        return teacherRepository.findAll().stream().map(TeacherDTO::new).toList();
    }

    /**
     * Actualiza la información de un profesor a partir de su ID.
     * @param id identificador del profesor.
     * @param dto profesor en dto.
     */

    @Transactional
    public void updateTeacher(Integer id, TeacherDTO dto) throws TeacherNotFoundException {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new TeacherNotFoundException("Teacher not found: " + id));
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setEmail(dto.getEmail());
    }

    /**
     * Elimina un profesor a partir del ID.
     * @param id identificador del profesor.
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
     * Devuelve todas las calificaciones de las asignaturas que imparta un profesor.
     * @param idTeacher identificador del profesor.
     * @return lista de las calificaciones de las asignaturas.
     */

    @Transactional
    public List<QualificationDTO> findQualificationsByTeacherId(Integer idTeacher) {
        return subjectRepository.findQualificationsByTeacherId(idTeacher);
    }
}