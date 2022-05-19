package com.example.cinemacrud.service.impl;

import com.example.cinemacrud.exeptions.ResourceAlreadyExistsException;
import com.example.cinemacrud.exeptions.ResourceNotFoundException;
import com.example.cinemacrud.model.domain.Cinema;
import com.example.cinemacrud.model.domain.Film;
import com.example.cinemacrud.model.dto.FilmDTO;
import com.example.cinemacrud.repository.CinemaRepository;
import com.example.cinemacrud.repository.FilmRepository;
import com.example.cinemacrud.service.FilmService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class FilmServiceImp implements FilmService {
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public FilmDTO save(FilmDTO filmDTO) {
        log.debug("Request came to  " + SERVICE_NAME + " service for save, DTO:{}", filmDTO);

        // check Film exists by name and cinema id
        if (filmRepository.existsByNameAndCinemaId(filmDTO.getName(), filmDTO.getCinemaId()))
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + filmDTO.getName() + " and cinemaId: " + filmDTO.getCinemaId());

        // exists Cinema by id
        if (!cinemaRepository.existsById(filmDTO.getCinemaId()))
            throw new ResourceNotFoundException("Cinema not found by cinemaId: " + filmDTO.getCinemaId());

        // save Film
        Film film = filmDTO.map2Entity();
        film.setCinema(new Cinema(filmDTO.getCinemaId()));

        FilmDTO saveFilmDTO = filmRepository.save(film).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", saveFilmDTO);

        return saveFilmDTO;
    }

    @Override
    public List<FilmDTO> findAll() {
        return filmRepository.findAll()
                .stream()
                .map(Film::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public FilmDTO findById(Long id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        FilmDTO filmDTO = film.map2DTO();
        log.debug(SERVICE_NAME + " response find by id from DB, " + SERVICE_NAME + "DTO:{}", filmDTO);

        return filmDTO;
    }

    @Override
    public FilmDTO update(Long id, FilmDTO filmDTO) {
        log.debug("Request came to update with -> id: {}, " + SERVICE_NAME + "DTO:{}", id, filmDTO);

        // find Film by id
        Film optionalFilm = filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        // exists Cinema by id
        if (!cinemaRepository.existsById(filmDTO.getCinemaId()))
            throw new ResourceNotFoundException("Cinema not found by cinemaId: " + filmDTO.getCinemaId());

        // update Film
        Film film = filmDTO.map2Entity();
        film.setId(optionalFilm.getId());
        film.setCinema(new Cinema(filmDTO.getCinemaId()));

        FilmDTO saveFilmDTO = filmRepository.save(film).map2DTO();
        log.debug(SERVICE_NAME + " updates, DTO:{}", saveFilmDTO);

        return saveFilmDTO;
    }

    @Override
    public void deleteById(Long id) {
        if (!filmRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id");

        filmRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id: {}", id);

    }
}
