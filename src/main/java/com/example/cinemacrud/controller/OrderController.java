package com.example.cinemacrud.controller;

import com.example.cinemacrud.model.dto.ApiResponseWrapperDTO;
import com.example.cinemacrud.model.dto.OrderDTO;
import com.example.cinemacrud.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private static final String SERVICE_NAME = "Order";
    @Autowired
    private OrderService orderService;

    @PostMapping
    public HttpEntity<ApiResponseWrapperDTO> save(@Valid @RequestBody OrderDTO orderDTO) {
        log.debug("Request came to save from url=.../api/order, " + SERVICE_NAME + "DTO:{}", orderDTO);

        OrderDTO saveOrderDTO = orderService.save(orderDTO);
        log.debug("Saved " + SERVICE_NAME + " response came from " + SERVICE_NAME + " Service, " + SERVICE_NAME + "DTO:{}", orderDTO);

        return ResponseEntity.ok(
                new ApiResponseWrapperDTO(
                        SERVICE_NAME + " saved",
                        HttpStatus.CREATED.value(),
                        saveOrderDTO
                ));
    }

    @GetMapping
    public HttpEntity<?> findAll() {
        return ResponseEntity.ok().body(orderService.findAll());
    }

    @GetMapping("/{id}")
    public HttpEntity<OrderDTO> findById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.findById(id);
        log.debug(SERVICE_NAME + " response find by id from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", orderDTO);

        return ResponseEntity.ok().body(orderDTO);
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponseWrapperDTO> update(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        log.debug("Request came to update from url=.../api/order/{id} id: {}, " + SERVICE_NAME + "DTO:{}", id, orderDTO);

        OrderDTO updateOrderDTO = orderService.update(id, orderDTO);
        log.debug("Updated " + SERVICE_NAME + " response came from " + SERVICE_NAME + " service, " + SERVICE_NAME + "DTO:{}", updateOrderDTO);

        return ResponseEntity.ok(new ApiResponseWrapperDTO(
                SERVICE_NAME + " updated",
                HttpStatus.ACCEPTED.value(),
                updateOrderDTO
        ));
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteById(@PathVariable Long id) {
        orderService.deleteById(id);
        log.debug(SERVICE_NAME + " deleted at controller by id: {}", id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
