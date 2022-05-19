package com.example.cinemacrud.model.domain;

import com.example.cinemacrud.model.dto.CinemaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
@AuditOverride(forClass = AbstractEntity.class)
@SQLDelete(sql =  "UPDATE cinema SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Cinema extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String name;

    public Cinema(Long id) {
        super(id);
    }

    public CinemaDTO map2DTO() {
        CinemaDTO cinemaDTO = new CinemaDTO();
        cinemaDTO.setId(this.getId());
        cinemaDTO.setName(this.name);
        cinemaDTO.setCreatedAt(this.getCreateAt());
        cinemaDTO.setUpdatedAt(this.getUpdateAt());
        return cinemaDTO;
    }
}
