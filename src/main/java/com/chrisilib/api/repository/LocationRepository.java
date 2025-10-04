package com.chrisilib.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisilib.api.model.Location;


@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    
}
