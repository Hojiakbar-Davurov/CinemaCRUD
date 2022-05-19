package com.example.cinemacrud.repository;

import com.example.cinemacrud.model.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {

    boolean existsByNameAndCinemaId(String name, Long cinema_id);
}
