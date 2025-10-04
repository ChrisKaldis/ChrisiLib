package com.chrisilib.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chrisilib.api.model.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
}
