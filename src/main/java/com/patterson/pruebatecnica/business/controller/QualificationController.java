package com.patterson.pruebatecnica.business.controller;

import com.patterson.pruebatecnica.business.dto.QualificationDTO;
import com.patterson.pruebatecnica.business.exceptions.QualificationNotFoundException;
import com.patterson.pruebatecnica.business.service.QualificationService;
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

@Tag(name = "Qualifications", description = "Operaciones con calificaciones")
@RestController
@RequestMapping("/qualifications")
public class QualificationController {

    private QualificationService qualificationService;

    public QualificationController(QualificationService qualificationService) {
        this.qualificationService = qualificationService;
    }

    @Operation(
            summary = "Crear calificaciones",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = QualificationDTO.class)),
                            examples = @ExampleObject(
                                    name = "Ejemplo",
                                    value = "[{\"percentage\":40,\"note\":8.5,\"idSubject\":1,\"idStudent\":1}," +
                                            "{\"percentage\":60,\"note\":7.0,\"idSubject\":2,\"idStudent\":2}]"
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Creado",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = QualificationDTO.class)))),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    @PostMapping("/batch")
    public ResponseEntity<List<QualificationDTO>> createQualifications(@RequestBody List<QualificationDTO> qualificationDTOs) {
        List<QualificationDTO> qualifications = qualificationService.createQualifications(qualificationDTOs);
        return ResponseEntity.status(201).body(qualifications);
    }

    @Operation(
            summary = "Buscar calificación por ID",
            parameters = @Parameter(name = "id", description = "ID de la calificación", example = "101"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrada",
                            content = @Content(schema = @Schema(implementation = QualificationDTO.class))),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            }
    )
    @GetMapping("/findById")
    public ResponseEntity<QualificationDTO> findQualificationById(@RequestParam Integer id) throws QualificationNotFoundException {
        QualificationDTO qualificationDTO = qualificationService.findQualificationById(id);
        return ResponseEntity.status(200).body(qualificationDTO);
    }

    @Operation(
            summary = "Lista de calificaciones",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = QualificationDTO.class))))
    )
    @GetMapping("/findQualifications")
    public List<QualificationDTO> findQualifications()  throws QualificationNotFoundException {
        return qualificationService.findAll();
    }

    @Operation(
            summary = "Lista de calificaciones de un estudiante",
            parameters = @Parameter(name = "idStudent", description = "ID del estudiante", example = "1"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = QualificationDTO.class)))),
                    @ApiResponse(responseCode = "404", description = "No se encontraron calificaciones para el estudiante")
            }
    )
    @GetMapping("/qualifications")
    public List<QualificationDTO> findQualificationsByStudentId(@RequestParam Integer idStudent) throws QualificationNotFoundException {
        return qualificationService.findByStudentId(idStudent);
    }

    @Operation(
            summary = "Eliminar calificación por ID",
            parameters = @Parameter(name = "id", description = "ID de la calificación", example = "101"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Eliminada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada")
            }
    )
    @DeleteMapping("/deleteById")
    public ResponseEntity<?> deleteQualificationById(@RequestParam Integer id) throws QualificationNotFoundException {
        qualificationService.deleteQualificationById(id);
        return ResponseEntity.ok().build();
    }
}