package com.example.cinemacrud.exeptions;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ValidErrorResponse {

    private String message;

    private List<String> details;
}
