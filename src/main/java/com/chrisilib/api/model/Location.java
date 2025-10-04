package com.chrisilib.api.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a high-level physical location, such as a house or an office.
 * This is the top-level entity in the location hierarchy.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Location extends BaseEntity {

    /**
     * The unique, human-readable name of the location (e.g., "Parents' House").
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * The list of rooms contained within this location.
     * The relationship is managed by the 'location' field in the Room entity.
     * Cascade operations ensure that rooms are managed along with their parent location.
     */
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

}
