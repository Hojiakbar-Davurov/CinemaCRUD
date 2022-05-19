package com.example.cinemacrud.service.impl;

import com.example.cinemacrud.exeptions.ResourceAlreadyExistsException;
import com.example.cinemacrud.exeptions.ResourceNotFoundException;
import com.example.cinemacrud.model.domain.Client;
import com.example.cinemacrud.model.dto.ClientDTO;
import com.example.cinemacrud.repository.ClientRepository;
import com.example.cinemacrud.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ClientServiceImp implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request came to " + SERVICE_NAME + " Service for save, DTO:{}", clientDTO);

        // check Client exists by FullName
        if (clientRepository.existsByFullName(clientDTO.getFullName())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by fullName: " + clientDTO.getFullName());
        }

        // save Client
        ClientDTO saveClientDTO = clientRepository.save(clientDTO.map2Entity()).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", saveClientDTO);

        return saveClientDTO;
    }

    @Override
    public List<ClientDTO> findAll() {
        return clientRepository.findAll()
                .stream()
                .map(Client::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        ClientDTO clientDTO = client.map2DTO();
        log.debug(SERVICE_NAME + " response find by id from DB, DTO:{}", clientDTO);

        return clientDTO;
    }

    @Override
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        log.debug("Request came to update with -> id: {}, " + SERVICE_NAME + "DTO:{}", id, clientDTO);

        // find Client by id
        Client optionalClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        // update Client
        Client client = clientDTO.map2Entity();
        client.setId(optionalClient.getId());

        ClientDTO saveClientDTO = clientRepository.save(client).map2DTO();
        log.debug(SERVICE_NAME + " updates, DTO:{}", saveClientDTO);

        return saveClientDTO;
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id);

        clientRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id: {}", id);
    }
}
