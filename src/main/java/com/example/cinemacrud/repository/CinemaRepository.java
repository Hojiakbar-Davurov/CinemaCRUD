package com.example.cinemacrud.repository;

import com.example.cinemacrud.model.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    boolean existsByName(String name);
}
