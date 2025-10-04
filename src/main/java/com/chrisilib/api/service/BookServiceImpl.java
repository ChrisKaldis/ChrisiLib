package com.chrisilib.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.chrisilib.api.dto.BookDTO;
import com.chrisilib.api.dto.BookResponseDTO;
import com.chrisilib.api.exception.ResourceNotFoundException;
import com.chrisilib.api.mapper.BookMapper;
import com.chrisilib.api.model.Book;
import com.chrisilib.api.model.Bookcase;
import com.chrisilib.api.repository.BookRepository;
import com.chrisilib.api.repository.BookcaseRepository;


/**
 * Implementation of the BookService interface.
 * This class contains the business logic for book management,
 * interacting with data repositories and using mappers for object conversion.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookcaseRepository bookcaseRepository;
    private final BookMapper bookMapper;

    /**
     * Constructs the BookServiceImpl with required dependencies.
     *
     * @param bookRepository The repository for book data access.
     * @param bookcaseRepository The repository for bookcase data access.
     * @param bookMapper The mapper for converting between DTOs and entities.
     */
    @Autowired
    public BookServiceImpl(
        BookRepository bookRepository,
        BookcaseRepository bookcaseRepository,
        BookMapper bookMapper
    ) {
        this.bookRepository = bookRepository;
        this.bookcaseRepository = bookcaseRepository;
        this.bookMapper = bookMapper;
    }


    @Override
    public Book createBook(BookDTO bookDTO) {
        // Use the mapper to convert simple fields from the DTO to the entity.
        Book newBook = bookMapper.toBook(bookDTO);

        // Manually handle the Bookcase relationship, as it requires a database lookup.
        if (bookDTO.getBookcaseId() != null) {
            Bookcase bookcase = bookcaseRepository.findById(bookDTO.getBookcaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Bookcase not found with id: " + bookDTO.getBookcaseId()));
            newBook.setBookcase(bookcase);
        }

        return bookRepository.save(newBook);
    }

    @Override
    public List<Book> findBooks(String query, Long bookcaseId, Long ownerId) {
        // Start with a specification that allows all results.
        Specification<Book> spec = (root, q, cb) -> cb.conjunction();

        // Add a predicate for the general query string if it exists.
        if (query != null && !query.isEmpty()) {
            spec = spec.and((root, q, criteriaBuilder) ->
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + query.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), "%" + query.toLowerCase() + "%")
                )
            );
        }

        // Add a predicate for the bookcase ID if it exists.
        if (bookcaseId != null) {
            spec = spec.and((root, q, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("bookcase").get("id"), bookcaseId)
            );
        }

        // Add a predicate for the owner ID if it exists.
        if (ownerId != null) {
            spec = spec.and((root, q, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ownerId"), ownerId)
            );
        }

        return bookRepository.findAll(spec);
    }

    @Override
    public Optional<Book> findBookById(Long id) {

        return bookRepository.findById(id);
    }

    @Override
    public Book updateBook(Long id, BookDTO bookDetails) {
        // Fetch the existing book or throw a 404 error if it doesn't exist.
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Use the mapper to perform a partial update. It will only map non-null fields
        // from the DTO to the entity, preserving existing values.
        bookMapper.updateBookFromDto(bookDetails, existingBook);

        // CLARIFICATION: This block re-assigns the book to a different bookcase.
        // It does not change the name of the bookcase itself.
        if (bookDetails.getBookcaseId() != null) {
            Bookcase bookcase = bookcaseRepository.findById(bookDetails.getBookcaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Bookcase not found with id: " + bookDetails.getBookcaseId()));
            existingBook.setBookcase(bookcase);
        }

        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        // Check if the book exists before attempting to delete it.
        // This ensures a clear 404 error is returned if the ID is invalid.
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                "Book not found with id: " + id
            );
        }

        bookRepository.deleteById(id);
    }

    @Override
    public BookResponseDTO findBookDetailsById(Long id) {
        // Fetch the entity or throw an exception if not found.
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Use the mapper to convert the entity to a safe, clean DTO for the API response.
        return bookMapper.toBookResponseDTO(book);
    }

}
