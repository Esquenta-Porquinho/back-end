package com.college.hotlittlepigs.box;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long>{
    
    Optional<Box> findBoxByNumberAndStatus(int number, Boolean status);
}
