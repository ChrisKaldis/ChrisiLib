package com.chrisilib.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.chrisilib.api.dto.BookDTO;
import com.chrisilib.api.exception.ResourceNotFoundException;
import com.chrisilib.api.model.Book;
import com.chrisilib.api.model.Bookcase;
import com.chrisilib.api.repository.BookRepository;
import com.chrisilib.api.repository.BookcaseRepository;


@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookcaseRepository bookcaseRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookcaseRepository bookcaseRepository) {
        this.bookRepository = bookRepository;
        this.bookcaseRepository = bookcaseRepository;
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book newBook = new Book();

        newBook.setTitle(bookDTO.getTitle());
        newBook.setAuthor(bookDTO.getAuthor());
        newBook.setIsbn(bookDTO.getIsbn());
        newBook.setOwnerId(bookDTO.getOwnerId());

        // Find the Bookcase entity by its ID and set it on the book
        if (bookDTO.getBookcaseId() != null) {
            Bookcase bookcase = bookcaseRepository.findById(bookDTO.getBookcaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Bookcase not found with id: " + bookDTO.getBookcaseId()));
            newBook.setBookcase(bookcase);
        }


        return bookRepository.save(newBook);
    }

    @Override
    public List<Book> findBooks(String query, Long bookcaseId, Long ownerId) {
        Specification<Book> spec = Specification.unrestricted();

        if (query != null && !query.isEmpty()) {
            spec = spec.and((root, q, criteriaBuilder) ->
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                                            "%" + query.toLowerCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("author")),
                                            "%" + query.toLowerCase() + "%")
                )
            );
        }

        if (bookcaseId != null) {
            spec = spec.and((root, q, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("locationId"), bookcaseId)
            );
        }

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
        Book existingBook = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // Check each field in the DTO and only update if
        // a new value was provided
        if (bookDetails.getTitle() != null) {
            existingBook.setTitle(bookDetails.getTitle());
        }
        if (bookDetails.getAuthor() != null) {
            existingBook.setAuthor(bookDetails.getAuthor());
        }
        if (bookDetails.getIsbn() != null) {
            existingBook.setIsbn(bookDetails.getIsbn());
        }
        if (bookDetails.getOwnerId() != null) {
            existingBook.setOwnerId(bookDetails.getOwnerId());
        }
        
        // Only if the book is in a bookcase you can change the bookcase name.
        if (bookDetails.getBookcaseId() != null) {
            Bookcase bookcase = bookcaseRepository.findById(bookDetails.getBookcaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Bookcase not found with id: " + bookDetails.getBookcaseId()));
            existingBook.setBookcase(bookcase);
        }

        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                "Book not found with id: " + id
            );
        }

        bookRepository.deleteById(id);
    }

}
