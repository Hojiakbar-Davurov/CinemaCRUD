package com.example.cinemacrud.model.domain;

import com.example.cinemacrud.model.dto.ClientDTO;
import com.example.cinemacrud.model.dto.FilmDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@AuditOverride(forClass = AbstractEntity.class)
@SQLDelete(sql = "UPDATE film SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Film extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @ManyToOne()
    private Cinema cinema;

    public Film(Long id) {
        this.setId(id);
    }

    public FilmDTO map2DTO() {
        return new FilmDTO(
                this.getId(),
                this.name,
                this.price,
                this.cinema.getId()
        );
    }
}
