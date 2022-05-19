package com.example.cinemacrud.controller;

import com.example.cinemacrud.model.dto.ApiResponseWrapperDTO;
import com.example.cinemacrud.model.dto.CinemaDTO;
import com.example.cinemacrud.service.CinemaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/cinema")
public class CinemaController {
    private static final String SERVICE_NAME = "Cinema";
    @Autowired
    private CinemaService cinemaService;

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody CinemaDTO cinemaDTO) {
        log.debug("Request came to save from url=.../api/cinema, " + SERVICE_NAME + "DTO:{}", cinemaDTO);

        CinemaDTO saveCinemaDTO = cinemaService.save(cinemaDTO);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", saveCinemaDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveCinemaDTO
                ));
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        return ResponseEntity
                .ok()
                .body(cinemaService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<CinemaDTO> findById(@PathVariable Long id) {
        CinemaDTO cinemaDTO = cinemaService.findById(id);
        log.debug(SERVICE_NAME + " response find by id from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", cinemaDTO);

        return ResponseEntity.ok().body(cinemaDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody CinemaDTO cinemaDTO) {
        log.debug("Request came to update from url=.../api/cinema/{id} id: {}, " + SERVICE_NAME + "DTO:{}", id, cinemaDTO);

        CinemaDTO updateCinemaDTO = cinemaService.update(id, cinemaDTO);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", updateCinemaDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " updated",
                HttpStatus.ACCEPTED.value(),
                updateCinemaDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        cinemaService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
