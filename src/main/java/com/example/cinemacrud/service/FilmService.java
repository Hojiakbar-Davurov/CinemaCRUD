package com.example.cinemacrud.service;

import com.example.cinemacrud.model.dto.FilmDTO;

import java.util.List;

public interface FilmService {
    String SERVICE_NAME = "Film";

    FilmDTO save(FilmDTO filmDTO);

    List<FilmDTO> findAll();

    FilmDTO findById(Long id);

    FilmDTO update(Long id, FilmDTO filmDTO);

    void deleteById(Long id);
}