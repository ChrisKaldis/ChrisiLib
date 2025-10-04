package com.chrisilib.api.model;

import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * An abstract base class for all JPA entities.
 * It provides a common primary key ('id') field, and a standard implementation
 * of equals() and hashCode() to avoid issues with entity identity.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * The unique identifier for the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        // If the id is null, the entities are not equal.
        // This is crucial for new, unsaved entities.
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        // Use a consistent hash code for all instances of this class.
        // This prevents issues when entities are added to collections before being saved.
        return getClass().hashCode();
    }

}
