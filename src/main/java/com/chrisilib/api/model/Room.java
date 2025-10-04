package com.chrisilib.api.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a specific room within a Location (e.g., "Living Room", "Office").
 * It acts as a container for bookcases.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Room extends BaseEntity {

    /**
     * The name of the room. It does not need to be unique across all locations.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The parent Location to which this room belongs.
     * This relationship is eagerly fetched by default but set to LAZY for better performance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    /**
     * A list of bookcases present in this room.
     * Operations on a Room (like deletion) will cascade to its associated bookcases.
     */    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookcase> bookcases;

}
