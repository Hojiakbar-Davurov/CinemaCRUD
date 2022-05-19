package com.example.cinemacrud.model.domain;

import com.example.cinemacrud.model.dto.ClientDTO;
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
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Audited
@AuditOverride(forClass = AbstractEntity.class)
@SQLDelete(sql = "UPDATE client SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Client extends AbstractEntity {
    @Column(unique = true, nullable = false)
    private String fullName;

    public Client(Long id) {
        this.setId(id);
    }

    public ClientDTO map2DTO() {
        return new ClientDTO(
                this.getId(),
                this.fullName,
                this.getCreateAt(),
                this.getUpdateAt()
        );
    }
}
