package com.chrisilib.api.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Bookcase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "George's Bookcase 1"

    // Many Bookcases can be in one Room
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // One Bookcase can hold many Books
    @OneToMany(mappedBy = "bookcase")
    private List<Book> books;

}
