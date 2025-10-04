package com.chrisilib.api.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a specific piece of furniture or area where books are stored,
 * such as a shelf or a desk, located within a Room.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Bookcase extends BaseEntity {

    /**
     * The name of the bookcase (e.g., "Left Wall Shelf").
     */
    @Column(nullable = false)
    private String name; // e.g., "George's Bookcase 1"

    /**
     * The Room where this bookcase is located.
     */    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    /**
     * The list of books stored in this bookcase. Deleting a bookcase
     * does not automatically delete the books within it.
     */
    @OneToMany(mappedBy = "bookcase")
    private List<Book> books;

}
