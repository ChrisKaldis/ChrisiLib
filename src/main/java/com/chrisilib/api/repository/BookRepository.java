package com.chrisilib.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.chrisilib.api.model.Book;


@Repository
public interface BookRepository extends  JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    // Spring Data JPA will automatically provide methods like
    // save(), findById(), findAll(), deleteById(), etc.

}
