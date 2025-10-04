package com.chrisilib.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chrisilib.api.dto.BookDTO;
import com.chrisilib.api.model.Book;
import com.chrisilib.api.service.BookService;


/**
 * REST Controller for managing books in the ChrisiLib application.
 * This controller handles all CRUD operations for books.
 */
@RestController
@RequestMapping("api/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * POST /api/v1/books
     * Adds a new book to the library.
     * The request body can contain either an ISBN (for automatic lookup)
     * or full manual details for the book.
     *
     * @param bookDTO Data Transfer Object containing book information.
     * @return The created book with a 201 Created status.
     */
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO) {
        // The service will contain the logic to check if an ISBN is present
        // and fetch data, or simply save the manually entered data.
        Book savedBook = bookService.createBook(bookDTO);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    /**
     * GET /api/v1/books
     * Searchs for a book in the library.
     * It can filter by a general query string, bookcase, or owner.
     * All parameters are optional.
     * 
     * Examples:
     * - /api/v1/books -> Get all books
     * - /api/v1/books?query=Orwell -> Searches for "Orwell" in title/author
     * - /api/v1/books?bookcaseId=2 -> Gets all books in bookcase with ID 2
     * - /api/v1/books?ownerId=5&query=Dune -> Finds "Dune" owned by user with ID 5
     * 
     * @param query Optional search term (for title, author, etc.).
     * @param bookcaseId Optional ID of the bookcase (e.g., "George's Bookcase 1").
     * @param ownerId Optional ID of the owner (you or a friend).
     * @return A list of books matching the criteria.
     */
    @GetMapping
    public ResponseEntity<List<Book>> searchBooks(
        @RequestParam(required = false) String query,
        @RequestParam(required = false) Long bookcaseId,
        @RequestParam(required = false) Long ownerId
    ) {
        List<Book> books = bookService.findBooks(query, bookcaseId, ownerId);

        return ResponseEntity.ok(books);
    }

    /**
     * GET /api/v1/books/{id}
     * Retrieves a single book by its unique ID.
     *
     * @param id The ID of the book.
     * @return The book if found (200 OK), or 404 Not Found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.findBookById(id);
   
        return book.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/v1/books/{id}
     * Updates an existing book. This is useful for moving a book
     * to a different location or correcting a typo.
     *
     * @param id The ID of the book to update.
     * @param bookDetails DTO with the fields to update.
     * @return The updated book.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
        @RequestBody BookDTO bookDetails
    ) {
        Book updatedBook = bookService.updateBook(id, bookDetails);

        return ResponseEntity.ok(updatedBook);
    }

    /**
     * DELETE /api/v1/books/{id}
     * Deletes a book from the library.
     *
     * @param id The ID of the book to delete.
     * @return 204 No Content status to indicate successful deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }

}
