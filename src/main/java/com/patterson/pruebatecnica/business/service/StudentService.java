package com.patterson.pruebatecnica.business.service;

import com.patterson.pruebatecnica.business.dto.StudentDTO;
import com.patterson.pruebatecnica.business.exceptions.StudentNotFoundException;
import com.patterson.pruebatecnica.data.entities.Student;
import com.patterson.pruebatecnica.data.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }

    /**
     * Crea y guarda los estudiantes a partir de los DTO, y devuelve los creados como DTO.
     * @param studentsDTOs lista de estudiantes en DTO.
     * @return lista de estudiantes DTO creados.
     */

    @Transactional
    public List<StudentDTO> createStudents(List<StudentDTO> studentsDTOs) {
        List<Student> stds = studentsDTOs.stream().map(dto -> {
            Student student = new Student();
            student.setFirstName(dto.getFirstName());
            student.setLastName(dto.getLastName());
            student.setEmail(dto.getEmail());
            return student;
        }).toList();

        List<Student> students = studentRepository.saveAll(stds);
        return students.stream().map(StudentDTO::new).toList();
    }

    /**
     * Se busca un estudiante a partir del ID.
     * @param id identificador del estudiante.
     * @return estudiante buscado.
     */

    @Transactional
    public StudentDTO findStudentById(Integer id) throws StudentNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isEmpty()) throw new StudentNotFoundException("Student not found");
        Student student = optionalStudent.get();
        return new StudentDTO(student);
    }

    /**
     * Devuelve todos los estudiantes guardados.
     * @return lista de los estudiantes.
     */

    @Transactional
    public List<StudentDTO> findAll() throws StudentNotFoundException {
        return studentRepository.findAll().stream().map(StudentDTO::new).toList();
    }

    /**
     * Actualiza la información de un alumno a partir de su ID.
     * @param id identificador del alumno.
     * @param dto alumno en dto.
     */

    @Transactional
    public StudentDTO updateStudent(Integer id, StudentDTO dto) throws StudentNotFoundException {
        Student entity = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found: " + id));
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        Student saved = studentRepository.save(entity);
        StudentDTO out = new StudentDTO();
        out.setId(saved.getId());
        out.setFirstName(saved.getFirstName());
        out.setLastName(saved.getLastName());
        out.setEmail(saved.getEmail());
        return out;
    }

    /**
     * Elimina un estudiante a partir del ID.
     * @param id identificador del estudiante.
     */

    @Transactional
    public void deleteStudentById(Integer id) throws StudentNotFoundException {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isPresent()){
            studentRepository.delete(optionalStudent.get());
            return;
        }
        throw new StudentNotFoundException("Student not found");
    }
}