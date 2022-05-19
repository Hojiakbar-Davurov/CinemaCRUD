package com.example.cinemacrud.model.dto;

import com.example.cinemacrud.model.domain.Client;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;
    @NotNull(message = "Client fullName not be empty")
    private String fullName;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Client map2Entity() {
        return new Client(this.fullName);
    }
}
