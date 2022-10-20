package com.metadata.school.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    protected UUID id;

    @PrePersist
    private void setNewId() {
        this.setId(UUID.randomUUID());
    }

}
