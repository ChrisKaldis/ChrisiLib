package com.chrisilib.api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.chrisilib.api.dto.BookDTO;
import com.chrisilib.api.model.Book;
import com.chrisilib.api.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book newBook = new Book();

        newBook.setTitle(bookDTO.getTitle());
        newBook.setAuthor(bookDTO.getAuthor());
        newBook.setIsbn(bookDTO.getIsbn());
        newBook.setLocationId(bookDTO.getLocationId());
        newBook.setOwnerId(bookDTO.getOwnerId());

        return bookRepository.save(newBook);
    }

    @Override
    public List<Book> findBooks(String query, Long locationId, Long ownerId) {
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

        if (locationId != null) {
            spec = spec.and((root, q, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("locationId"), locationId)
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
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public void deleteBook(Long id) {
        // TODO Auto-generated method stub

        return ;
    }
    
}
