package com.example.cinemacrud.model.dto;

import com.example.cinemacrud.model.domain.Cinema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CinemaDTO {
    private Long id;
    @NotNull(message = "Cinema name not be empty")
    private String name;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Cinema map2Entity() {
        return new Cinema(this.name);
    }
}
