package com.chrisilib.api.service;

import java.util.List;
import java.util.Optional;

import com.chrisilib.api.dto.BookDTO;
import com.chrisilib.api.model.Book;

public interface BookService {
    
    Book createBook(BookDTO bookDTO);
    List<Book> findBooks(String query, Long locationId, Long ownerId);
    Optional<Book> findBookById(Long id);
    Book updateBook(Long id, BookDTO bookDetails);
    void deleteBook(Long id);

}
