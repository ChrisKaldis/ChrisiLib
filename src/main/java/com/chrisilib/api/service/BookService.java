package com.chrisilib.api.service;

import java.util.List;
import java.util.Optional;

import com.chrisilib.api.dto.BookDTO;
import com.chrisilib.api.dto.BookResponseDTO;
import com.chrisilib.api.model.Book;


/**
 * Defines the business logic contract for managing books in the application.
 * This interface decouples the controller layer from the implementation details
 * of the service layer, allowing for flexibility and easier testing.
 */
public interface BookService {

    /**
     * Creates and saves a new book based on the provided DTO.
     *
     * @param bookDTO The Data Transfer Object containing the new book's information.
     * @return The saved Book entity, including its generated ID.
     */
    Book createBook(BookDTO bookDTO);

    /**
     * Searches for books based on a set of optional filter criteria.
     *
     * @param query An optional search term to match against book titles and authors.
     * @param bookcaseId An optional ID to filter books by the bookcase they are in.
     * @param ownerId An optional ID to filter books by their owner.
     * @return A list of Book entities matching the criteria.
     */
    List<Book> findBooks(String query, Long bookcaseId, Long ownerId);

    /**
     * Retrieves a single book by its unique ID.
     *
     * @param id The unique identifier of the book.
     * @return An Optional containing the found Book, or an empty Optional if not found.
     */
    Optional<Book> findBookById(Long id);

    /**
     * Updates an existing book with new details. This performs a partial update,
     * only changing fields that are provided in the DTO.
     *
     * @param id The ID of the book to update.
     * @param bookDetails The DTO containing the fields to be updated.
     * @return The updated Book entity.
     */
    Book updateBook(Long id, BookDTO bookDetails);

    /**
     * Deletes a book from the repository by its ID.
     *
     * @param id The ID of the book to delete.
     * @throws com.chrisilib.api.exception.ResourceNotFoundException if no book with the given ID exists.
     */
    void deleteBook(Long id);

    /**
     * Retrieves the details of a single book by its ID and returns them in a safe,
     * flattened Response DTO format suitable for API clients.
     *
     * @param id The unique identifier of the book.
     * @return A BookResponseDTO containing the book's details.
     */
    BookResponseDTO findBookDetailsById(Long id);

}
