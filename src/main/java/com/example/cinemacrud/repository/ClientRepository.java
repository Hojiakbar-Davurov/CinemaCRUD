package com.example.cinemacrud.repository;

import com.example.cinemacrud.model.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByFullName(String name);
}
