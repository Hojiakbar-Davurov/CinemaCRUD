package com.example.cinemacrud.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;

    @NotNull(message = "film id must be not null")
    private Long filmId;

    @NotNull(message = "client id  not be empty")
    private Long clientId;

}
