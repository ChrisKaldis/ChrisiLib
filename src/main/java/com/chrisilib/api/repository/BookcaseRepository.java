package com.chrisilib.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisilib.api.model.Bookcase;

@Repository
public interface BookcaseRepository extends JpaRepository<Bookcase, Long> {
    
}
