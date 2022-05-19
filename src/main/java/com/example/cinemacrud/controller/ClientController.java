package com.example.cinemacrud.controller;

import com.example.cinemacrud.model.dto.ApiResponseWrapperDTO;
import com.example.cinemacrud.model.dto.ClientDTO;
import com.example.cinemacrud.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/client")
public class ClientController {
    private static final String SERVICE_NAME = "Client";
    @Autowired
    private ClientService clientService;

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody ClientDTO clientDTO) {
        log.debug("Request came to save from url=.../api/client, " + SERVICE_NAME + "DTO:{}", clientDTO);

        ClientDTO saveClientDTO = clientService.save(clientDTO);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", saveClientDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveClientDTO
                ));
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        return ResponseEntity
                .ok()
                .body(clientService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<ClientDTO> findById(@PathVariable Long id) {
        ClientDTO clientDTO = clientService.findById(id);
        log.debug(SERVICE_NAME + " response find by id from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", clientDTO);

        return ResponseEntity
                .ok()
                .body(clientDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody ClientDTO clientDTO) {
        log.debug("Request came to update from url=.../api/client/{id} id: {}, " + SERVICE_NAME + "DTO:{}", id, clientDTO);

        ClientDTO updateClientDTO = clientService.update(id, clientDTO);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", updateClientDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " update",
                HttpStatus.ACCEPTED.value(),
                updateClientDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
