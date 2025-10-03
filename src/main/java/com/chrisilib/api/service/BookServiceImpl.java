package com.chrisilib.api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    public List<Book> findBooks(String query, Long locationId, Long ownerId) {
        // TODO Auto-generated method stub

        return Collections.emptyList();
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        // TODO Auto-generated method stub

        return Optional.empty();
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
