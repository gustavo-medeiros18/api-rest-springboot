package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.erudio.util.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/person/v1")
@Tag(name = "People", description = "Endpoints for managing people")
public class PersonController {
  @Autowired
  private PersonServices service;

  private static final String YAML_MEDIA_TYPE = "application/x-yaml";

  @GetMapping(
      value = "/{id}",
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  @Operation(
      summary = "Find a person recorded in the database",
      description = "Find a person recorded in the database",
      tags = {"People"},
      responses = {
          @ApiResponse(
              description = "Person found",
              responseCode = "200",
              content = {
                  @Content(
                      mediaType = MediaType.APLLICATION_JSON,
                      schema = @Schema(implementation = PersonVO.class)
                  )
              }
          ),
          @ApiResponse(
              description = "Bad request",
              responseCode = "400",
              content = @Content
          ),
          @ApiResponse(
              description = "Unauthorized",
              responseCode = "401",
              content = @Content
          ),
          @ApiResponse(
              description = "Not found",
              responseCode = "404",
              content = @Content
          ),
          @ApiResponse(
              description = "Internal server error",
              responseCode = "500",
              content = @Content
          ),
      }
  )
  public PersonVO findById(
      @PathVariable(value = "id") Long id
  ) {
    return service.findById(id);
  }

  @GetMapping(
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  @Operation(
      summary = "Find all people recorded in the database",
      description = "Find all people recorded in the database",
      tags = {"People"},
      responses = {
          @ApiResponse(
              description = "People found",
              responseCode = "200",
              content = {
                  @Content(
                      mediaType = MediaType.APLLICATION_JSON,
                      array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                  )
              }
          ),
          @ApiResponse(
              description = "Bad request",
              responseCode = "400",
              content = @Content
          ),
          @ApiResponse(
              description = "Unauthorized",
              responseCode = "401",
              content = @Content
          ),
          @ApiResponse(
              description = "Not found",
              responseCode = "404",
              content = @Content
          ),
          @ApiResponse(
              description = "Internal server error",
              responseCode = "500",
              content = @Content
          ),
      }
  )
  public List<PersonVO> findAll() {
    return service.findAll();
  }

  @PostMapping(
      consumes = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      },
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  @Operation(
      summary = "Records a person in the database",
      description = "Records a person in the database",
      tags = {"People"},
      responses = {
          @ApiResponse(
              description = "Person created",
              responseCode = "201",
              content = {
                  @Content(
                      mediaType = MediaType.APLLICATION_JSON,
                      schema = @Schema(implementation = PersonVO.class)
                  )
              }
          ),
          @ApiResponse(
              description = "Bad request",
              responseCode = "400",
              content = @Content
          ),
          @ApiResponse(
              description = "Unauthorized",
              responseCode = "401",
              content = @Content
          ),
          @ApiResponse(
              description = "Internal server error",
              responseCode = "500",
              content = @Content
          ),
      }
  )
  public PersonVO createPerson(
      @RequestBody PersonVO person
  ) {
    return service.create(person);
  }

  @PutMapping(
      consumes = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      },
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  public PersonVO updatePerson(
      @RequestBody PersonVO person
  ) {
    return service.update(person);
  }

  @DeleteMapping(
      value = "/{id}"
  )
  public ResponseEntity<Void> delete(
      @PathVariable(value = "id") Long id
  ) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}