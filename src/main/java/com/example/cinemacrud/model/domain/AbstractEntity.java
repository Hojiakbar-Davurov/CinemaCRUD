package com.example.cinemacrud.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotAudited
    @Column(unique = true, updatable = false)
    @CreationTimestamp()
    private Timestamp createAt;

    @NotAudited
    @Column(unique = true)
    @UpdateTimestamp
    private Timestamp updateAt;

    private boolean deleted = Boolean.FALSE;

    public AbstractEntity(Long id) {
        this.id = id;
    }


//    @CreatedBy
//    @JoinColumn(updatable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User createdBy;
//
//    @LastModifiedBy
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User updatedBy;
}
