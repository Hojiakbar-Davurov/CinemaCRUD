package com.example.cinemacrud.service.impl;

import com.example.cinemacrud.exeptions.ResourceAlreadyExistsException;
import com.example.cinemacrud.exeptions.ResourceNotFoundException;
import com.example.cinemacrud.model.domain.Cinema;
import com.example.cinemacrud.model.dto.CinemaDTO;
import com.example.cinemacrud.repository.CinemaRepository;
import com.example.cinemacrud.service.CinemaService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CinemaServiceImp implements CinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public CinemaDTO save(CinemaDTO cinemaDTO) {
        log.debug("Request came to " + SERVICE_NAME + " service for save, DTO:{}", cinemaDTO);

        // check cinema exists by name
        if (cinemaRepository.existsByName(cinemaDTO.getName())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + cinemaDTO.getName());
        }

        // save cinema
        CinemaDTO savedCinemaDTO = cinemaRepository.save(cinemaDTO.map2Entity()).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedCinemaDTO);

        return savedCinemaDTO;
    }

    @Override
    public List<CinemaDTO> findAll() {
        return cinemaRepository.findAll()
                .stream()
                .map(Cinema::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public CinemaDTO findById(Long id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        CinemaDTO cinemaDTO = cinema.map2DTO();
        log.debug(SERVICE_NAME + " response find by id from DB, " + SERVICE_NAME + "DTO:{}", cinemaDTO);

        return cinemaDTO;
    }

    @Override
    public CinemaDTO update(Long id, CinemaDTO cinemaDTO) {
        log.debug("Request came to update with -> id: {}, " + SERVICE_NAME + "DTO:{}", id, cinemaDTO);

        // find cinema by id
        Cinema optionalCinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));
        // update cinema
        Cinema cinema = cinemaDTO.map2Entity();
        cinema.setId(optionalCinema.getId());

        CinemaDTO saveCinemaDTO = cinemaRepository.save(cinema).map2DTO();
        log.debug(SERVICE_NAME + " updates, DTO:{}", saveCinemaDTO);

        return saveCinemaDTO;
    }

    @Override
    public void deleteById(Long id) {
        if (!cinemaRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id: " + id);

        cinemaRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id: {}", id);
    }
}
