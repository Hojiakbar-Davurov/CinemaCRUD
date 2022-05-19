package com.example.cinemacrud.service.impl;

import com.example.cinemacrud.exeptions.ResourceAlreadyExistsException;
import com.example.cinemacrud.exeptions.ResourceNotFoundException;
import com.example.cinemacrud.model.domain.Client;
import com.example.cinemacrud.model.domain.Film;
import com.example.cinemacrud.model.domain.Order;
import com.example.cinemacrud.model.dto.OrderDTO;
import com.example.cinemacrud.repository.ClientRepository;
import com.example.cinemacrud.repository.FilmRepository;
import com.example.cinemacrud.repository.OrderRepository;
import com.example.cinemacrud.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        log.debug("Request came to " + SERVICE_NAME + "Service for save, DTO:{}", orderDTO);

        // check order exists by film id and client id
        if (orderRepository.existsByFilmIdAndClientId(orderDTO.getFilmId(), orderDTO.getClientId())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by filmId: " + orderDTO.getFilmId() + " and clientId: " + orderDTO.getClientId());
        }

        // check film exists by id
        if (!filmRepository.existsById(orderDTO.getFilmId())) {
            throw new ResourceNotFoundException("Film not found by filmId: " + orderDTO.getFilmId());
        }

        // check client exists by id
        if (!clientRepository.existsById(orderDTO.getClientId())) {
            throw new ResourceNotFoundException("Client not found by filmId: " + orderDTO.getClientId());
        }

        // save Film
        Order order = new Order();
        order.setClient(new Client(orderDTO.getClientId()));
        order.setFilm(new Film(orderDTO.getFilmId()));

        OrderDTO saveOrderDTO = orderRepository.save(order).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", saveOrderDTO);

        return saveOrderDTO;
    }

    @Override
    public List<OrderDTO> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(Order::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        OrderDTO orderDTO = order.map2DTO();
        log.debug(SERVICE_NAME + " response find by id from DB, " + SERVICE_NAME + "DTO:{}", orderDTO);

        return orderDTO;
    }

    @Override
    public OrderDTO update(Long id, OrderDTO orderDTO) {
        log.debug("Request came to update with -> id: {}, " + SERVICE_NAME + "DTO:{}", id, orderDTO);

        // find Order by id
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found by id:" + id));

        // exists Film by id
        if (!filmRepository.existsById(orderDTO.getFilmId()))
            throw new ResourceNotFoundException("Film not found by filmId: " + orderDTO.getFilmId());

        // exists Client by id
        if (!clientRepository.existsById(orderDTO.getClientId()))
            throw new ResourceNotFoundException("Client not found by clientId: " + orderDTO.getClientId());

        // update Order
        order.setFilm(new Film(orderDTO.getFilmId()));
        order.setClient(new Client(orderDTO.getClientId()));

        OrderDTO saveOrderDTO = orderRepository.save(order).map2DTO();
        log.debug(SERVICE_NAME + " updates, DTO:{}", saveOrderDTO);

        return saveOrderDTO;
    }

    @Override
    public void deleteById(Long id) {
        if (!orderRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id");

        orderRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id: {}", id);
    }
}
