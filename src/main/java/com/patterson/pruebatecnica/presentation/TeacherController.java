package com.patterson.pruebatecnica.presentation;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.dto.TeacherDTO;
import com.patterson.pruebatecnica.business.exceptions.TeacherNotFoundException;
import com.patterson.pruebatecnica.business.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Teachers", description = "Operaciones con profesores")
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(
            summary = "Crear varios profesores",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class)),
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = "[{\"firstName\":\"Ada\",\"lastName\":\"Lovelace\",\"email\":\"ada@uni.es\"}," +
                                            "{\"firstName\":\"Alan\",\"lastName\":\"Turing\",\"email\":\"alan@uni.es\"}]"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Creado",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/batch")
    public ResponseEntity<List<TeacherDTO>> createTeachers(@RequestBody List<TeacherDTO> teacherDTOs) {
        List<TeacherDTO> teachers = teacherService.createTeachers(teacherDTOs);
        return ResponseEntity.status(201).body(teachers);
    }

    @Operation(
            summary = "Buscar profesor por ID",
            parameters = @Parameter(name = "id", description = "ID del profesor", example = "1"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado",
                            content = @Content(schema = @Schema(implementation = TeacherDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrado")
            }
    )
    @GetMapping("/findById")
    public ResponseEntity<TeacherDTO> findTeacherById(@RequestParam Integer id) throws TeacherNotFoundException {
        TeacherDTO teacherDTO = teacherService.findTeacherById(id);
        return ResponseEntity.status(200).body(teacherDTO);
    }

    @Operation(
            summary = "Lista de profesores",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TeacherDTO.class))))
    )
    @GetMapping("/findTeachers")
    public List<TeacherDTO> findTeachers() throws TeacherNotFoundException {
        return teacherService.findAll();
    }

    @Operation(
            summary = "Calificaciones de la asignatura que imparte un profesor",
            parameters = @Parameter(name = "idTeacher", description = "ID del profesor", example = "1"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = QualificationDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
            }
    )
    @GetMapping("/qualifications")
    public List<QualificationDTO> findQualificationsByTeacherId(@RequestParam Integer idTeacher) {
        return teacherService.findQualificationsByTeacherId(idTeacher);
    }

    @Operation(
            summary = "Eliminar profesor por ID",
            parameters = @Parameter(name = "id", description = "ID del profesor", example = "1"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Eliminado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado")
            }
    )
    @DeleteMapping("/deleteById")
    public ResponseEntity<TeacherDTO> deleteTeacherById(@RequestParam Integer id) throws TeacherNotFoundException {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.ok().build();
    }
}