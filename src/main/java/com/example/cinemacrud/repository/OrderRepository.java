package com.example.cinemacrud.repository;

import com.example.cinemacrud.model.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByFilmIdAndClientId(Long film_id, Long client_id);

//    @Override
//    @Query("UPDATE client SET deleted = true WHERE id = :id")
//    void deleteById(@Param("id") Long aLong);
}
