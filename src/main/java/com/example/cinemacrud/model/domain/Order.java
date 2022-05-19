package com.example.cinemacrud.model.domain;

import com.example.cinemacrud.model.dto.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
@Audited
@AuditOverride(forClass = AbstractEntity.class)
@SQLDelete(sql = "UPDATE orders SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Order extends AbstractEntity {
    @OneToOne
    private Film film;
    @OneToOne
    private Client client;

    public Order(Long id) {
        super(id);
    }

    public OrderDTO map2DTO() {
        return new OrderDTO(
                this.getId(),
                this.film.getId(),
                this.client.getId()
        );
    }
}
