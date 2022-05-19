package com.example.cinemacrud.service;

import com.example.cinemacrud.model.dto.CinemaDTO;

import java.util.List;

public interface CinemaService {

   String SERVICE_NAME = "Cinema";

    CinemaDTO save(CinemaDTO cinemaDTO);

    List<CinemaDTO> findAll();

    CinemaDTO findById(Long id);

    CinemaDTO update(Long id, CinemaDTO cinemaDTO);

    void deleteById(Long id);
}