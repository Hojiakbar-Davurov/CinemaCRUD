package com.example.cinemacrud.service;

import com.example.cinemacrud.model.dto.ClientDTO;

import java.util.List;

public interface ClientService {

    String SERVICE_NAME = "Client";

    ClientDTO save(ClientDTO clientDTO);

    List<ClientDTO> findAll();

    ClientDTO findById(Long id);

    ClientDTO update(Long id, ClientDTO clientDTO);

    void deleteById(Long id);
}