package com.chrisilib.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Represents a single book in the user's library.
 * This is the central entity of the application.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {

    private String title;
    private String author;

    /**
     * The International Standard Book Number (ISBN).
     */
    private String isbn;

    /**
     * The ID of the owner of this book.
     * Consider refactoring this to a @ManyToOne relationship with an Owner/User entity.
     */
    private Long ownerId;

    /**
     * The specific bookcase where this book is physically located.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookcase_id")
    private Bookcase bookcase;

}
