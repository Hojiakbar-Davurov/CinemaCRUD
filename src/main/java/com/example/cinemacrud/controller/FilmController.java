package com.example.cinemacrud.controller;

import com.example.cinemacrud.model.dto.ApiResponseWrapperDTO;
import com.example.cinemacrud.model.dto.FilmDTO;
import com.example.cinemacrud.service.FilmService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/film")
public class FilmController {
    private static final String SERVICE_NAME = "Film";
    @Autowired
    private FilmService filmService;

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody FilmDTO filmDTO) {
        log.debug("Request came to save from url=.../api/film, " + SERVICE_NAME + "DTO:{}", filmDTO);

        FilmDTO saveFilmDTO = filmService.save(filmDTO);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", saveFilmDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveFilmDTO
                ));
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        return ResponseEntity.ok().body(filmService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<FilmDTO> findById(@PathVariable Long id) {
        FilmDTO filmDTO = filmService.findById(id);
        log.debug(SERVICE_NAME + " response find by id from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", filmDTO);

        return ResponseEntity.ok().body(filmDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody FilmDTO filmDTO) {
        log.debug("Request came to update from url=.../api/film/{id} id: {}, " + SERVICE_NAME + "DTO:{}", id, filmDTO);

        FilmDTO updateFilmDTO = filmService.update(id, filmDTO);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", filmDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " updated",
                HttpStatus.ACCEPTED.value(),
                updateFilmDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        filmService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
