package es.aitor.com.aitor.Controladores.restapi;

import es.aitor.com.aitor.Entidades.Persona;

import es.aitor.com.aitor.Servicios.PersonasServices;
import es.aitor.com.aitor.beans.BeanCopyUtils;
import es.aitor.com.aitor.dto.Aplicacion.PersonaMapper;
import es.aitor.com.aitor.dto.Aplicacion.PersonaCreateDto;
import es.aitor.com.aitor.dto.Aplicacion.PersonaResponseDto;
import es.aitor.com.aitor.dto.Aplicacion.PersonaUpdateDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Data
@RestController
@CrossOrigin
@RequestMapping("/api/persona")
public class PersonaRestController {

    private final PersonasServices personasServices;
    private final PersonaMapper mapper;

    @Operation(summary = "Obtiene todas las personas", description = "Retorna una lista de todas las personas disponibles")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaResponseDto.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping
    public ResponseEntity<List<PersonaResponseDto>> getAll() {
        log.info("estamos en api persona");
        try {
            List<PersonaResponseDto> personaResponseDtos = mapper.toDtoList(personasServices.findAll());
            return new ResponseEntity<>(personaResponseDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Operation(summary = "Obtiene una  persona", description = "Retorna una informacion  de una persona solamente")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaResponseDto.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping({ "/{id}" })
    public ResponseEntity<PersonaResponseDto> getOne(@PathVariable("id") Long id) {
        Optional<Persona> persona = personasServices.findById(id);

        return persona
                .map(m -> new ResponseEntity<>(mapper.toDto(m), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }



    @Operation(summary = "Obtiene una  persona", description = "Retorna una informacion  de una persona solamente")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaCreateDto.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")

    @PostMapping
    public ResponseEntity<Map<String, Object>> add(@Valid @RequestBody PersonaCreateDto personaDto) {
        if (personaDto == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        try {
            Long id = personasServices.save(mapper.toEntity(personaDto)).getId();
            return new ResponseEntity<>(Collections.singletonMap("id", id), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Puedes poner una nueva persona a la base de datos", description = "Retorna una informacion  de una persona solamente")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaUpdateDto.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> updateReplace(@Valid @RequestBody(required = false) PersonaUpdateDto personaDto,
                                                             @PathVariable("id") Long id) {
        if (personaDto == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        try {
            Optional<Persona> personaDB = personasServices.findById(id);
            if (personaDB.isPresent()) {
                personasServices.save(mapper.toEntity(personaDto));
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Puedes poner una nueva persona a la base de datos", description = "Retorna una informacion  de una persona solamente")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaUpdateDto.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> updateModify(@Valid @RequestBody(required = false) PersonaUpdateDto personaDto,
                                                             @PathVariable("id") Long id) {
        if (personaDto == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        try {
            Optional<Persona> personaDB = personasServices.findById(id);
            if (personaDB.isPresent()) {
                // copiar los campos no nulos de mascotaDto en mascotaDB
                Persona mascotaSource = mapper.toEntity(personaDto);
                Persona mascotaTarget = personaDB.get();
                BeanCopyUtils.copyNonNullProperties(mascotaSource, mascotaTarget);
                personasServices.save(mascotaTarget);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Operation(summary = "Puedes borrar ", description = "Retorna una informacion  de una persona solamente")
    @ApiResponse(responseCode = "200", description = "Operación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PersonaUpdateDto.class)))
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id) {
        try {
            Optional<Persona> persona = personasServices.findById(id);
            if (persona.isPresent()) {
                personasServices.deleteById(id);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
