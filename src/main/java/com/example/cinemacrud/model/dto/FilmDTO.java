package com.example.cinemacrud.model.dto;

import com.example.cinemacrud.model.domain.Film;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FilmDTO {

    private Long id;

    @NotEmpty(message = "Film name not be empty")
    private String name;

    @NotNull(message = "Film price not be empty")
    private Double price;

    @NotNull(message = "Cinema id not be empty")
    private Long cinemaId;

    public Film map2Entity() {
        Film film = new Film();
        film.setName(this.name);
        film.setPrice(this.price);
        return film;
    }
}
