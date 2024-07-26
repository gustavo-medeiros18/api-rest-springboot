package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.services.BookServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
public class BookController {
  @Autowired
  private BookServices service;

  @GetMapping(
      value = "/{id}",
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  @Operation(
      summary = "Find a book recorded in the database",
      description = "Find a book recorded in the database",
      tags = {"Books"},
      responses = {
          @ApiResponse(
              description = "Book found",
              responseCode = "200",
              content = {
                  @Content(
                      mediaType = MediaType.APLLICATION_JSON,
                      schema = @Schema(implementation = BookVO.class)
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
  public BookVO findById(@PathVariable(value = "id") Long id) {
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
      summary = "Find all books recorded in the database",
      description = "Find all books recorded in the database",
      tags = {"Books"},
      responses = {
          @ApiResponse(
              description = "Book found",
              responseCode = "200",
              content = {
                  @Content(
                      mediaType = MediaType.APLLICATION_JSON,
                      array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
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
  public List<BookVO> findAll() {
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
      summary = "Records a book in the database",
      description = "Records a book in the database",
      tags = {"Books"},
      responses = {
          @ApiResponse(
              description = "Book created",
              responseCode = "201",
              content = {
                  @Content(
                      mediaType = MediaType.APLLICATION_JSON,
                      schema = @Schema(implementation = BookVO.class)
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
  public BookVO create(@RequestBody BookVO book) {
    return service.create(book);
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
  @Operation(
      summary = "Updates a book in the database",
      description = "Updates a book in the database",
      tags = {"Books"},
      responses = {
          @ApiResponse(
              description = "Book updated",
              responseCode = "200",
              content = {
                  @Content(
                      mediaType = MediaType.APLLICATION_JSON,
                      schema = @Schema(implementation = BookVO.class)
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
  public BookVO update(@RequestBody BookVO book) {
    return service.update(book);
  }

  @DeleteMapping("/{id}")
  @Operation(
      summary = "Deletes a book in the database",
      description = "Delete a book in the database",
      tags = {"Books"},
      responses = {
          @ApiResponse(
              description = "No content",
              responseCode = "204",
              content = @Content
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
  public void delete(@PathVariable(value = "id") Long id) {
    service.delete(id);
  }
}
