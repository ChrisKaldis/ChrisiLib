package com.chrisilib.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.chrisilib.api.model.Book;


/**
 * Spring Data JPA repository for the {@link Book} entity.
 * It provides standard CRUD operations and support for dynamic queries
 * via JpaSpecificationExecutor.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

}
