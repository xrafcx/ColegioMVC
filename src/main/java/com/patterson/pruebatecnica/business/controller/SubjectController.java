package com.patterson.pruebatecnica.business.controller;

import com.patterson.pruebatecnica.business.dto.SubjectDTO;
import com.patterson.pruebatecnica.business.exceptions.SubjectNotFoundException;
import com.patterson.pruebatecnica.business.service.SubjectService;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Subjects", description = "Operaciones con asignaturas")
@RestController
@RequestMapping("/subjects")
public class SubjectController {

    private SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Operation(
            summary = "Crear varias asignaturas",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class)),
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = "[{\"name\":\"Matemáticas I\",\"idTeacher\":1},{\"name\":\"Física I\",\"idTeacher\":2}]"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Creado",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/batch")
    public ResponseEntity<List<SubjectDTO>> createSubjects(@RequestBody List<SubjectDTO> subjectDTOs) {
        List<SubjectDTO> subjects = subjectService.createSubjects(subjectDTOs);
        return ResponseEntity.status(201).body(subjects);
    }

    @Operation(
            summary = "Buscar asignatura por ID",
            parameters = @Parameter(name = "id", description = "ID de la asignatura", example = "1"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrada",
                            content = @Content(schema = @Schema(implementation = SubjectDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            }
    )
    @GetMapping("/findById")
    public ResponseEntity<SubjectDTO> findSubjectById(@RequestParam Integer id) throws SubjectNotFoundException {
        SubjectDTO subjectDTO = subjectService.findSubjectById(id);
        return ResponseEntity.status(200).body(subjectDTO);
    }

    @Operation(
            summary = "Lista de asignaturas",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class))))
    )
    @GetMapping("/findSubjects")
    public List<SubjectDTO> findSubjects() throws SubjectNotFoundException {
        return subjectService.findAll();
    }

    @Operation(
            summary = "Obtener asignaturas a partir del id de un profesor",
            parameters = @Parameter(name = "idTeacher", description = "ID del profesor", example = "1"),
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class))))
    )
    @GetMapping("/findSubjectsByIdTeacher")
    public List <SubjectDTO> findSubjectsByIdTeacher(@RequestParam Integer idTeacher) {
        return subjectService.findSubjectsByTeacherId(idTeacher);
    }

    @Operation(
            summary = "Eliminar asignatura por ID",
            parameters = @Parameter(name = "id", description = "ID de la asignatura", example = "1"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Eliminada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            }
    )
    @DeleteMapping("/deleteById")
    public ResponseEntity<SubjectDTO> deleteSubjectById(@RequestParam Integer id) throws SubjectNotFoundException {
        subjectService.deleteSubjectById(id);
        return ResponseEntity.ok().build();
    }
}