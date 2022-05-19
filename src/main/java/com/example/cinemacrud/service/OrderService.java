package com.example.cinemacrud.service;

import com.example.cinemacrud.model.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    String SERVICE_NAME = "Order";

    OrderDTO save(OrderDTO orderDTO);

    List<OrderDTO> findAll();

    OrderDTO findById(Long id);

    OrderDTO update(Long id, OrderDTO orderDTO);

    void deleteById(Long id);
}