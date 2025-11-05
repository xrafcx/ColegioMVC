package com.patterson.pruebatecnica.business.controller;

import com.patterson.pruebatecnica.business.dto.StudentDTO;
import com.patterson.pruebatecnica.business.exceptions.StudentNotFoundException;
import com.patterson.pruebatecnica.business.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Students", description = "Operaciones con estudiantes")
@RestController
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Crear varios estudiantes",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = StudentDTO.class)),
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = "[{\"firstName\":\"Ada\",\"lastName\":\"Lovelace\",\"email\":\"ada@uni.es\"}," +
                                            "{\"firstName\":\"Alan\",\"lastName\":\"Turing\",\"email\":\"alan@uni.es\"}]"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Creado",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/batch")
    public ResponseEntity<List<StudentDTO>> createStudents(@RequestBody List<StudentDTO> studentsDTOs) {
        List<StudentDTO> students = studentService.createStudents(studentsDTOs);
        return ResponseEntity.status(201).body(students);
    }

    @Operation(
            summary = "Buscar estudiante por ID",
            parameters = @Parameter(name = "id", description = "ID del estudiante", example = "1"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado",
                            content = @Content(schema = @Schema(implementation = StudentDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrado")
            }
    )
    @GetMapping("/findById")
    public ResponseEntity<StudentDTO> findStudentById(@RequestParam Integer id) throws StudentNotFoundException {
        StudentDTO studentDTO = studentService.findStudentById(id);
        return ResponseEntity.status(200).body(studentDTO);
    }

    @Operation(
            summary = "Lista de estudiantes",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentDTO.class))))
    )
    @GetMapping("/findStudents")
    public List<StudentDTO> findStudents() throws StudentNotFoundException {
        return studentService.findAll();
    }

    @Operation(
            summary = "Eliminar estudiante por ID",
            parameters = @Parameter(name = "id", description = "ID del estudiante", example = "1"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Eliminado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado")
            }
    )
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteStudentById(@RequestParam Integer id) throws StudentNotFoundException {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok().build();
    }
}